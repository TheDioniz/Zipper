package pl.devdioniz.compressor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * The class serves as an util for compressing files
 * it can compress one file or bunch of them and pack
 * to .zip file.
 *
 * @author TheDioniz, created on 30.08.2017.
 */
public final class Zipper {

    private static final Logger log = LoggerFactory.getLogger(Zipper.class);

    private Zipper() {
    }

    /**
     * compress all files which are added to the class and
     * puts all files in one .zip file
     *
     * @param destZipPath destination path to zip file
     * @return null if nothing to compress
     */
    public static Path zipAllFiles(Set<Path> files, Path destZipPath) throws IOException {

        if (files.isEmpty()) {
            log.warn("Nothing to zip.");
            return null;
        }

        if (destZipPath == null) {
            throw new NullPointerException("DESTINATION ZIP PATH cannot be null!");
        }

        try (
                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipPath.toString()))
        ) {

            for (Path path : files) {

                if (path.toFile().exists()) {

                    byte[] buffer = new byte[1024];

                    try (
                            FileInputStream fis = new FileInputStream(path.toFile())
                    ) {
                        ZipEntry ze = new ZipEntry(path.getFileName().toString());

                        try {
                            int len;

                            log.info("zipping file: {}", ze.getName());
                            zos.putNextEntry(ze);

                            while ((len = fis.read(buffer)) > 0) {
                                zos.write(buffer, 0, len);
                            }
                            zos.closeEntry();

                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    } catch (FileNotFoundException e) {
                        log.error("Cannot zip file: {} it does not exist.", path);
                        log.error(e.getMessage());
                    }
                }
            }
            log.info("Saved to: {}", destZipPath);

        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        return destZipPath;
    }

    /**
     * gets one file and zips it to destination zip
     *
     * @param filePath path to file for zipping
     * @param destZipFile destination path of zipped file
     * @return path to zipped file
     */
    public static Path zipFile(Path filePath, Path destZipFile) throws IOException {

        if (destZipFile == null) {
            destZipFile = Paths.get(filePath.toString().concat(".zip"));
        }

        Set<Path> files = new HashSet<>();
        files.add(filePath);

        return zipAllFiles(files, destZipFile);
    }

    /**
     * zips one file and creates the zip with original
     * filename and .zip suffix
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Path zipFile(Path filePath) throws IOException {
        return zipFile(filePath, null);
    }
}
