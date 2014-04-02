package kr.pe.kwonnam.rits.core;

import java.awt.*;
import java.text.BreakIterator;

/**
 * 이미지 생성에 필요한 정보 전달용 데이터 클래스
 */
public class ImageTextParams {
    public static final int MIN_WIDTH = 1;
    public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    public static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
    public static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 12);

    private Font font = DEFAULT_FONT;
    private Margin margin = Margin.NO_MARGIN;
    private int lineHeight = 0;
    private int width = MIN_WIDTH;

    private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
    private Color foregroundColor = DEFAULT_FOREGROUND_COLOR;

    private boolean antialiasing = true;
    private boolean useFractionalMatrics = true;

    /** {@link kr.pe.kwonnam.rits.core.ImageTextGenerator#writelnWrapped(String)} 호출시에
     * 새 줄로 구분하는 규칙을 지정한다. 기본은 단어 단위.
     */
    private BreakIterator breakIterator = BreakIterator.getWordInstance();

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Margin getMargin() {
        return margin;
    }

    public void setMargin(Margin margin) {
        this.margin = margin;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public boolean isAntialiasing() {
        return antialiasing;
    }

    public void setAntialiasing(boolean antialiasing) {
        this.antialiasing = antialiasing;
    }

    public boolean isUseFractionalMatrics() {
        return useFractionalMatrics;
    }

    public void setUseFractionalMatrics(boolean useFractionalMatrics) {
        this.useFractionalMatrics = useFractionalMatrics;
    }

    public BreakIterator getBreakIterator() {
        return breakIterator;
    }

    public void setBreakIterator(BreakIterator breakIterator) {
        this.breakIterator = breakIterator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageTextParams that = (ImageTextParams) o;

        if (antialiasing != that.antialiasing) return false;
        if (lineHeight != that.lineHeight) return false;
        if (useFractionalMatrics != that.useFractionalMatrics) return false;
        if (width != that.width) return false;
        if (backgroundColor != null ? !backgroundColor.equals(that.backgroundColor) : that.backgroundColor != null)
            return false;
        if (breakIterator != null ? !breakIterator.equals(that.breakIterator) : that.breakIterator != null)
            return false;
        if (font != null ? !font.equals(that.font) : that.font != null) return false;
        if (foregroundColor != null ? !foregroundColor.equals(that.foregroundColor) : that.foregroundColor != null)
            return false;
        if (margin != null ? !margin.equals(that.margin) : that.margin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = font != null ? font.hashCode() : 0;
        result = 31 * result + (margin != null ? margin.hashCode() : 0);
        result = 31 * result + lineHeight;
        result = 31 * result + width;
        result = 31 * result + (backgroundColor != null ? backgroundColor.hashCode() : 0);
        result = 31 * result + (foregroundColor != null ? foregroundColor.hashCode() : 0);
        result = 31 * result + (antialiasing ? 1 : 0);
        result = 31 * result + (useFractionalMatrics ? 1 : 0);
        result = 31 * result + (breakIterator != null ? breakIterator.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ImageTextParams{" +
                "font=" + font +
                ", margin=" + margin +
                ", lineHeight=" + lineHeight +
                ", width=" + width +
                ", backgroundColor=" + backgroundColor +
                ", foregroundColor=" + foregroundColor +
                ", antialiasing=" + antialiasing +
                ", useFractionalMatrics=" + useFractionalMatrics +
                ", breakIterator=" + breakIterator +
                '}';
    }
}