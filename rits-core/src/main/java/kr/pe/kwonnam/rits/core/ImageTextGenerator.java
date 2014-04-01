package kr.pe.kwonnam.rits.core;

import java.io.File;
import java.io.OutputStream;

public interface ImageTextGenerator {

    void writeln(String string);

    void writelnWrapped(String string);

    void generateImage(ImageFormat imageFormat, File file);

    void generateImage(ImageFormat imageFormat, OutputStream outputStream);
}
