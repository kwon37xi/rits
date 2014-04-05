package kr.pe.kwonnam.rits.core;

import java.io.File;
import java.io.OutputStream;

public interface ImageTextGenerator {

    /**
     * Write text with new line. It does NOT wrap string, even though the string's width over the image width.
     * @param string string
     */
    void writeln(String string);


    /**
     * Write text with new line. It wrap string when the string's width over the image width.
     * @param string string
     */
    void writelnWrapped(String string);

    /**
     * add new line.
     */
    void newLine();

    /**
     * add new line <code>lines</code> times.
     * @param lines count of new line.
     */
    void newLine(int lines);

    /**
     * Generate image file with {@link ImageFormat} type.
     * @param imageFormat image format. {@link ImageFormat}.
     * @param file image file
     */
    void generateImage(ImageFormat imageFormat, File file);

    /**
     * Generate image file with {@link ImageFormat} type.
     * @param imageFormat image format. {@link ImageFormat}.
     * @param outputStream image output stream
     */
    void generateImage(ImageFormat imageFormat, OutputStream outputStream);
}
