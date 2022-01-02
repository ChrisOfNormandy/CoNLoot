package com.github.chrisofnormandy.conloot;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern range = Pattern.compile("(\\w+)\\[(\\d+)-(\\d+)\\]");
    public static final Pattern dye = Pattern.compile("\\{dyed\\}");
    public static final Pattern modID = Pattern.compile("\\w+:");
    public static final Pattern replPattern = Pattern.compile("[\\w\\d]+>[\\w\\d]+");

    public static final String[] colors = { "red", "orange", "yellow", "lime", "green", "cyan", "light_blue", "blue",
            "purple", "magenta", "pink", "white", "light_gray", "gray", "black", "brown" };

    public static final HashMap<String, String> colorMap = new HashMap<String, String>() {
        {
            put("red", "255, 0, 0");
            put("orange", "255, 165, 0");
            put("yellow", "255, 255, 0");
            put("lime", "50, 205, 50");
            put("green", "0, 128, 0");
            put("cyan", "0, 128, 128");
            put("blue", "0, 0, 255");
            put("light_blue", "0, 191, 255");
            put("purple", "148, 0, 211");
            put("magenta", "255, 0, 255");
            put("pink", "255, 105, 180");
            put("white", "255, 255, 255");
            put("light_gray", "169, 169, 169");
            put("gray", "90, 90, 90");
            put("black", "0, 0, 0");
            put("brown", "139, 69, 19");
        }
    };

    public static String getColor(String color) {
        return colorMap.containsKey(color) ? colorMap.get(color) : "0, 0, 0";
    }
}
