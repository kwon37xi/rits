# Realtime Image Text Service

Realtime Image Text Service is for creating heading image text from any TTF fonts.
Actually this is not for usual long text.

If you want like long text data image, you'd better refer to [TextImageGenerator](https://github.com/jcraane/textimagegenerator).

## rits-core API
* Refer to `DefaultImageTextGeneratorRealImageTest.java`
* This api support flexible image height. It will calculate the strings' total height, then decide the image size.

### Requirements
* Java 6

### Example

```java
// Set font infomation
int style = Font.PLAIN;
float pixel = 26F;
Font font = Font.createFont(Font.TRUETYPE_FONT, new File("/path/to/ttf/font/yourfont.ttf")).deriveFont(style, pixel);

// Set image attributes.
ImageTextParams params = new ImageTextParams();
params.setFont(font);
params.setWidth(500); // image width
params.setBackgroundColor(Color.WHITE);
params.setForegroundColor(Color.BLACK);
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
generator.generateImage(ImageFormat.PNG, new File("example.png"));
```

You will have like the following image.

![generated image](https://raw.githubusercontent.com/kwon37xi/rits/master/example.png "generated lorem ipsum image")

### Caution
* New lines(\n) in the string are ignored. If you want a new line, you must call `ImageTextGenerator.newLine()` explicitly.

## rits-server
TODO

rits-server is a web application which is based on rits-core.
This application servers request from another application or browser to generate images on the fly.

### Requirements
* Java 6
* Servlet container which support java servlet 3.0. (Tomcat 7)
