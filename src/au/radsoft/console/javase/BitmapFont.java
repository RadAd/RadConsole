// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console.javase;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class BitmapFont {
    private final Image font_;
    private final java.util.AbstractMap<Color, Image> fontColorMap_ = new java.util.HashMap<Color, Image>();

    private final static Color defColor_ = Color.WHITE;
    private final static java.net.URL fontUrl_ = BitmapFont.class
            .getResource("/font_default.png");
    private final static int fontWidth_ = 32;
    private final static int fontHeight_ = 8;

    public BitmapFont() throws java.io.IOException {
        font_ = javax.imageio.ImageIO.read(fontUrl_);
        fontColorMap_.clear();
        fontColorMap_.put(defColor_, font_);
    }

    public int getWidth() {
        return font_.getWidth(null) / fontWidth_;
    }

    public int getHeight() {
        return font_.getHeight(null) / fontHeight_;
    }

    public void paint(java.awt.Graphics g, char ch, Color fg, Color bg, int x,
            int y) {
        final int tw = getWidth();
        final int th = getHeight();

        if (ch == ' ' || fg.equals(bg)) {
            g.setColor(bg);
            g.fillRect(x, y, tw + 1, th + 1);
        } else if (ch == (char) 219) {
            g.setColor(fg);
            g.fillRect(x, y, tw + 1, th + 1);
        } else if (ch >= 0 && ch < 256) {
            final int bx = ch % fontWidth_ * tw;
            final int by = ch / fontWidth_ * th;

            Image font = getColorImage(fg);
            g.drawImage(font, x, y, x + tw, y + th, bx, by, bx + tw, by + th,
                    bg, null);
        }
    }

    private Image getColorImage(Color c) {
        Image font = fontColorMap_.get(c);
        if (font == null) {
            font = createColorImage(font_, defColor_, c);
            fontColorMap_.put(c, font);
        }
        return font;
    }

    private static Image createColorImage(Image src, Color a, Color b) {
        BufferedImage dst = new BufferedImage(src.getWidth(null),
                src.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        BufferedImage bisrc = (BufferedImage) src;
        int[] pixels = new int[src.getWidth(null) * src.getHeight(null)];
        bisrc.getRGB(0, 0, src.getWidth(null), src.getHeight(null), pixels, 0,
                src.getWidth(null));

        for (int i = 0; i < pixels.length; i++) {
            if ((pixels[i] & 0xFFFFFF) == (a.getRGB() & 0xFFFFFF)) {
                pixels[i] = (pixels[i] & 0xFF000000) | b.getRGB();
            }
        }

        dst.setRGB(0, 0, dst.getWidth(null), dst.getHeight(null), pixels, 0,
                dst.getWidth(null));
        return dst;
    }
}
