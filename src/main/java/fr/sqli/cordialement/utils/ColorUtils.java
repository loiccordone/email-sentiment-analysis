package fr.sqli.cordialement.utils;

import fr.sqli.cordialement.model.entity.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ColorUtils {

    /* SubClass of ColorUtils. In order to lookup */
    public static class Color {

        private String name;
        private int r;
        private int g;
        private int b;

        public Color(String name, int r, int g, int b) {
            this.name = name;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int computeMSE(int pixR, int pixG, int pixB) {
            return (((pixR - this.r) * (pixR - this.r) + (pixG - this.g) * (pixG - this.g) + (pixB - this.b)
                    * (pixB - this.b)) / 3);
        }

        public String getName() {
            return name.toString();
        }

        public int getR() {
            return this.r;
        }

        public int getG() {
            return this.g;
        }

        public int getB() {
            return this.b;
        }

        public static Color getDefault() {
            return new Color(Fragment.COLOR.BLACK.toString(), 0, 0, 0);
        }
    }

    /** Initialize the color lists that we need. */
    private static List<Color> initBasicColorList() {
        ArrayList<Color> colorList = new ArrayList<Color>();
        colorList.add(new Color(Fragment.COLOR.BLACK.toString(), 0, 0, 0));
        colorList.add(new Color(Fragment.COLOR.WHITE.toString(), 255, 255, 255));
        colorList.add(new Color(Fragment.COLOR.RED.toString(), 255, 0, 0));
        colorList.add(new Color(Fragment.COLOR.ORANGE.toString(), 255, 128, 0));
        colorList.add(new Color(Fragment.COLOR.GREEN.toString(), 0, 128, 0));
        colorList.add(new Color(Fragment.COLOR.BLUE.toString(), 0, 0, 255));
        colorList.add(new Color(Fragment.COLOR.YELLOW.toString(), 255, 255, 0));
        colorList.add(new Color(Fragment.COLOR.GREY.toString(), 192, 192, 192));
        colorList.add(new Color(Fragment.COLOR.PURPLE.toString(), 128, 0, 128));
        colorList.add(new Color(Fragment.COLOR.PINK.toString(), 255, 0, 255));
        colorList.add(new Color(Fragment.COLOR.BROWN.toString(), 128, 64, 0));

        return colorList;
    }

    private static List<Color> initFullColorList() {
        ArrayList<Color> fullColorList = new ArrayList<>();
        fullColorList.add(new Color("AliceBlue", 0xF0, 0xF8, 0xFF));
        fullColorList.add(new Color("AntiqueWhite", 0xFA, 0xEB, 0xD7));
        fullColorList.add(new Color("Aqua", 0x00, 0xFF, 0xFF));
        fullColorList.add(new Color("Aquamarine", 0x7F, 0xFF, 0xD4));
        fullColorList.add(new Color("Azure", 0xF0, 0xFF, 0xFF));
        fullColorList.add(new Color("Beige", 0xF5, 0xF5, 0xDC));
        fullColorList.add(new Color("Bisque", 0xFF, 0xE4, 0xC4));
        fullColorList.add(new Color("Black", 0x00, 0x00, 0x00));
        fullColorList.add(new Color("BlanchedAlmond", 0xFF, 0xEB, 0xCD));
        fullColorList.add(new Color("Blue", 0x00, 0x00, 0xFF));
        fullColorList.add(new Color("BlueViolet", 0x8A, 0x2B, 0xE2));
        fullColorList.add(new Color("Brown", 0xA5, 0x2A, 0x2A));
        fullColorList.add(new Color("BurlyWood", 0xDE, 0xB8, 0x87));
        fullColorList.add(new Color("CadetBlue", 0x5F, 0x9E, 0xA0));
        fullColorList.add(new Color("Chartreuse", 0x7F, 0xFF, 0x00));
        fullColorList.add(new Color("Chocolate", 0xD2, 0x69, 0x1E));
        fullColorList.add(new Color("Coral", 0xFF, 0x7F, 0x50));
        fullColorList.add(new Color("CornflowerBlue", 0x64, 0x95, 0xED));
        fullColorList.add(new Color("Cornsilk", 0xFF, 0xF8, 0xDC));
        fullColorList.add(new Color("Crimson", 0xDC, 0x14, 0x3C));
        fullColorList.add(new Color("Cyan", 0x00, 0xFF, 0xFF));
        fullColorList.add(new Color("DarkBlue", 0x00, 0x00, 0x8B));
        fullColorList.add(new Color("DarkCyan", 0x00, 0x8B, 0x8B));
        fullColorList.add(new Color("DarkGoldenRod", 0xB8, 0x86, 0x0B));
        fullColorList.add(new Color("DarkGray", 0xA9, 0xA9, 0xA9));
        fullColorList.add(new Color("DarkGreen", 0x00, 0x64, 0x00));
        fullColorList.add(new Color("DarkKhaki", 0xBD, 0xB7, 0x6B));
        fullColorList.add(new Color("DarkMagenta", 0x8B, 0x00, 0x8B));
        fullColorList.add(new Color("DarkOliveGreen", 0x55, 0x6B, 0x2F));
        fullColorList.add(new Color("DarkOrange", 0xFF, 0x8C, 0x00));
        fullColorList.add(new Color("DarkOrchid", 0x99, 0x32, 0xCC));
        fullColorList.add(new Color("DarkRed", 0x8B, 0x00, 0x00));
        fullColorList.add(new Color("DarkSalmon", 0xE9, 0x96, 0x7A));
        fullColorList.add(new Color("DarkSeaGreen", 0x8F, 0xBC, 0x8F));
        fullColorList.add(new Color("DarkSlateBlue", 0x48, 0x3D, 0x8B));
        fullColorList.add(new Color("DarkSlateGray", 0x2F, 0x4F, 0x4F));
        fullColorList.add(new Color("DarkTurquoise", 0x00, 0xCE, 0xD1));
        fullColorList.add(new Color("DarkViolet", 0x94, 0x00, 0xD3));
        fullColorList.add(new Color("DeepPink", 0xFF, 0x14, 0x93));
        fullColorList.add(new Color("DeepSkyBlue", 0x00, 0xBF, 0xFF));
        fullColorList.add(new Color("DimGray", 0x69, 0x69, 0x69));
        fullColorList.add(new Color("DodgerBlue", 0x1E, 0x90, 0xFF));
        fullColorList.add(new Color("FireBrick", 0xB2, 0x22, 0x22));
        fullColorList.add(new Color("FloralWhite", 0xFF, 0xFA, 0xF0));
        fullColorList.add(new Color("ForestGreen", 0x22, 0x8B, 0x22));
        fullColorList.add(new Color("Fuchsia", 0xFF, 0x00, 0xFF));
        fullColorList.add(new Color("Gainsboro", 0xDC, 0xDC, 0xDC));
        fullColorList.add(new Color("GhostWhite", 0xF8, 0xF8, 0xFF));
        fullColorList.add(new Color("Gold", 0xFF, 0xD7, 0x00));
        fullColorList.add(new Color("GoldenRod", 0xDA, 0xA5, 0x20));
        fullColorList.add(new Color("Gray", 0x80, 0x80, 0x80));
        fullColorList.add(new Color("Green", 0x00, 0x80, 0x00));
        fullColorList.add(new Color("GreenYellow", 0xAD, 0xFF, 0x2F));
        fullColorList.add(new Color("HoneyDew", 0xF0, 0xFF, 0xF0));
        fullColorList.add(new Color("HotPink", 0xFF, 0x69, 0xB4));
        fullColorList.add(new Color("IndianRed", 0xCD, 0x5C, 0x5C));
        fullColorList.add(new Color("Indigo", 0x4B, 0x00, 0x82));
        fullColorList.add(new Color("Ivory", 0xFF, 0xFF, 0xF0));
        fullColorList.add(new Color("Khaki", 0xF0, 0xE6, 0x8C));
        fullColorList.add(new Color("Lavender", 0xE6, 0xE6, 0xFA));
        fullColorList.add(new Color("LavenderBlush", 0xFF, 0xF0, 0xF5));
        fullColorList.add(new Color("LawnGreen", 0x7C, 0xFC, 0x00));
        fullColorList.add(new Color("LemonChiffon", 0xFF, 0xFA, 0xCD));
        fullColorList.add(new Color("LightBlue", 0xAD, 0xD8, 0xE6));
        fullColorList.add(new Color("LightCoral", 0xF0, 0x80, 0x80));
        fullColorList.add(new Color("LightCyan", 0xE0, 0xFF, 0xFF));
        fullColorList.add(new Color("LightGoldenRodYellow", 0xFA, 0xFA, 0xD2));
        fullColorList.add(new Color("LightGray", 0xD3, 0xD3, 0xD3));
        fullColorList.add(new Color("LightGreen", 0x90, 0xEE, 0x90));
        fullColorList.add(new Color("LightPink", 0xFF, 0xB6, 0xC1));
        fullColorList.add(new Color("LightSalmon", 0xFF, 0xA0, 0x7A));
        fullColorList.add(new Color("LightSeaGreen", 0x20, 0xB2, 0xAA));
        fullColorList.add(new Color("LightSkyBlue", 0x87, 0xCE, 0xFA));
        fullColorList.add(new Color("LightSlateGray", 0x77, 0x88, 0x99));
        fullColorList.add(new Color("LightSteelBlue", 0xB0, 0xC4, 0xDE));
        fullColorList.add(new Color("LightYellow", 0xFF, 0xFF, 0xE0));
        fullColorList.add(new Color("Lime", 0x00, 0xFF, 0x00));
        fullColorList.add(new Color("LimeGreen", 0x32, 0xCD, 0x32));
        fullColorList.add(new Color("Linen", 0xFA, 0xF0, 0xE6));
        fullColorList.add(new Color("Magenta", 0xFF, 0x00, 0xFF));
        fullColorList.add(new Color("Maroon", 0x80, 0x00, 0x00));
        fullColorList.add(new Color("MediumAquaMarine", 0x66, 0xCD, 0xAA));
        fullColorList.add(new Color("MediumBlue", 0x00, 0x00, 0xCD));
        fullColorList.add(new Color("MediumOrchid", 0xBA, 0x55, 0xD3));
        fullColorList.add(new Color("MediumPurple", 0x93, 0x70, 0xDB));
        fullColorList.add(new Color("MediumSeaGreen", 0x3C, 0xB3, 0x71));
        fullColorList.add(new Color("MediumSlateBlue", 0x7B, 0x68, 0xEE));
        fullColorList.add(new Color("MediumSpringGreen", 0x00, 0xFA, 0x9A));
        fullColorList.add(new Color("MediumTurquoise", 0x48, 0xD1, 0xCC));
        fullColorList.add(new Color("MediumVioletRed", 0xC7, 0x15, 0x85));
        fullColorList.add(new Color("MidnightBlue", 0x19, 0x19, 0x70));
        fullColorList.add(new Color("MintCream", 0xF5, 0xFF, 0xFA));
        fullColorList.add(new Color("MistyRose", 0xFF, 0xE4, 0xE1));
        fullColorList.add(new Color("Moccasin", 0xFF, 0xE4, 0xB5));
        fullColorList.add(new Color("NavajoWhite", 0xFF, 0xDE, 0xAD));
        fullColorList.add(new Color("Navy", 0x00, 0x00, 0x80));
        fullColorList.add(new Color("OldLace", 0xFD, 0xF5, 0xE6));
        fullColorList.add(new Color("Olive", 0x80, 0x80, 0x00));
        fullColorList.add(new Color("OliveDrab", 0x6B, 0x8E, 0x23));
        fullColorList.add(new Color("Orange", 0xFF, 0xA5, 0x00));
        fullColorList.add(new Color("OrangeRed", 0xFF, 0x45, 0x00));
        fullColorList.add(new Color("Orchid", 0xDA, 0x70, 0xD6));
        fullColorList.add(new Color("PaleGoldenRod", 0xEE, 0xE8, 0xAA));
        fullColorList.add(new Color("PaleGreen", 0x98, 0xFB, 0x98));
        fullColorList.add(new Color("PaleTurquoise", 0xAF, 0xEE, 0xEE));
        fullColorList.add(new Color("PaleVioletRed", 0xDB, 0x70, 0x93));
        fullColorList.add(new Color("PapayaWhip", 0xFF, 0xEF, 0xD5));
        fullColorList.add(new Color("PeachPuff", 0xFF, 0xDA, 0xB9));
        fullColorList.add(new Color("Peru", 0xCD, 0x85, 0x3F));
        fullColorList.add(new Color("Pink", 0xFF, 0xC0, 0xCB));
        fullColorList.add(new Color("Plum", 0xDD, 0xA0, 0xDD));
        fullColorList.add(new Color("PowderBlue", 0xB0, 0xE0, 0xE6));
        fullColorList.add(new Color("Purple", 0x80, 0x00, 0x80));
        fullColorList.add(new Color("Red", 0xFF, 0x00, 0x00));
        fullColorList.add(new Color("RosyBrown", 0xBC, 0x8F, 0x8F));
        fullColorList.add(new Color("RoyalBlue", 0x41, 0x69, 0xE1));
        fullColorList.add(new Color("SaddleBrown", 0x8B, 0x45, 0x13));
        fullColorList.add(new Color("Salmon", 0xFA, 0x80, 0x72));
        fullColorList.add(new Color("SandyBrown", 0xF4, 0xA4, 0x60));
        fullColorList.add(new Color("SeaGreen", 0x2E, 0x8B, 0x57));
        fullColorList.add(new Color("SeaShell", 0xFF, 0xF5, 0xEE));
        fullColorList.add(new Color("Sienna", 0xA0, 0x52, 0x2D));
        fullColorList.add(new Color("Silver", 0xC0, 0xC0, 0xC0));
        fullColorList.add(new Color("SkyBlue", 0x87, 0xCE, 0xEB));
        fullColorList.add(new Color("SlateBlue", 0x6A, 0x5A, 0xCD));
        fullColorList.add(new Color("SlateGray", 0x70, 0x80, 0x90));
        fullColorList.add(new Color("Snow", 0xFF, 0xFA, 0xFA));
        fullColorList.add(new Color("SpringGreen", 0x00, 0xFF, 0x7F));
        fullColorList.add(new Color("SteelBlue", 0x46, 0x82, 0xB4));
        fullColorList.add(new Color("Tan", 0xD2, 0xB4, 0x8C));
        fullColorList.add(new Color("Teal", 0x00, 0x80, 0x80));
        fullColorList.add(new Color("Thistle", 0xD8, 0xBF, 0xD8));
        fullColorList.add(new Color("Tomato", 0xFF, 0x63, 0x47));
        fullColorList.add(new Color("Turquoise", 0x40, 0xE0, 0xD0));
        fullColorList.add(new Color("Violet", 0xEE, 0x82, 0xEE));
        fullColorList.add(new Color("Wheat", 0xF5, 0xDE, 0xB3));
        fullColorList.add(new Color("White", 0xFF, 0xFF, 0xFF));
        fullColorList.add(new Color("WhiteSmoke", 0xF5, 0xF5, 0xF5));
        fullColorList.add(new Color("Yellow", 0xFF, 0xFF, 0x00));
        fullColorList.add(new Color("YellowGreen", 0x9A, 0xCD, 0x32));

        return fullColorList;
    }

    /* Get the closest color name from our list */
    public static Color getBasicColorFromRgb(int r, int g, int b) {
        List<Color> colorList = initBasicColorList();
        Color closestMatch = Color.getDefault();
        int minMSE = Integer.MAX_VALUE;
        int mse;
        for (Color c : colorList) {
            mse = c.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = c;
            }
        }

        return closestMatch;
    }

    public static Color getBasicColorFromColorName(String colorName) {
        List<Color> fullColorList = initFullColorList();
        Color closestMatch = Color.getDefault();

        for (Color color : fullColorList) {
            if (color.getName().toUpperCase().equals(colorName.toUpperCase())) {
                closestMatch = getBasicColorFromRgb(color.getR(), color.getG(), color.getB());
            }
        }

        return closestMatch;
    }

    public static Color getBasicColorFromHex(int hexColor) {
        int r = (hexColor & 0xFF0000) >> 16;
        int g = (hexColor & 0xFF00) >> 8;
        int b = (hexColor & 0xFF);
        return getBasicColorFromRgb(r, g, b);
    }
}
