package pl.devdioniz.compressor;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * @author TheDioniz, created on 30.08.2017.
 */
public class ZipperTest {

    static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String TEST_FILE1_TXT = "test.txt";
    public static final String TEST_FILE2_TXT = "test2.txt";
    public static final String TEST_ZIP = "TEST_ZIP.zip";
    public static final String TEST_ZIP_FILE = "TEST_ZIP_FILE.zip";

    @Before
    public void setUp() throws Exception {

        Files.deleteIfExists(Paths.get(TEMP_DIR.concat(File.separator + TEST_FILE1_TXT)));
        Files.deleteIfExists(Paths.get(TEMP_DIR.concat(File.separator + TEST_FILE2_TXT)));
        Files.deleteIfExists(Paths.get(TEMP_DIR.concat(File.separator + TEST_ZIP)));
        Files.deleteIfExists(Paths.get(TEMP_DIR.concat(File.separator + TEST_ZIP_FILE)));
    }

    @Test
    public void shouldZipAllFiles() throws Exception {

        Path file1 = Files.createFile(Paths.get(TEMP_DIR.concat(File.separator + TEST_FILE1_TXT)));
        Path file2 = Files.createFile(Paths.get(TEMP_DIR.concat(File.separator + TEST_FILE2_TXT)));

        Files.write(file1, "TEST DATA".getBytes());
        Files.write(file2, "TEST DATA".getBytes());

        Set<Path> files = new HashSet<>();
        files.add(file1);
        files.add(file2);

        Path zipFile = Zipper.zipAllFiles(files, Paths.get(TEMP_DIR.concat(File.separator + TEST_ZIP)));

        Assert.assertNotNull(zipFile);
        Assert.assertTrue(Files.exists(zipFile));
    }

    @Test
    public void shouldZipFile() throws Exception {
        String tmpdir = System.getProperty("java.io.tmpdir");
        String testZipPath = tmpdir.concat("TEST_ZIP_FILE.zip");

        Path file = Files.createFile(Paths.get(tmpdir.concat(File.separator + TEST_FILE1_TXT)));

        Files.write(file, "TEST DATA FOR TEST FILE".getBytes());

        Path zip = Zipper.zipFile(file, Paths.get(File.separator +testZipPath));

        Assert.assertNotNull(zip);
        Assert.assertTrue(Files.exists(zip));
    }

    @AfterClass
    public static void cleanUp() throws Exception {
        Files.deleteIfExists(Paths.get(TEMP_DIR.concat(File.separator + TEST_FILE1_TXT)));
        Files.deleteIfExists(Paths.get(TEMP_DIR.concat(File.separator + TEST_FILE2_TXT)));
        Files.deleteIfExists(Paths.get(TEMP_DIR.concat(File.separator + TEST_ZIP)));
        Files.deleteIfExists(Paths.get(TEMP_DIR.concat(File.separator + TEST_ZIP_FILE)));
    }
}