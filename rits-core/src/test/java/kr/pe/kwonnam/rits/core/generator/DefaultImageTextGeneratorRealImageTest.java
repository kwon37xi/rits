package kr.pe.kwonnam.rits.core.generator;

import kr.pe.kwonnam.rits.core.ImageFormat;
import kr.pe.kwonnam.rits.core.ImageTextGenerator;
import kr.pe.kwonnam.rits.core.ImageTextParams;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class DefaultImageTextGeneratorRealImageTest {
    private final Logger log = LoggerFactory.getLogger(DefaultImageTextGeneratorRealImageTest.class);

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

            log.debug("####### Font : {}", fontname);
            ImageTextParams params = new ImageTextParams();
            params.setFont(font);
            params.setWidth(1000);
            params.setBackgroundColor(Color.GRAY);
            params.setForegroundColor(Color.PINK);
            ImageTextGenerator generator = new DefaultImageTextGenerator(params);

            generator.writeln(TEXT);
            generator.writeln(TEXT);
            generator.writeln(TEXT);

            generator.generateImage(ImageFormat.PNG, new File(parent, fontname + ".png"));
        }
    }
}
