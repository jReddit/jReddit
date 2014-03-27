package com.github.jreddit.testsupport;

import org.json.simple.JSONArray;

public class JsonHelpers {
    public static JSONArray jsonArrayOf(Object... args) {
        JSONArray array = new JSONArray();
        for (Object o : args) {
            array.add(o);
        }
        return array;
    }

    public static JSONArray emptyJsonArray() {
        return new JSONArray();
    }
}
