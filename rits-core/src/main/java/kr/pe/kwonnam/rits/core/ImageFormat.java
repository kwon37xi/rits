package kr.pe.kwonnam.rits.core;

/**
 * 이미지 형식
 */
public enum ImageFormat {
    JPEG("jpeg"),
    PNG("png");

    private String imageFormatName;

    ImageFormat(String imageFormatName) {
        this.imageFormatName = imageFormatName;
    }

    public String getImageFormatName() {
        return imageFormatName;
    }
}
