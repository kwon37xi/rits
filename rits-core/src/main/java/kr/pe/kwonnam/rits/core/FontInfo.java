package kr.pe.kwonnam.rits.core;

import java.io.InputStream;

/**
 * 글꼴파일 정보
 */
public class FontInfo {
    private String name;
    private InputStream inputStream;

    public FontInfo(String name, InputStream inputStream) {
        this.name = name;
        this.inputStream = inputStream;
    }

    public String getName() {
        return name;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
