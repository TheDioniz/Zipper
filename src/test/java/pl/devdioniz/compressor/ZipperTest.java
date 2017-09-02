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

    static final Path TEMP_DIR = Paths.get(System.getProperty("java.io.tmpdir") + File.separator);

    public static final String TEST_FILE1_TXT = "test.txt";
    public static final String TEST_FILE2_TXT = "test2.txt";
    public static final String TEST_ZIP = "TEST_ZIP.zip";
    public static final String TEST_ZIP_FILE = "TEST_ZIP_FILE.zip";

    private static Path joinTempDirWithFile(String fileName) {
        return TEMP_DIR.resolve(fileName);
    }

    @Before
    public void setUp() throws Exception {

        Files.deleteIfExists(joinTempDirWithFile(TEST_FILE1_TXT));
        Files.deleteIfExists(joinTempDirWithFile(TEST_FILE2_TXT));
        Files.deleteIfExists(joinTempDirWithFile(TEST_ZIP));
        Files.deleteIfExists(joinTempDirWithFile(TEST_ZIP_FILE));
    }

    @Test
    public void shouldZipAllFiles() throws Exception {

        Path file1 = Files.createFile(TEMP_DIR.resolve(TEST_FILE1_TXT));
        Path file2 = Files.createFile(TEMP_DIR.resolve(TEST_FILE2_TXT));

        Files.write(file1, "TEST DATA".getBytes());
        Files.write(file2, "TEST DATA".getBytes());

        Set<Path> files = new HashSet<>();
        files.add(file1);
        files.add(file2);

        Path zipFile = Zipper.zipAllFiles(files, joinTempDirWithFile(TEST_ZIP));

        Assert.assertNotNull(zipFile);
        Assert.assertTrue(Files.exists(zipFile));
    }

    @Test
    public void shouldZipFile() throws Exception {
        Path file = Files.createFile(joinTempDirWithFile(TEST_FILE1_TXT));

        Files.write(file, "TEST DATA FOR TEST FILE".getBytes());

        Path zip = Zipper.zipFile(file, joinTempDirWithFile(TEST_ZIP_FILE));

        Assert.assertNotNull(zip);
        Assert.assertTrue(Files.exists(zip));
    }

    @AfterClass
    public static void cleanUp() throws Exception {
        Files.deleteIfExists(joinTempDirWithFile(TEST_FILE1_TXT));
        Files.deleteIfExists(joinTempDirWithFile(TEST_FILE2_TXT));
        Files.deleteIfExists(joinTempDirWithFile(TEST_ZIP));
        Files.deleteIfExists(joinTempDirWithFile(TEST_ZIP_FILE));
    }
}