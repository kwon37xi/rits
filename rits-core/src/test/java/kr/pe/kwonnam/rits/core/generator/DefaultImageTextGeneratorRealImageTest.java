package kr.pe.kwonnam.rits.core.generator;

import kr.pe.kwonnam.rits.core.ImageFormat;
import kr.pe.kwonnam.rits.core.ImageTextGenerator;
import kr.pe.kwonnam.rits.core.ImageTextParams;
import kr.pe.kwonnam.rits.core.Margin;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;

public class DefaultImageTextGeneratorRealImageTest {
    private final Logger log = LoggerFactory.getLogger(DefaultImageTextGeneratorRealImageTest.class);

    private static final String RESOURCE_PREFIX = "/testfonts";
    private static final String[] FONT_NAMES = new String[]{
            "NanumGothicBold",
            "NanumMyeongjoBold",
            "NanumBrush",
            "NanumPen"
    };

    public static final String TEXT = "Lorem ipsum dolor sit amet, 동해물과 백두산이 마르고 닳도록 1234576890";

    private File parent = new File("build");

    @Before
    public void setUp() {
        if (!parent.exists()) {
            parent.mkdir();
        }
    }

    private Font getFont(String fontname) throws IOException, FontFormatException {
        InputStream is = this.getClass().getResourceAsStream(RESOURCE_PREFIX + "/" + fontname + ".ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
        return font.deriveFont(Font.PLAIN, 26F);
    }

    @Test
    public void generate() throws Exception {


        for (String fontname : FONT_NAMES) {
            Font font = getFont(fontname);

            log.debug("####### Font : {}", fontname);
            ImageTextParams params = new ImageTextParams();
            params.setFont(font);
            params.setWidth(700);
            params.setBackgroundColor(Color.WHITE);
            params.setForegroundColor(Color.BLACK);
            params.setLineHeight(7);
            params.setBreakIterator(BreakIterator.getCharacterInstance());
            ImageTextGenerator generator = new DefaultImageTextGenerator(params);

            generator.writeln(TEXT);
            generator.newLine(3);
            generator.writeln(TEXT);
            generator.writeln(TEXT);
            generator.writeln("---------------");
            generator.writelnWrapped(TEXT);
            generator.writelnWrapped("동해 물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세. " +
                    "무궁화 삼천리 화려강산 대한 사람, 대한으로 길이 보전하세. " +
                    "남산 위에 저 소나무, 철갑을 두른 듯 바람서리 불변함은 우리 기상일세. " +
                    "무궁화 삼천리 화려강산 대한 사람, 대한으로 길이 보전하세.\n" +
                    "가을 하늘 공활한데 높고 구름 없이 밝은 달은 우리 가슴 일편단심일세. " +
                    "무궁화 삼천리 화려강산 대한 사람, 대한으로 길이 보전하세.\n" +
                    "이 기상과 이 맘으로 충성을 다하여 괴로우나 즐거우나 나라 사랑하세. " +
                    "무궁화 삼천리 화려강산 대한 사람, 대한으로 길이 보전하세.");

            generator.generateImage(ImageFormat.PNG, new File(parent, fontname + ".png"));
            generator.generateImage(ImageFormat.JPEG, new File(parent, fontname + ".jpg"));
        }
    }

    @Test
    public void generateSimple() throws Exception {
        Font font = getFont("NanumMyeongjoBold");

        // Set image attributes.
        ImageTextParams params = new ImageTextParams();
        params.setFont(font);
        params.setWidth(500); // image width
        params.setBackgroundColor(Color.decode("#f0f0f0"));
        params.setForegroundColor(Color.BLUE);
        params.setMargin(new Margin(25, 25));
        params.setLineHeight(5); // line height pixel

        params.setBreakIterator(BreakIterator.getWordInstance()); // Line wrapping rule.

        ImageTextGenerator generator = new DefaultImageTextGenerator(params);

        // add texts
        generator.writeln("Realtime Image Text Service");
        generator.writeln("실시간 이미지 텍스트 생성 서비스");
        generator.newLine();
        generator.writelnWrapped("Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Duis sapien nibh, auctor at urna non, rutrum fermentum metus.");

        // generate image file
        generator.generateImage(ImageFormat.PNG, new File(parent, "example.png"));
    }
}
