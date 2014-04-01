package kr.pe.kwonnam.rits.core;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ImageTextTest {

    @Test
    public void currentDir() throws IOException {
        System.out.println("Current Dir : " + new File(".").getCanonicalPath());
    }
}
