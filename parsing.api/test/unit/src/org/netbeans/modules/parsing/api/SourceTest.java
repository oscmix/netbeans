/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.parsing.api;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.netbeans.junit.NbTestCase;
import org.netbeans.modules.parsing.impl.SourceAccessor;
import org.netbeans.modules.parsing.impl.TaskProcessor;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;

/**
 *
 * @author vita
 */
public class SourceTest extends ParsingTestBase {

    public SourceTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        FileUtil.setMIMEType("txt", "text/plain");  //NOI18N
    }

    public void testCreateSnapshotEOLConversions() throws IOException {
        String documentContent = "0\n1\n2\n3\n4\n5\n6\n7\n8\n9\n";

        // test CRLF conversion
        {
        FileObject crlfFile = createFileObject("crlf.txt", documentContent, "\r\n");
        Source crlfSource = Source.create(crlfFile);
        assertNull("The crlfSource should have no document", crlfSource.getDocument(false));
        assertSame("Wrong file in crlfSource", crlfFile, crlfSource.getFileObject());
        Snapshot crlfSnapshot = crlfSource.createSnapshot();
        assertEquals("Wrong crlf endlines conversion", documentContent, crlfSnapshot.getText().toString());
        }

        // test LF conversion
        {
        FileObject lfFile = createFileObject("lf.txt", documentContent, "\n");
        Source lfSource = Source.create(lfFile);
        assertNull("The crlfSource should have no document", lfSource.getDocument(false));
        assertSame("Wrong file in crlfSource", lfFile, lfSource.getFileObject());
        Snapshot lfSnapshot = lfSource.createSnapshot();
        assertEquals("Wrong crlf endlines conversion", documentContent, lfSnapshot.getText().toString());
        }
    }

    public void testSourceForFileObject() throws IOException {
        FileObject file = createFileObject("plain.txt", "Hey dude!", "\n");
        Source source = Source.create(file);
        assertNotNull("No Source for " + file, source);

        Source s1 = Source.create(file);
        assertSame("Expecting the same Source for the same file", source, s1);
    }

    public void testSourceForFileObjectGCed() throws IOException {
        FileObject file = createFileObject("plain.txt", "Hey dude!", "\n");
        Source source = Source.create(file);
        assertNotNull("No Source for " + file, source);

        Reference<Source> sourceRef = new WeakReference<Source>(source);
        Reference<FileObject> fileRef = new WeakReference<FileObject>(file);

        source = null;
        assertGC("Source can't be GCed", sourceRef);

        file = null;
        assertGC("File can't be GCed", fileRef);
    }

    public void testSourceForFilelessDocument() {
        Document doc = createDocument("text/plain", "");
        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);

        Source s1 = Source.create(doc);
        assertSame("Expecting the same Source for the same document", source, s1);
    }

    public void testSourceForFilelessDocumentGCed() {
        Document doc = createDocument("text/plain", "");
        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);

        Reference<Source> sourceRef = new WeakReference<Source>(source);
        Reference<Document> docRef = new WeakReference<Document>(doc);

        source = null;
        assertGC("Source can't be GCed", sourceRef);

        doc = null;
        assertGC("Document can't be GCed", docRef);
    }


    public void testSourceForDocument() throws IOException {
        FileObject file = createFileObject("plain.txt", getName(), "\n");
        Document doc = openDocument(file);
        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);

        Source s1 = Source.create(doc);
        assertSame("Expecting the same Source for the same document", source, s1);
    }

    public void testSourceForDocumentGCed() throws IOException {
        FileObject file = createFileObject("plain.txt", getName(), "\n");
        Document doc = openDocument(file);
        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);

        Reference<Source> sourceRef = new WeakReference<Source>(source);
        Reference<Document> docRef = new WeakReference<Document>(doc);
        Reference<FileObject> fileRef = new WeakReference<FileObject>(file);

        source = null;
        assertGC("Source can't be GCed", sourceRef);

        doc = null;
        assertGC("Document can't be GCed", docRef);

        file = null;
        assertGC("FileObject can't be GCed", fileRef);
    }

    public void testSnapshotContents() throws IOException {
        String documentContent = "Apples\nPears\nPlums\nAppricots\nOranges\nBananas\nPeaches\n"; //NOI18N
        FileObject file = createFileObject("plain.txt", documentContent, "\r\n");
        Source source = Source.create(file);
        assertNotNull("No Source for " + file, source);

        Snapshot snapshot = source.createSnapshot();
        assertNotNull("No snapshot", snapshot);
        assertEquals("Wrong snapshot contents", documentContent, snapshot.getText().toString());

        String documentContent2 = "Potatos\nTomatos\nOnion\nGarlic\nCucumber\nBeetroot\nEggplant\n"; //NOI18N
        writeToFileObject(file, documentContent2, "\r\n");

        // the old snapshot is still the same
        assertEquals("Original snapshot modified", documentContent, snapshot.getText().toString());

        // new snapshot should contain the new text
        Snapshot snapshot2 = source.createSnapshot();
        assertNotNull("No snapshot", snapshot2);
        assertEquals("New snapshot has wrong contents", documentContent2, snapshot2.getText().toString());
    }

    public void testConsistencySourceForFileObject() throws IOException {
        FileObject file = createFileObject("empty.txt", "", "\n");
        Source source = Source.create(file);
        assertNotNull("No Source for " + file, source);
        assertSame("Wrong FileObject", file, source.getFileObject());
        assertSame("Inconsistent Source.create(FileObject)", source, Source.create(file));

        Document doc = openDocument(file);
        assertNotNull("Can't open document for " + file, doc);
        assertSame("Inconsistent Source.create(Document)", source, Source.create(doc));
        assertSame("Wrong document", doc, source.getDocument(false));
    }

    public void testConsistencySourceForDocument() throws IOException {
        FileObject file = createFileObject("empty.txt", "", "\n");
        Document doc = openDocument(file);
        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);
        assertSame("Wrong document", doc, source.getDocument(false));
        assertSame("Inconsistent Source.create(Document)", source, Source.create(doc));

        assertSame("Inconsistent Source.create(FileObject)", source, Source.create(file));
        assertSame("Wrong FileObject", file, source.getFileObject());
    }

    public void testMimeTypeChange() throws IOException {
        clearWorkDir();
        final FileObject file = createFileObject("empty.foo", "", "\n");
        final Source source = Source.create(file);
        assertNotNull("No Source for " + file, source);
        assertSame("Wrong FileObject", file, source.getFileObject());
        assertSame("Inconsistent Source.create(FileObject)", source, Source.create(file));
        final FileLock lock = file.lock();
        try {
            file.rename(lock, "empty", "txt");  //NOI18N
        } finally {
            lock.releaseLock();
        }
        final Source source2 = Source.create(file);
        assertNotNull("No Source for " + file, source2);
        assertSame("Wrong FileObject", file, source2.getFileObject());
        assertNotSame("Inconsistent Source.create(FileObject)", source, source2);
    }

    public void testConsistencySourceForFilelessDocument() {
        Document doc = createDocument("text/plain", "");
        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);
        assertSame("Wrong document", doc, source.getDocument(false));
        assertSame("Inconsistent Source.create(Document)", source, Source.create(doc));
        assertNull("Source for fileless document should not have FileObject", source.getFileObject());
    }

    public void testMimeType() throws IOException {
        FileObject file = createFileObject("empty.txt", "", "\n");
        Source source = Source.create(file);
        assertNotNull("No Source for " + file, source);
        assertEquals("Wrong mimetype", file.getMIMEType(), source.getMimeType());
    }

    public void testMimeTypeOnSourceForFilebasedDocument() throws IOException {
        FileObject file = createFileObject("empty.txt", "", "\n");
        Document doc = openDocument(file);

        // simulate CloneableEditorSupport.Env setting different mimetype
        doc.putProperty("mimeType", "text/x-different");
        assertFalse("Document should have different mimetype then file", "text/x-different".equals(file.getMIMEType()));

        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);
        assertEquals("Wrong mimetype", "text/x-different", source.getMimeType());
    }

    public void testMimeTypeOnSourceForFilelessDocument() {
        Document doc = createDocument("text/x-testtesttest", "");
        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);
        assertEquals("Wrong mimetype", "text/x-testtesttest", source.getMimeType());
    }

    public void testDOMove154813() throws IOException {
        FileObject file = createFileObject("test1/empty.txt", "", "\n");
        DataObject dfile = DataObject.find(file);
        Document doc = createDocument("text/x-testtesttest", "");
        doc.putProperty(Document.StreamDescriptionProperty, dfile);
        Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);
        FileObject wd = file.getParent().getParent();
        FileObject nueParent = wd.getFileObject("test2");

        if (nueParent == null) {
            nueParent = wd.createFolder("test2");
        }

        dfile.move(DataFolder.findFolder(nueParent));
        source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);
        assertEquals("Correct FileObject", dfile.getPrimaryFile(), source.getFileObject());
    }

    public void testDeadlock164258() throws Exception {
        final StyledDocument doc = (StyledDocument) createDocument("text/plain", "");
        final Source source = Source.create(doc);
        assertNotNull("No Source for " + doc, source);
        final CountDownLatch startLatch1 = new CountDownLatch(1);
        final CountDownLatch startLatch2 = new CountDownLatch(1);

        //Prerender
        ParserManager.parse(Collections.singleton(source), new UserTask() {
            @Override
            public void run(ResultIterator resultIterator) throws Exception {
                
            }
        });

        new Thread() {
            public void run () {
                NbDocument.runAtomic(doc, new Runnable() {
                    public void run () {
                        try {
                            startLatch1.await();
                            startLatch2.countDown();
                            SourceAccessor.getINSTANCE().getEnvControl(source).sourceChanged(false);
                        } catch (InterruptedException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                });
            }
        }.start();
        synchronized(TaskProcessor.INTERNAL_LOCK) {
            startLatch1.countDown();
            startLatch2.await();
            NbDocument.runAtomic(doc, new Runnable() {
                public void run() {
                }
            });
        }
    }

    private FileObject createFileObject(String name, String documentContent, String eol) throws IOException {
        FileObject workDir = FileUtil.toFileObject(getWorkDir());
        FileObject f = FileUtil.createData(workDir, name);
        writeToFileObject(f, documentContent, eol);

        return f;
    }

    private void writeToFileObject(FileObject f, String documentContent, String eol) throws IOException {
        assertTrue("No UTF-8 available", Charset.isSupported("UTF-8"));
        OutputStream os = f.getOutputStream();
        try {
            byte [] eolBytes = eol.getBytes("UTF-8");
            byte [] bytes = documentContent.getBytes("UTF-8");
            for(byte b : bytes) {
                if (b == '\n') {
                    os.write(eolBytes);
                } else {
                    os.write(b);
                }
            }
        } finally {
            os.close();
        }
    }

    private Document createDocument(String mimeType, String contents) {
        Document doc = new DefaultStyledDocument();
        doc.putProperty("mimeType", mimeType);
        try {
            doc.insertString(0, contents, null);
            return doc;
        } catch (BadLocationException ble) {
            throw new IllegalStateException(ble);
        }
    }

    private Document openDocument(FileObject f) {
        try {
            DataObject dataObject = DataObject.find(f);
            EditorCookie ec = dataObject.getLookup().lookup(EditorCookie.class);
            return ec.openDocument();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
