package kr.pe.kwonnam.rits.core;

import org.junit.Test;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DefaultImageTextGeneratorRealImageTest {
    private String RESOURCE_PREFIX = "/testfonts";
    private String[] FONT_NAMES = new String[]{
            "Daum_Regular",
            "Daum_SemiBold",
            "NanumGothic",
            "NanumGothicBold",
            "NanumGothicExtraBold",
            "NanumMyeongjo",
            "NanumMyeongjoBold",
            "NanumMyeongjoExtraBold",
            "SeoulHangangEB",
            "SeoulNamsanEB"
    };

    public static final String TEXT = "Lorem ipsum dolor sit amet, 동해물과 백두산이 마르고 닳도록 1234576890";
    private Font getFont(String fontname) throws IOException, FontFormatException {
        InputStream is = this.getClass().getResourceAsStream(RESOURCE_PREFIX + "/" + fontname + ".ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
        return font.deriveFont(Font.PLAIN, 26F);
    }

    @Test
    public void generate() throws Exception {
        File parent = new File("build");
        if (!parent.exists()) {
            parent.mkdirs();
        }

        for (String fontname: FONT_NAMES) {
            Font font = getFont(fontname);

            ImageTextParams params = new ImageTextParams(font, Margin.NO_MARGIN, 1000);
            ImageTextGenerator generator = new DefaultImageTextGenerator(params);

            generator.writeln(TEXT);

            generator.generateImage(ImageFormat.PNG, new File(parent, fontname + ".png"));
        }
    }
}
