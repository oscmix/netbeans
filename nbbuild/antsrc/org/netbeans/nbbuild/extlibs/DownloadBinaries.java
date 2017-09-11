/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */

package org.netbeans.nbbuild.extlibs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.util.FileUtils;

/**
 * Task to retrieve named files (generally large binaries such as ZIPs) from a repository.
 * Similar to a very simplified version of Ivy, but correctly handles binaries
 * with missing or irrelevant version numbers, since it is based on a hash of contents.
 * You keep one or more manifests under version control which enumerate files and their SHA-1 hashes.
 * Then just run this task to download any missing files.
 * Remember to specify the binaries as "ignorable" to your version control system.
 * You can also run it in a clean mode which will remove the binaries.
 * At the end of this source file is a sample CGI script and matching form which you can run on the server
 * to permit people to upload files to the correct repository paths.
 * Motivation: http://wiki.netbeans.org/wiki/view/HgMigration#section-HgMigration-Binaries
 */
public class DownloadBinaries extends Task {

    private File cache;
    /**
     * Location of per-user cache of already downloaded binaries.
     * Optional; no cache will be used if unset.
     * The directory will be created if it does not yet exist.
     */
    public void setCache(File cache) {
        this.cache = cache;
    }

    private String server;
    /**
     * URL prefix for the server repository.
     * Should generally include a trailing slash.
     * You may include multiple server URLs separated by spaces
     * in which case they will be tried in order.
     * To use a local repository, simply specify e.g. <code>file:/repo/</code> as the URL.
     */
    public void setServer(String server) {
        this.server = server;
    }

    private final List<FileSet> manifests = new ArrayList<FileSet>();
    /**
     * Add one or more manifests of files to download.
     * Each manifest is a text file; lines beginning with # are ignored.
     * All other lines must be entries of the form
     * <pre>
     * 0123456789ABCDEF something-1.0.jar
     * </pre>
     * consisting of an SHA-1 hash followed by a filename.
     * The filename is relative to the manifest, usually a simple basename.
     * If the file exists and has the specified hash, nothing is done.
     * If it has the wrong hash, the task aborts with an error message.
     * If it is missing, it is downloaded from the server (or copied from cache)
     * using a filename derived from the basename of the file in the manifest and its hash.
     * For example, the above line with a server of <code>http://nowhere.net/repo/</code>
     * would try to download
     * <pre>
     * http://nowhere.net/repo/0123456789ABCDEF-something-1.0.jar
     * </pre>
     * Any version number etc. in the filename is purely informational;
     * the "up to date" check is entirely based on the hash.
     */
    public void addManifest(FileSet manifest) {
        manifests.add(manifest);
    }

    private boolean clean;
    /**
     * If true, rather than creating binary files, will delete them.
     * Any cache is ignored in this case.
     * If a binary does not match its hash, the build is aborted:
     * the file might be a precious customized version and should not be blindly deleted.
     */
    public void setClean(boolean clean) {
        this.clean = clean;
    }

    @Override
    public void execute() throws BuildException {
        for (FileSet fs : manifests) {
            DirectoryScanner scanner = fs.getDirectoryScanner(getProject());
            File basedir = scanner.getBasedir();
            for (String include : scanner.getIncludedFiles()) {
                File manifest = new File(basedir, include);
                log("Scanning: " + manifest, Project.MSG_VERBOSE);
                try {
                    InputStream is = new FileInputStream(manifest);
                    try {
                        BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String line;
                        while ((line = r.readLine()) != null) {
                            if (line.startsWith("#")) {
                                continue;
                            }
                            if (line.trim().length() == 0) {
                                continue;
                            }
                            String[] hashAndFile = line.split(" ", 2);
                            if (hashAndFile.length < 2) {
                                throw new BuildException("Bad line '" + line + "' in " + manifest, getLocation());
                            }

                            if (isMavenFile(hashAndFile)) {
                                try {
                                    mavenFile(hashAndFile, manifest);
                                } catch (IOException ex) {
                                    String[] artifactGroupVersion = hashAndFile[1].split(":");
                                    String shortName = artifactGroupVersion[1] + '-' + artifactGroupVersion[2] + ".jar";
                                    hashedFile(hashAndFile[0], shortName, manifest);
                                }
                            } else {
                                hashedFile(hashAndFile[0], hashAndFile[1], manifest);
                            }
                        }
                    } finally {
                        is.close();
                    }
                } catch (IOException x) {
                    throw new BuildException("Could not open " + manifest + ": " + x, x, getLocation());
                }
            }
        }
    }
    
    private void mavenFile(String[] hashAndId, File manifest) throws IOException {
        String id = hashAndId[1];
        String[] ids = id.split(":");
        if (ids.length != 3) {
            throw new BuildException("Expecting groupId:artifactId:version, but was " + id + " in " + manifest);
        }

        String baseName = mavenFileName(hashAndId);
        File f = new File(manifest.getParentFile(), baseName);
        if (clean || !f.exists()) {
            log("Creating " + f);
            String cacheName = ids[0].replace('.', '/') + "/" +
                    ids[1] + "/" + ids[2] + "/" + ids[1] + "-" + ids[2] + ".jar";
            
            File local = new File(new File(new File(new File(System.getProperty("user.home")), ".m2"), "repository"), cacheName.replace('/', File.separatorChar));
            final String url;
            if (local.exists()) {
                url = local.toURI().toString();
            } else {
                url = "http://central.maven.org/maven2/" + cacheName;
            }
            try {
                URL u = new URL(url);
                if (downloadFromServer(u, cacheName, f, hashAndId[0])) {
                    return;
                }
            } catch (IOException ex) {
                String msg = "Could not download " + url + " to " + f + ": " + cacheName;
                log(msg, Project.MSG_WARN);
                throw new IOException(ex);
            }
            String msg = "Could not download " + url + " to " + f + ": " + cacheName;
            throw new IOException(msg);
        }
    }

    private void hashedFile(String expectedHash, String baseName, File manifest) throws BuildException {
        File f = new File(manifest.getParentFile(), baseName);
        if (!clean) {
            if (!f.exists() || !hash(f).equals(expectedHash)) {
                log("Creating " + f);
                String cacheName = expectedHash + "-" + baseName;
                if (cache != null) {
                    cache.mkdirs();
                    File cacheFile = new File(cache, cacheName);
                    if (!cacheFile.exists()) {
                        download(cacheName, cacheFile, expectedHash);
                    }
                    if (f.isFile() && !f.delete()) {
                        throw new BuildException("Could not delete " + f);
                    }
                    try {
                        FileUtils.getFileUtils().copyFile(cacheFile, f);
                    } catch (IOException x) {
                        throw new BuildException("Could not copy " + cacheFile + " to " + f + ": " + x, x, getLocation());
                    }
                } else {
                    download(cacheName, f, expectedHash);
                }
            }
            String actualHash = hash(f);
            if (!actualHash.equals(expectedHash)) {
                throw new BuildException("File " + f + " requested by " + manifest + " to have hash " +
                        expectedHash + " actually had hash " + actualHash, getLocation());
            }
            log("Have " + f + " with expected hash", Project.MSG_VERBOSE);
        } else {
            if (f.exists()) {
                String actualHash = hash(f);
                if (!actualHash.equals(expectedHash)) {
                    throw new BuildException("File " + f + " requested by " + manifest + " to have hash " +
                            expectedHash + " actually had hash " + actualHash, getLocation());
                }
                log("Deleting " + f);
                f.delete();
            }
        }
    }

    private void download(String cacheName, File destination, String expectedHash) {
        if (server == null) {
            throw new BuildException("Must specify a server to download files from", getLocation());
        }
        Throwable firstProblem = null;
        for (String prefix : server.split(" ")) {
            URL url = null;
            try {
                url = new URL(prefix + cacheName);
                if (downloadFromServer(url, cacheName, destination, expectedHash)) {
                    return;
                }
            } catch (IOException x) {
                String msg = "Could not download " + url + " to " + destination + ": " + x;
                log(msg, Project.MSG_WARN);
                if (firstProblem == null) {
                    firstProblem = new IOException(msg).initCause(x);
                }
            }
        }
        throw new BuildException("Could not download " + cacheName + " from " + server + ": " + firstProblem, firstProblem, getLocation());
    }
    
    private boolean downloadFromServer(URL url, String cacheName, File destination, String expectedHash) 
    throws IOException {
        log("Downloading: " + url);
        URLConnection conn = openConnection(url);
        int code = HttpURLConnection.HTTP_OK;
        if (conn instanceof HttpURLConnection) {
            code = ((HttpURLConnection) conn).getResponseCode();
        }
        if (code != HttpURLConnection.HTTP_OK) {
            log("Skipping download from " + url + " due to response code " + code, Project.MSG_VERBOSE);
            return false;
        }
        InputStream is = conn.getInputStream();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                byte[] buf = new byte[4096];
                int read;
                while ((read = is.read(buf)) != -1) {
                    baos.write(buf, 0, read);
                }
            } catch (IOException x) {
                throw new BuildException(x); // should not happen
            }
            byte[] data = baos.toByteArray();
            if (expectedHash != null) {
                String actualHash = hash(new ByteArrayInputStream(data));
                if (!expectedHash.equals(actualHash)) {
                    throw new BuildException("Download of " + url + " produced content with hash "
                            + actualHash + " when " + expectedHash + " was expected", getLocation());
                }
            }
            OutputStream os = new FileOutputStream(destination);
            try {
                os.write(data);
            } catch (IOException x) {
                os.close();
                destination.delete();
                throw x;
            }
            os.close();
        } finally {
            is.close();
        }
        return true;
    }

    private URLConnection openConnection(final URL url) throws IOException {
        final URLConnection[] conn = { null };
        final CountDownLatch connected = new CountDownLatch(1);
        ExecutorService connectors = Executors.newFixedThreadPool(3);
        connectors.submit(new Runnable() {
            public void run() {
                String httpProxy = System.getenv("http_proxy");
                if (httpProxy != null) {
                    try {
                        URI uri = new URI(httpProxy);
                        InetSocketAddress address = InetSocketAddress.createUnresolved(uri.getHost(), uri.getPort());
                        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
                        URLConnection test = url.openConnection(proxy);
                        test.connect();
                        conn[0] = test;
                        connected.countDown();
                    } catch (IOException ex) {
                        log(ex, Project.MSG_ERR);
                    } catch (URISyntaxException ex) {
                        log(ex, Project.MSG_ERR);
                    }
                }
            }
        });
        connectors.submit(new Runnable() {
            public void run() {
                String httpProxy = System.getenv("https_proxy");
                if (httpProxy != null) {
                    try {
                        URI uri = new URI(httpProxy);
                        InetSocketAddress address = InetSocketAddress.createUnresolved(uri.getHost(), uri.getPort());
                        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
                        URLConnection test = url.openConnection(proxy);
                        test.connect();
                        conn[0] = test;
                        connected.countDown();
                    } catch (IOException ex) {
                        log(ex, Project.MSG_ERR);
                    } catch (URISyntaxException ex) {
                        log(ex, Project.MSG_ERR);
                    }
                }
            }
        });
        connectors.submit(new Runnable() {
            public void run() {
                try {
                    URLConnection test = url.openConnection();
                    test.connect();
                    conn[0] = test;
                    connected.countDown();
                } catch (IOException ex) {
                    log(ex, Project.MSG_ERR);
                }
            }
        });
        try {
            connected.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
        }
        if (conn[0] == null) {
            throw new IOException("Cannot connect to " + url);
        }
        return conn[0];
    }

    private String hash(File f) {
        try {
            FileInputStream is = new FileInputStream(f);
            try {
                return hash(is);
            } finally {
                is.close();
            }
        } catch (IOException x) {
            throw new BuildException("Could not get hash for " + f + ": " + x, x, getLocation());
        }
    }

    private String hash(InputStream is) throws IOException {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException x) {
            throw new BuildException(x, getLocation());
        }
        byte[] buf = new byte[4096];
        int r;
        while ((r = is.read(buf)) != -1) {
            digest.update(buf, 0, r);
        }
        return String.format("%040X", new BigInteger(1, digest.digest()));
    }

    static boolean isMavenFile(String... hashAndId) {
        return hashAndId[1].split(":").length > 2;
    }
    static String mavenFileName(String... hashAndId) {
        assert isMavenFile(hashAndId);
        String[] artifactGroupVersion = hashAndId[1].split(":");
        return artifactGroupVersion[1] + "-" + artifactGroupVersion[2] + ".jar";
    }


}

/*

Sample upload script (edit repository location as needed):

#!/usr/bin/env ruby
repository = '/tmp/repository'
require 'cgi'
require 'digest/sha1'
require 'date'
cgi = CGI.new
begin
  if cgi.request_method == 'POST'
    value = cgi['file']
    content = value.read
    name = value.original_filename.gsub(/\.\.|[^a-zA-Z0-9._+-]/, '_')
    sha1 = Digest::SHA1.hexdigest(content).upcase
    open("#{repository}/#{sha1}-#{name}", "w") do |f|
      f.write content
    end
    open("#{repository}/log", "a") do |f|
      f << "#{DateTime.now.to_s} #{sha1}-#{name} #{cgi.remote_user}\n"
    end
    cgi.out do <<RESPONSE
<html>
<head>
<title>Uploaded #{name}</title>
</head>
<body>
<p>Uploaded. Add to your manifest:</p>
<pre>#{sha1} #{name}</pre>
</body>
</html>
RESPONSE
    end
  else
    cgi.out do <<FORM
<html>
<head>
<title>Upload a Binary</title>
</head>
<body>
<form method="POST" action="" enctype="multipart/form-data">
<input type="file" name="file">
<input type="submit" value="Upload">
</form>
</body>
</html>
FORM
    end
  end
rescue
  cgi.out do
    "Caught an exception: #{$!}"
  end
end

 */
