package com.github.chrisofnormandy.conloot;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern range = Pattern.compile("(\\w+)\\[(\\d+)-(\\d+)\\]");
    public static final Pattern dye = Pattern.compile("\\{dyed\\}");
    public static final Pattern modID = Pattern.compile("\\w+:");

    public static final String[] colors = {
        "red",
        "orange",
        "yellow",
        "lime",
        "green",
        "cyan",
        "light_blue",
        "blue",
        "purple",
        "magenta",
        "pink",
        "white",
        "light_gray",
        "gray",
        "black",
        "brown"
    };

    public static final HashMap<String, String> colorMap = new HashMap<String, String>();

    static {
        colorMap.put("red", "255, 0, 0");
        colorMap.put("orange", "255, 165, 0");
        colorMap.put("yellow", "255, 255, 0");
        colorMap.put("lime", "50, 205, 50");
        colorMap.put("green", "0, 128, 0");
        colorMap.put("cyan", "0, 128, 128");
        colorMap.put("blue", "0, 0, 255");
        colorMap.put("light_blue", "0, 191, 255");
        colorMap.put("purple", "148, 0, 211");
        colorMap.put("magenta", "255, 0, 255");
        colorMap.put("pink", "255, 105, 180");
        colorMap.put("white", "255, 255, 255");
        colorMap.put("light_gray", "169, 169, 169");
        colorMap.put("gray", "90, 90, 90");
        colorMap.put("black", "0, 0, 0");
        colorMap.put("brown", "139, 69, 19");
    }
}
