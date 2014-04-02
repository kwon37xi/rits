package kr.pe.kwonnam.rits.core.generator;


import kr.pe.kwonnam.rits.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
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

    private final Rectangle2D maxCharBounds;

    /* New line marker. This line text is not rendered. */
    private TextLayout newLineMarkerTextLayout;

    public DefaultImageTextGenerator(ImageTextParams params) {
        if (params == null) {
            throw new IllegalArgumentException("params cannot be null.");
        }

        this.params = params;
        textLayouts = new ArrayList<TextLayout>();
        fontRenderContext = new FontRenderContext(null, params.isAntialiasing(), params.isUseFractionalMatrics());
        maxCharBounds = params.getFont().getMaxCharBounds(fontRenderContext);
        newLineMarkerTextLayout = new TextLayout("\n", params.getFont(), fontRenderContext);
    }

    @Override
    public void writeln(String string) {
        textLayouts.add(new TextLayout(string, params.getFont(), fontRenderContext));
    }

    @Override
    public void writelnWrapped(String string) {
        AttributedString attributedString = new AttributedString(string);
        attributedString.addAttribute(TextAttribute.FONT, params.getFont());
        AttributedCharacterIterator textInterator = attributedString.getIterator();
        LineBreakMeasurer lineBreakMeasurer = new LineBreakMeasurer(textInterator, params.getBreakIterator(), fontRenderContext);

        int wrappingWidth = params.getWidth() - params.getMargin().getLeft() - params.getMargin().getRight();

        while (lineBreakMeasurer.getPosition() < textInterator.getEndIndex()) {
            TextLayout textLayout = lineBreakMeasurer.nextLayout(wrappingWidth);
            textLayouts.add(textLayout);
        }
    }

    @Override
    public void newLine() {
        newLine(1);
    }

    @Override
    public void newLine(int lines) {
        for (int i = 0; i < lines; i++) {
            textLayouts.add(newLineMarkerTextLayout);
        }
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
            float height = calculateHeight();
            BufferedImage image = createBufferedImage((int) height);
            Graphics2D g2d = image.createGraphics();
            populateBackgroundAndForegroundColors(g2d, (int) height);
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

    float calculateHeight() {
        float height = 0.0F;

        Margin margin = params.getMargin();
        height += margin.getTop() + margin.getBottom() + (-maxCharBounds.getY());

        for (int i = 0; i < textLayouts.size(); i++) {
            height += calculateNextHeight(i);
        }

        return height;
    }

    private float calculateNextHeight(int index) {
        float nextHeight = 0;

        if (index != 0) {
            nextHeight = params.getLineHeight() + (float) maxCharBounds.getHeight();
        }
        return nextHeight;
    }

    protected BufferedImage createBufferedImage(int height) {
        return new BufferedImage(params.getWidth(), height, BufferedImage.TYPE_INT_RGB);
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
        float y = calculateStartingY();

        log.debug("base y : {}", y);

        for (int i = 0; i < textLayouts.size(); i++) {
            TextLayout textLayout = textLayouts.get(i);
            Rectangle2D bounds = textLayout.getBounds();

            float x = calculateCurrentX(params.getMargin(), bounds);
            float nextHeight = calculateNextHeight(i);

            y += nextHeight;

            log.debug("current({}) y : {}", i, y);
            textLayout.draw(g2d, x, y);
        }
    }

    private float calculateStartingY() {
        float y = params.getMargin().getTop();

        // drawString은 좌표 기준점이 글자의 좌상단이 아니라 baseLine이다.
        // 따라서 baseLine만큼 아래로 좌표를 잡아야한다.
        y += (-maxCharBounds.getY());
        return y;
    }

    private float calculateCurrentX(Margin margin, Rectangle2D bounds) {
        return (float) (bounds.getX() + margin.getLeft());
    }
}