package it.inail.geodnotifapp.security.utils;

import org.springframework.util.ObjectUtils;

public final class PathUtils {

    private PathUtils() {
        throw new IllegalAccessError("Utility class");
    }

    public static String getPath(String path) {
    	if (ObjectUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path is empty");
        }
        if (path.charAt(0) == '/') {
            return path;
        }
        return "/" + path;
    }
}
