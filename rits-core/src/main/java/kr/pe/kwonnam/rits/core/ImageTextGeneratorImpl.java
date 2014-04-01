package kr.pe.kwonnam.rits.core;

import java.awt.*;
import java.io.*;

/**
 * 텍스트를 이미지로 생성하는 구현체.
 * <p/>
 * <strong>This is NOT thread safe.</strong>
 */
public class ImageTextGeneratorImpl implements ImageTextGenerator {
    private Font font;
    private boolean antialiasing = true;
    private boolean useFractionalMatrics = true;
    private int imageWidth;
    private Margin margin;

    public ImageTextGeneratorImpl(Font font, boolean antialiasing, boolean useFractionalMatrics, int imageWidth, Margin margin) {
        this.font = font;
        this.antialiasing = antialiasing;
        this.useFractionalMatrics = useFractionalMatrics;
        this.imageWidth = imageWidth;
        this.margin = margin;
    }

    public Font getFont() {
        return font;
    }

    public boolean isAntialiasing() {
        return antialiasing;
    }

    public boolean isUseFractionalMatrics() {
        return useFractionalMatrics;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public Margin getMargin() {
        return margin;
    }

    @Override
    public void writeln(String string) {

    }

    @Override
    public void writelnWrapped(String string) {

    }

    @Override
    public void generateImage(ImageFormat imageFormat, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Could not generate image. - " + e.getMessage(), e);
        }

        generateImage(imageFormat, fos);
    }

    @Override
    public void generateImage(ImageFormat imageFormat, OutputStream outputStream) {
        try {
            // do the job
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ignored) {
                    // ignored
                }
            }
        }
    }
}