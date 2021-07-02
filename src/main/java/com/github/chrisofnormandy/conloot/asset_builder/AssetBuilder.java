package com.github.chrisofnormandy.conloot.asset_builder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import com.github.chrisofnormandy.conlib.collections.JsonBuilder;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonArray;
import com.github.chrisofnormandy.conlib.collections.JsonBuilder.JsonObject;
import com.github.chrisofnormandy.conlib.common.Files;
import com.github.chrisofnormandy.conloot.Main;

import net.minecraftforge.fml.loading.FMLPaths;

public class AssetBuilder {
    private static BufferedImage getImage(String name) throws IOException {
        return ImageIO.read(FMLPaths.GAMEDIR.get().resolve("defaultconfigs/textures/" + name + ".png").toFile());
    }

    private static int getIndex(int colors, int shades, int shade) {
        int v = shades / colors;
        if (v == 0) // If there are more colors than shades - prevent divide-by-zero error.
            v = 1;
        int k = (shade - (shade % v)) / v;
        return k >= colors ? colors - 1 : k;
    }

    private static Integer[] getPixel(int tempRgba, int baseRgba, String mode, List<Integer> shades, String[] colors, Boolean templateShading) {
        Integer[] rgba = new Integer[]{0, 0, 0, 0};

        int r = 0, g = 0, b = 0, a = 0;
        int rT, gT, bT, aT;
        int rB, gB, bB, aB;

        aT = (tempRgba >> 24) & 255;
        rT = (tempRgba >> 16) & 255;
        gT = (tempRgba >> 8) & 255;
        bT = tempRgba & 255;

        aB = (baseRgba >> 24) & 255;
        rB = (baseRgba >> 16) & 255;
        gB = (baseRgba >> 8) & 255;
        bB = baseRgba & 255;       

        int i = getIndex(colors.length, shades.size(), shades.indexOf(tempRgba));

        if (aT == 0 && aB == 0) 
            return rgba;

        switch (mode) {
            case "sharp": {
                String[] clr = colors[i].split(",\\s?");

                if (clr.length == 3) {
                    if (aT > 0) {
                        if (templateShading) {
                            r = Integer.parseInt(clr[0]) * rT / 255;
                            g = Integer.parseInt(clr[1]) * gT / 255;
                            b = Integer.parseInt(clr[2]) * bT / 255;
                            a = aT;
                        }
                        else if (aB > 0) {
                            r = Integer.parseInt(clr[0]) * rB / 255;
                            g = Integer.parseInt(clr[1]) * gB / 255;
                            b = Integer.parseInt(clr[2]) * bB / 255;
                            a = aB;
                        }
                    }
                    else {
                        r = rB;
                        g = gB;
                        b = bB;
                        a = aB;
                    }
                }
                else {
                    r = templateShading && aT > 0 ? (Integer.parseInt(clr[0]) * rT / 255) : aB > 0 ? (Integer.parseInt(clr[0]) * rB / 255) : 0;
                    g = r;
                    b = r;
                    a = aT > 0 ? aT : aB > 0 ? aB : 0;
                }

                break;
            }
            case "gradient": {
                int c = shades.size() / colors.length;

                // If c = 3:
                // 0 = Use full i => 3/3 * i + 0 / 3 * (i + 1)
                // 1 = 2/3 * i + 1/3 * (i + 1)
                // 2+ etc...

                double y = i % c; // Color B
                double x = c - y; // Color A

                String[] clrA = colors[i].split(",\\s?");
                String[] clrB = i < colors.length - 1 ? colors[i + 1].split(",\\s?") : colors[i].split(",\\s?");

                Integer[] clr = new Integer[3];

                if (clrA.length == 3) {
                    if (clrB.length == 3) {
                        clr[0] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                        clr[1] = (int)Math.round((x / c * Integer.parseInt(clrA[1])) + (y / c * Integer.parseInt(clrB[1])));
                        clr[2] = (int)Math.round((x / c * Integer.parseInt(clrA[2])) + (y / c * Integer.parseInt(clrB[2])));
                    }
                    else {
                        clr[0] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                        clr[1] = (int)Math.round((x / c * Integer.parseInt(clrA[1])) + (y / c * Integer.parseInt(clrB[0])));
                        clr[2] = (int)Math.round((x / c * Integer.parseInt(clrA[2])) + (y / c * Integer.parseInt(clrB[0])));
                    }
                }
                else {
                    if (clrB.length == 3) {
                        clr[0] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                        clr[1] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[1])));
                        clr[2] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[2])));
                    }
                    else {
                        clr[0] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                        clr[1] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                        clr[2] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                    }
                }

                if (aT > 0) {
                    if (templateShading) {
                        r = clr[0] * rT / 255;
                        g = clr[1] * gT / 255;
                        b = clr[2] * bT / 255;
                        a = aT;
                    }
                    else if (aB > 0) {
                        r = clr[0] * rB / 255;
                        g = clr[1] * gB / 255;
                        b = clr[2] * bB / 255;
                        a = aB;
                    }
                }
                else {
                    r = rB;
                    g = gB;
                    b = bB;
                    a = aB;
                }

                break;
            }
            case "spotted": {
                if (i == 0) {
                    String[] clr = colors[i].split(",\\s?");

                    if (clr.length == 3) {
                        if (aT > 0) {
                            if (templateShading) {
                                r = Integer.parseInt(clr[0]) * rT / 255;
                                g = Integer.parseInt(clr[1]) * gT / 255;
                                b = Integer.parseInt(clr[2]) * bT / 255;
                                a = aT;
                            }
                            else if (aB > 0) {
                                r = Integer.parseInt(clr[0]) * rB / 255;
                                g = Integer.parseInt(clr[1]) * gB / 255;
                                b = Integer.parseInt(clr[2]) * bB / 255;
                                a = aB;
                            }
                        }
                        else {
                            r = rB;
                            g = gB;
                            b = bB;
                            a = aB;
                        }
                    }
                    else {
                        r = templateShading && aT > 0 ? (Integer.parseInt(clr[0]) * rT / 255) : aB > 0 ? (Integer.parseInt(clr[0]) * rB / 255) : 0;
                        g = r;
                        b = r;
                        a = aT > 0 ? aT : aB > 0 ? aB : 0;
                    }
                }
                else {
                    int c = shades.size() / colors.length;
                    if (c == 0) // If there are more colors than shades - prevent divide-by-zero error.
                        c = 1;

                    // If c = 3:
                    // 0 = Use full i => 3/3 * i + 0 / 3 * (i + 1)
                    // 1 = 2/3 * i + 1/3 * (i + 1)
                    // 2+ etc...

                    double y = i % c; // Color B
                    double x = c - y; // Color A

                    String[] clrA = colors[i].split(",\\s?");
                    String[] clrB = i < colors.length - 1 ? colors[i + 1].split(",\\s?") : colors[i].split(",\\s?");

                    Integer[] clr = new Integer[3];

                    if (clrA.length == 3) {
                        if (clrB.length == 3) {
                            clr[0] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                            clr[1] = (int)Math.round((x / c * Integer.parseInt(clrA[1])) + (y / c * Integer.parseInt(clrB[1])));
                            clr[2] = (int)Math.round((x / c * Integer.parseInt(clrA[2])) + (y / c * Integer.parseInt(clrB[2])));
                        }
                        else {
                            clr[0] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                            clr[1] = (int)Math.round((x / c * Integer.parseInt(clrA[1])) + (y / c * Integer.parseInt(clrB[0])));
                            clr[2] = (int)Math.round((x / c * Integer.parseInt(clrA[2])) + (y / c * Integer.parseInt(clrB[0])));
                        }
                    }
                    else {
                        if (clrB.length == 3) {
                            clr[0] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                            clr[1] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[1])));
                            clr[2] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[2])));
                        }
                        else {
                            clr[0] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                            clr[1] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                            clr[2] = (int)Math.round((x / c * Integer.parseInt(clrA[0])) + (y / c * Integer.parseInt(clrB[0])));
                        }
                    }

                    if (aT > 0) {
                        if (templateShading) {
                            r = clr[0] * rT / 255;
                            g = clr[1] * gT / 255;
                            b = clr[2] * bT / 255;
                            a = aT;
                        }
                        else if (aB > 0) {
                            r = clr[0] * rB / 255;
                            g = clr[1] * gB / 255;
                            b = clr[2] * bB / 255;
                            a = aB;
                        }
                    }
                    else {
                        r = rB;
                        g = gB;
                        b = bB;
                        a = aB;
                    }
                }

                break;
            }
        }

        rgba[0] = r;
        rgba[1] = g;
        rgba[2] = b;
        rgba[3] = a;

        return rgba;
    }

    public static String createImage(String path, String name, String[] textureList, String[] overlayList, String[] colorList, String mode, Boolean templateShading) {
        String filename = name + ".png";
        Path p = FMLPaths.GAMEDIR.get().resolve(path);

        if (!p.toFile().exists())
            p.toFile().mkdirs();

        File absOutFile = p.resolve(filename).toFile();

        BufferedImage[] textures = new BufferedImage[textureList.length];
        BufferedImage[] overlays = overlayList.length > 0 ? new BufferedImage[overlayList.length] : new BufferedImage[1];

        Main.LOG.debug("Creating new texture for " + name + " using " + textureList.length + " base textures and " + overlayList.length + " overlays.");

        for (int i = 0; i < textureList.length; i++) {
            try {
                textures[i] = getImage(textureList[i]);
            } catch (IOException err) {
                Main.LOG.error("Failed to get template asset for " + textureList[i]);
                Main.LOG.error(err);
                return null;
            }
        }

        if (overlayList.length == 0)
            overlays[0] = new BufferedImage(textures[0].getWidth(), textures[0].getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
        else {
            for (int i = 0; i < overlayList.length; i++) {
                if (overlayList[i].equals("none"))
                    overlays[i] = new BufferedImage(textures[0].getWidth(), textures[0].getHeight(),
                            BufferedImage.TYPE_INT_ARGB);
                else {
                    try {
                        overlays[i] = getImage(overlayList[i]);
                    } catch (IOException err) {
                        Main.LOG.error("Failed to get base asset for " + overlayList[i]);
                        Main.LOG.error(err);
                        if (i > 1)
                            overlays[i] = overlays[0];
                        else
                            overlays[i] = new BufferedImage(textures[0].getWidth(), textures[0].getHeight(),
                                    BufferedImage.TYPE_INT_ARGB);
                    }
                }
            }
        }

        Integer width = textures[0].getWidth();
        Integer frameHeight = textures[0].getHeight();
        Integer height = frameHeight * textureList.length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, width, height);

        g2d.setComposite(AlphaComposite.Src);

        int min, max;
        List<Integer> shades;

        for (int i = 0; i < textures.length; i++) {
            min = -1;
            max = -1;
            shades = new ArrayList<Integer>();

            int rgba;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < frameHeight; y++) {
                    rgba = textures[i].getRGB(x, y);

                    if (!shades.contains(rgba))
                        shades.add(rgba);

                    if (rgba < min || min == -1)
                        min = rgba;
                    if (rgba > max || max == -1)
                        max = rgba;
                }
            }

            Collections.sort(shades); // Low -> High

            Main.LOG.info("Creating asset map using " + shades.size() + " shades and " + colorList.length + " colors.");

            String[] colorArr = colorList.length > shades.size() ? new String[shades.size()] : new String[colorList.length];

            if (colorList.length > shades.size()) {
                Main.LOG.info("Colors > Shades. Reducing available colors to match shades.");

                for (int c = 0; c < shades.size(); c++)
                    colorArr[c] = colorList[c];
            } else
                colorArr = colorList;

            int tempRgba, baseRgba;
            Integer[] pixel;
            
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < frameHeight; y++) {
                    tempRgba = textures[i].getRGB(x, y);
                    baseRgba = overlays.length == textures.length ? overlays[i].getRGB(x, y) : overlays[0].getRGB(x, y);

                    pixel = getPixel(tempRgba, baseRgba, mode, shades, colorArr, templateShading);

                    g2d.setColor(new Color(pixel[0], pixel[1], pixel[2], pixel[3]));
                    g2d.fillRect(x, y + frameHeight * i, 1, 1);
                }
            }
        }
        
        g2d.dispose();

        try {
            // if (!absOutFile.exists())
                ImageIO.write(image, "png", absOutFile);
        } catch (IOException err) {
            Main.LOG.error("Failed to generate new asset for " + name);
            Main.LOG.error(err);
        }

        return Main.MOD_ID + ":block/" + name;
    }

    public static String createAnimationController(String path, String name, Integer frameCount, Integer frameTime, String[] frameSettings) {
        JsonBuilder builder = new JsonBuilder();
        JsonObject json = builder.createJsonObject();

        JsonArray frames = json.addObject("animation").set("frametime", 2).addArray("frames");
        HashMap<Integer, JsonObject> settings = new HashMap<Integer, JsonObject>();

        for (String s : frameSettings) {
            String[] v = s.split(":");
            settings.put(Integer.parseInt(v[0]), builder.createJsonObject().set("index", v[0]).set("time", v.length > 1 ? v[1] : frameTime));
        }

        int count = 0;
        while (count < frameCount) {
            if (settings.containsKey(count))
                frames.add(settings.get(count));
            else
                frames.add(count);

            count++;
        }

        Files.write(path, name, builder.stringify(json), ".png.mcmeta");

        return Main.MOD_ID + ":block/" + name;
    }
}
