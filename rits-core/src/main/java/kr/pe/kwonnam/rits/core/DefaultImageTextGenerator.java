package kr.pe.kwonnam.rits.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Default image text generation implementation.
 * This implementation does not support transparent background.
 * <p/>
 * <p/>
 * <strong>This is NOT thread safe.</strong>
 */
public class DefaultImageTextGenerator implements ImageTextGenerator {
    private final Logger log = LoggerFactory.getLogger(DefaultImageTextGenerator.class);

    private ImageTextParams params;

    private List<TextLayout> textLayouts;

    private FontRenderContext fontRenderContext;

    public DefaultImageTextGenerator(ImageTextParams params) {
        if (params == null) {
            throw new IllegalArgumentException("params cannot be null.");
        }

        this.params = params;
        textLayouts = new ArrayList<TextLayout>();
        fontRenderContext = new FontRenderContext(null, params.isAntialiasing(), params.isUseFractionalMatrics());
    }

    @Override
    public void writeln(String string) {
        textLayouts.add(new TextLayout(string, params.getFont(), fontRenderContext));
    }

    @Override
    public void writelnWrapped(String string) {
        // TODO
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
            int height = 50;
            BufferedImage image = createBufferedImage(height);
            Graphics2D g2d = image.createGraphics();
            populateBackgroundAndForegroundColors(g2d, height);
            populateFont(g2d);
            populateRenderingHints(g2d);

            drawText(g2d);

            g2d.dispose();

            ImageIO.write(image, imageFormat.getImageFormatName(), outputStream);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to generate image. ImageFormat - " + imageFormat, ex);
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

    protected BufferedImage createBufferedImage(int height) {
        return new BufferedImage(params.getWidth(), height, BufferedImage.TYPE_INT_RGB);
    }

    protected void populateBackgroundAndForegroundColors(Graphics2D g2d, int height) {
        Color backgroundColor = params.getBackgroundColor();
        Color foregroundColor = params.getForegroundColor();

        log.debug("Background : {}, Foreground : {}", backgroundColor, foregroundColor);

        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, params.getWidth(), height);

        g2d.setColor(foregroundColor);
    }

    protected void populateFont(Graphics2D g2d) {
        g2d.setFont(params.getFont());
    }

    protected void populateRenderingHints(Graphics2D g2d) {
        Object fractionalMetricsValue = params.isUseFractionalMatrics() ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
        Object antialiasingValue = params.isAntialiasing() ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;

        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetricsValue);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antialiasingValue);
    }


    private void drawText(Graphics2D g2d) {
        for (TextLayout textLayout : textLayouts) {

            Rectangle2D bounds = textLayout.getBounds();

            log.debug("text bound : {}", bounds);
            textLayout.draw(g2d, (float)bounds.getX(), (float)-bounds.getY());
        }


    }
}