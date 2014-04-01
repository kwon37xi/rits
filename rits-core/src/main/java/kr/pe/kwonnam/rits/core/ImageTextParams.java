package kr.pe.kwonnam.rits.core;

import java.awt.Color;
import java.awt.Font;

/**
 * 이미지 생성에 필요한 정보 전달용 데이터 클래스
 */
public class ImageTextParams {
    public static final int MIN_WIDTH = 1;
    public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    public static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;

    private Font font;
    private Margin margin;
    private int width;


    private Color backgroundColor;
    private Color foregroundColor;

    private boolean antialiasing;
    private boolean useFractionalMatrics;

    public ImageTextParams(Font font, Margin margin, int width, boolean antialiasing, boolean useFractionalMatrics, Color backgroundColor, Color foregroundColor) {
        if (font == null) {
            throw new IllegalArgumentException("font cannot be null");
        }

        if (margin == null) {
            throw new IllegalArgumentException("margin cannot be null.");
        }

        if (width < MIN_WIDTH) {
            throw new IllegalArgumentException("width cannot be less than " + MIN_WIDTH);
        }

        this.font = font;
        this.margin = margin;
        this.width = width;
        this.antialiasing = antialiasing;
        this.useFractionalMatrics = useFractionalMatrics;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
    }

    public ImageTextParams(Font font, Margin margin, int width) {
        this(font, margin, width, true, true, DEFAULT_BACKGROUND_COLOR, DEFAULT_FOREGROUND_COLOR);
    }

    public Font getFont() {
        return font;
    }

    public Margin getMargin() {
        return margin;
    }

    public int getWidth() {
        return width;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public boolean isAntialiasing() {
        return antialiasing;
    }

    public boolean isUseFractionalMatrics() {
        return useFractionalMatrics;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageTextParams that = (ImageTextParams) o;

        if (antialiasing != that.antialiasing) return false;
        if (useFractionalMatrics != that.useFractionalMatrics) return false;
        if (width != that.width) return false;
        if (!backgroundColor.equals(that.backgroundColor)) return false;
        if (!font.equals(that.font)) return false;
        if (!foregroundColor.equals(that.foregroundColor)) return false;
        if (!margin.equals(that.margin)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = font.hashCode();
        result = 31 * result + margin.hashCode();
        result = 31 * result + width;
        result = 31 * result + backgroundColor.hashCode();
        result = 31 * result + foregroundColor.hashCode();
        result = 31 * result + (antialiasing ? 1 : 0);
        result = 31 * result + (useFractionalMatrics ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ImageTextParams{" +
                "font=" + font +
                ", margin=" + margin +
                ", width=" + width +
                ", backgroundColor=" + backgroundColor +
                ", foregroundColor=" + foregroundColor +
                ", antialiasing=" + antialiasing +
                ", useFractionalMatrics=" + useFractionalMatrics +
                '}';
    }
}