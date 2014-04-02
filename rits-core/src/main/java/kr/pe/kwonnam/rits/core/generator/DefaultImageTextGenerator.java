package kr.pe.kwonnam.rits.core.generator;


import kr.pe.kwonnam.rits.core.ImageFormat;
import kr.pe.kwonnam.rits.core.ImageTextGenerator;
import kr.pe.kwonnam.rits.core.ImageTextParams;
import kr.pe.kwonnam.rits.core.RitsIoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.FontMetrics;
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
    public void newLine() {
        // TODO
    }

    @Override
    public void newLine(int lines) {
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
            int height = 400;
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
            RitsIoUtils.closeQuietly(outputStream);
        }
    }

    protected BufferedImage createBufferedImage(int height) {
        return new BufferedImage(params.getWidth(), height, BufferedImage.TYPE_INT_ARGB);
    }

    protected void populateBackgroundAndForegroundColors(Graphics2D g2d, int height) {
        Color backgroundColor = params.getBackgroundColor();
        Color foregroundColor = params.getForegroundColor();

        log.debug("Background : {}, Foreground : {}", backgroundColor, foregroundColor);

        g2d.setBackground(backgroundColor);
        g2d.setColor(foregroundColor);
        g2d.clearRect(0, 0, params.getWidth(), height);
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

        // FIXME lineheight, margin 등을 반영해야함.
        FontMetrics fontMetrics = g2d.getFontMetrics(params.getFont());
        Rectangle2D maxCharBounds = fontMetrics.getMaxCharBounds(g2d);
        // macCharBounds가 시작 x,y좌표의 핵심 값인듯.

        log.debug("FontMaxCharBounds : {}", maxCharBounds);
        log.debug("FontMaxCharBounds.getBounds : {}", maxCharBounds.getBounds());


        for (int i = 0; i < textLayouts.size(); i++){
            TextLayout textLayout = textLayouts.get(i);
            Rectangle2D bounds = textLayout.getBounds();

            textLayout.draw(g2d, (float) bounds.getX(), (float) ((-maxCharBounds.getY()) + (bounds.getHeight() * i)));
        }


    }
}