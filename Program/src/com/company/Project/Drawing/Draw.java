package com.company.Project.Drawing;
/*==============================================================
Author: Draw
Datum:  
ProjektName:    Program
Beschreibung: 
==============================================================*/

import com.company.Project.Stock.Stock;
import com.company.Project.Stock.StockManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class Draw {

    private static final Color COLOR_BACKGROUND = new Color(88, 90, 94);
    private static final Color COLOR_SEPARATOR = new Color(243, 219, 206, 196);
    private static final int SEPARATOR_LINE_OCCURRENCES = 20;


    public static void draw(StockManager sm) {

        try {
            HashMap<String, Color> colorList = new HashMap<>();
            String fileNamePrefix = getName();
            File f = new File(fileNamePrefix + ".png");
            int height = ((int) sm.getObserver().getHighestPriceRecorded());
            int width = sm.getObserver().getNumOfUpdates() * 3;
            BufferedImage bufferedImage = new BufferedImage(
                    width,
                    height,
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g2d = bufferedImage.createGraphics();
            //draw background color
            g2d.setColor(COLOR_BACKGROUND);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    g2d.drawLine(x, y, x, y);
                }
            }

            //draw Separator lines
            g2d.setColor(COLOR_SEPARATOR);
            for (int i = 0; i < width; i += SEPARATOR_LINE_OCCURRENCES) {
                g2d.drawLine(i, height, i, 0);
            }

            //Draws stock prices
            for (Map.Entry<Stock, Stack<Double>> set : sm.getMemory().entrySet()) {

                Stock s = set.getKey();
                Stack<Double> priceHistory = set.getValue();
                Color c = generateColor();
                colorList.put(s.getName(), c);
                g2d.setColor(c);
                int currentWidthPosition = width;
                int lastWidthPosition = width;
                long currentValue = Math.round(priceHistory.pop());
                int convertedCurrentValue = ((int) currentValue);
                int lastValue = 0;
                while (priceHistory.size() != 0) {
                    if (currentWidthPosition == width) {
                        lastValue = convertedCurrentValue;
                    }
                    g2d.drawLine(currentWidthPosition, convertedCurrentValue, lastWidthPosition, lastValue);
                    lastWidthPosition = currentWidthPosition;
                    currentWidthPosition -= 3;
                    lastValue = convertedCurrentValue;
                    currentValue = Math.round(priceHistory.pop());
                    convertedCurrentValue = ((int) currentValue);
                }

            }

            //write img
            bufferedImage = flipVertically(bufferedImage);
            ImageIO.write(bufferedImage, "png", f);
            writeInfoText(fileNamePrefix, colorList, sm.getObserver().getHighestPriceRecorded(), sm.getObserver().getLowestPriceRecorded());
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    private static BufferedImage flipVertically(BufferedImage bufferedImage) {
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                flipped.setRGB(x, (height - 1) - y, bufferedImage.getRGB(x, y));
            }
        }
        return flipped;
    }

    private static Color generateColor() {
        Random rand = new Random();
        final int MAX = 255;
        final int MIN = 40;
        int r = rand.nextInt(MAX - MIN) + MIN;
        int g = rand.nextInt(MAX - MIN) + MIN;
        int b = rand.nextInt(MAX - MIN) + MIN;
        return new Color(r, g, b);
    }

    private static void writeInfoText(String filePrefix, HashMap<String, Color> stockColorMap, double highestValue, double lowestValue) {
        File f = new File(filePrefix + ".txt");
        String text = "This file contains the Stock colors,\n" +
                "so it defines which stock has which color\n\n" +
                "Highest Price(All Stocks): " + highestValue +
                "Lowest Price(All Stocks): "+lowestValue+"\n\n";
        for (Map.Entry<String, Color> set : stockColorMap.entrySet()) {
            text += set.getKey() + " RGB: " + set.getValue().getRed() + ", " + set.getValue().getGreen() + ", " + set.getValue().getBlue() + "\n";
        }
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
        }

    }

    private static String getName() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
