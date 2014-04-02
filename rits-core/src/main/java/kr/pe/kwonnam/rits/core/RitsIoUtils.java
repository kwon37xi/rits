package kr.pe.kwonnam.rits.core;

import java.io.Closeable;
import java.io.IOException;

public class RitsIoUtils {

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
              closeable.close();
            } catch (IOException e) {
                // ignored
            }
        }
    }
}
