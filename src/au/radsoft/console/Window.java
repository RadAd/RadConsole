// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console;

public class Window {
    private CharInfo[][] data_;

    public Window(int w, int h) {
        if (w <= 0 || h <= 0)
            throw new IllegalArgumentException(
                    "Dimensions must be greater than zero.");

        data_ = new CharInfo[h][w];

        for (int y = 0; y < height(); ++y) {
            for (int x = 0; x < width(); ++x) {
                data_[y][x] = new CharInfo(' ', Color.WHITE, Color.BLACK);
            }
        }
    }

    public int width() {
        return data_[0].length;
    }

    public int height() {
        return data_.length;
    }

    public CharInfo get(int x, int y) {
        if (isvalid(x, y))
            return data_[y][x];
        else
            return null;
    }

    private boolean isvalid(int x, int y) {
        return y >= 0 && y < height() && x >= 0 && x < width();
    }

    public void cls() {
        fill(0, 0, width(), height(), ' ', Color.WHITE, Color.BLACK);
    }

    public void fill(int x, int y, int w, int h, char c, Color fg, Color bg) {
        for (int dy = 0; dy < h; ++dy)
            for (int dx = 0; dx < w; ++dx) {
                final CharInfo cell = get(x + dx, y + dy);
                if (cell != null) {
                    cell.c = c;
                    cell.fg = fg;
                    cell.bg = bg;
                }
            }
    }

    public void fill(int x, int y, int w, int h, char c) {
        for (int dy = 0; dy < h; ++dy)
            for (int dx = 0; dx < w; ++dx) {
                final CharInfo cell = get(x + dx, y + dy);
                if (cell != null) {
                    cell.c = c;
                }
            }
    }

    public void fill(int x, int y, int w, int h, Color fg, Color bg) {
        for (int dy = 0; dy < h; ++dy)
            for (int dx = 0; dx < w; ++dx) {
                final CharInfo cell = get(x + dx, y + dy);
                if (cell != null) {
                    cell.fg = fg;
                    cell.bg = bg;
                }
            }
    }

    public void write(int x, int y, char ch) {
        final CharInfo cell = get(x, y);
        if (cell != null) {
            cell.c = ch;
        }
    }

    public void write(int x, int y, char ch, Color fg) {
        final CharInfo cell = get(x, y);
        if (cell != null) {
            cell.c = ch;
            cell.fg = fg;
        }
    }

    public void write(int x, int y, char ch, Color fg, Color bg) {
        final CharInfo cell = get(x, y);
        if (cell != null) {
            cell.c = ch;
            cell.fg = fg;
            cell.bg = bg;
        }
    }

    public void write(int x, int y, String s) {
        for (int i = 0; i < s.length(); ++i) {
            write(x + i, y, s.charAt(i));
        }
    }

    public void write(int x, int y, String s, Color fg) {
        for (int i = 0; i < s.length(); ++i) {
            write(x + i, y, s.charAt(i), fg);
        }
    }

    public void write(int x, int y, String s, Color fg, Color bg) {
        for (int i = 0; i < s.length(); ++i) {
            write(x + i, y, s.charAt(i), fg, bg);
        }
    }

    public void read(int x, int y, Window w) {
        for (int xx = 0; xx < w.width(); ++xx) {
            for (int yy = 0; yy < w.height(); ++yy) {
                final CharInfo srccell = get(xx + x, yy + y);
                if (srccell != null) {
                    final CharInfo dstcell = w.get(xx, yy);
                    if (dstcell != null) {
                        dstcell.set(srccell);
                    }
                }
            }
        }
    }

    public void write(int x, int y, Window w) {
        for (int xx = 0; xx < w.width(); ++xx) {
            for (int yy = 0; yy < w.height(); ++yy) {
                final CharInfo dstcell = get(xx + x, yy + y);
                if (dstcell != null) {
                    final CharInfo srccell = w.get(xx, yy);
                    if (srccell != null) {
                        dstcell.set(srccell);
                    }
                }
            }
        }
    }
};
