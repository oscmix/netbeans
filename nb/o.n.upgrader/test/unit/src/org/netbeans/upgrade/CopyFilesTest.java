/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.upgrade;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.LocalFileSystem;

public class CopyFilesTest extends org.netbeans.junit.NbTestCase {

    public CopyFilesTest(String name) {
	super(name);
    }

    @Before
    @Override
    public void setUp() throws Exception {
	super.setUp();
        clearWorkDir();
    }

    @Test
    public void testCopyDeep() throws Exception {
	ArrayList<String> fileList = new ArrayList<String>();
	fileList.addAll(Arrays.asList(new java.lang.String[]{"source/foo/X.txt",
		    "source/foo/A.txt", "source/foo/B.txt", "source/foo/foo2/C.txt"}));

	FileSystem fs = createLocalFileSystem(fileList.toArray(new String[0]));

	FileObject path = fs.findResource("source");
	assertNotNull(path);
	FileObject tg = fs.getRoot().createFolder("target");
	assertNotNull(tg);
	FileObject patterns = FileUtil.createData(fs.getRoot(), "source/foo/etc/patterns.import");
	assertNotNull(patterns);
	String pattern = "# ignore comment\n"
		+ "include foo/.*\n"
		+ "translate foo=>bar\n";
	writeTo(fs, "source/foo/etc/patterns.import", pattern);

	org.netbeans.upgrade.CopyFiles.copyDeep(FileUtil.toFile(path), FileUtil.toFile(tg), FileUtil.toFile(patterns));

	assertNotNull("file not copied: " + "foo/X.txt", tg.getFileObject("bar/X.txt"));
	assertNotNull("file not copied: " + "foo/A.txt", tg.getFileObject("bar/A.txt"));
	assertNotNull("file not copied: " + "foo/B.txt", tg.getFileObject("bar/B.txt"));
	assertNotNull("file not copied: " + "foo/foo2/C.txt", tg.getFileObject("bar/foo2/C.txt"));
    }

    private static void writeTo (FileSystem fs, String res, String content) throws java.io.IOException {
        FileObject fo = org.openide.filesystems.FileUtil.createData (fs.getRoot (), res);
        org.openide.filesystems.FileLock lock = fo.lock ();
        java.io.OutputStream os = fo.getOutputStream (lock);
        os.write (content.getBytes ());
        os.close ();
        lock.releaseLock ();
    }

    public LocalFileSystem createLocalFileSystem(String[] resources) throws IOException {
        File mountPoint = new File(getWorkDir(), "tmpfs");
        mountPoint.mkdir();

        for (int i = 0; i < resources.length; i++) {
            File f = new File (mountPoint,resources[i]);
            if (f.isDirectory() || resources[i].endsWith("/")) {
              FileUtil.createFolder(f);
            } else {
              FileUtil.createData(f);
            }
        }

        LocalFileSystem lfs = new LocalFileSystem();
        try {
            lfs.setRootDirectory(mountPoint);
        } catch (Exception ex) {}

        return lfs;
    }
}
