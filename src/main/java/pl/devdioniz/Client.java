package pl.devdioniz;

import pl.devdioniz.compressor.Zipper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * @author TheDioniz, created on 30.08.2017.
 */
public class Client {

    public static void main(String[] args) throws IOException {


        Set<Path> fileSet = new HashSet<>();
        fileSet.add(Paths.get("C:\\Users\\Denis\\.IntelliJIdea2017.2\\system\\log\\idea.log.1"));
        fileSet.add(Paths.get("C:\\Users\\Denis\\.IntelliJIdea2017.2\\system\\log\\idea.log.2"));

        Zipper.zipAllFiles(fileSet, Paths.get("C:\\temp\\MYzip.zip"));
//        zipper.zipAllFiles();

    }
}
