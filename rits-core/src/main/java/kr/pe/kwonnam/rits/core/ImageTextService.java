package kr.pe.kwonnam.rits.core;

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
import java.io.File;
import java.text.AttributedString;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class ImageTextService {
    private static final Logger log = LoggerFactory.getLogger(ImageTextGenerator.class);

    private Collection<FontInfo> fontInfos;

    private Map<String, Font> fonts;

    public ImageTextService(Collection<FontInfo> fontInfos) {
        this.fontInfos = fontInfos;
    }

    public Collection<FontInfo> getFontInfos() {
        return Collections.unmodifiableCollection(fontInfos);
    }

    public Map<String, Font> getFonts() {
        return Collections.unmodifiableMap(fonts);
    }

    public void setFonts(Map<String, Font> fonts) {
        this.fonts = fonts;
    }

    public ImageTextGenerator createImageTextGenerator(String fontName, int fontSizePixel, boolean antialiasing, boolean useFractionalMatrics,
                                                       int imageWidth, Margin margin) {

        return null;
    }

    public ImageTextGenerator createImageTextGenerator(String fontName, int fontSizePixel, int imageWidth, Margin margin) {

        return null;
    }

    public static void main(String[] args) throws Exception {
        String sampleText = "Sample Text 샘플 텍스트\n 좀 길게 써보께요. Hello Coupang!";
        File file = new File("sample.jpg");

        Font font = Font.createFont(Font.TRUETYPE_FONT, new File("/home/kwon37xi/projects/rits/rits-core/src/test/resources/nanumfonts/NanumMyeongjoExtraBold.ttf")).deriveFont(Font.PLAIN, 26F);

        FontRenderContext frc = new FontRenderContext(null, true, true);

        AttributedString as = new AttributedString(sampleText);
        as.addAttribute(TextAttribute.FONT, font);

        LineBreakMeasurer measurer = new LineBreakMeasurer(as.getIterator(), frc);

        TextLayout layout = new TextLayout(sampleText, font, frc);

        Rectangle2D bounds = font.getStringBounds(sampleText, frc);
        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        log.info("width : {}, height : {}", width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLUE);
        g.setFont(font);

        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString(sampleText, (float) bounds.getX(), (float) -bounds.getY());

        g.dispose();

        ImageIO.write(image, "jpeg", file);
    }


}