// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console.win32;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import au.radsoft.win32.CHAR_INFO;
import au.radsoft.win32.CONSOLE_CURSOR_INFO;
import au.radsoft.win32.CONSOLE_SCREEN_BUFFER_INFO;
import au.radsoft.win32.COORD;
import au.radsoft.win32.INPUT_RECORD;
import au.radsoft.win32.MOUSE_EVENT_RECORD;
import au.radsoft.win32.KEY_EVENT_RECORD;
import au.radsoft.win32.SMALL_RECT;
import au.radsoft.win32.FileAPI;
import au.radsoft.win32.WinCon;
import au.radsoft.win32.WinUser;
import au.radsoft.win32.WINDOW_BUFFER_SIZE_RECORD;

import au.radsoft.console.CharInfo;
import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Event;
import au.radsoft.console.Buffer;

public class Win32Console implements au.radsoft.console.Console {
    private final static boolean useUnicode = false;

    private final CONSOLE_SCREEN_BUFFER_INFO savedcsbi_ = new CONSOLE_SCREEN_BUFFER_INFO();
    private final Pointer hStdOutput_;
    private final Pointer hStdInput_;
    private int width_;
    private int height_;
    private int mousex_ = -1;
    private int mousey_ = -1;
    private int mousebutton_ = 0;
    
    public static void realloc()
    {
        WinCon.INSTANCE.FreeConsole();
        WinCon.INSTANCE.AllocConsole();
    }

    public static Win32Console create(String title, int w, int h, boolean realloc) {
        if (WinCon.INSTANCE == null || WinUser.INSTANCE == null)
            return null;
        else
            return new Win32Console(title, w, h, realloc);
    }

    public Win32Console(String title, int w, int h, boolean realloc) {
        width_ = w;
        height_ = h;

        //System.err.println("STD_OUTPUT_HANDLE: " + WinCon.INSTANCE.GetStdHandle(WinCon.STD_OUTPUT_HANDLE));
        //System.err.println("STD_INPUT_HANDLE: " + WinCon.INSTANCE.GetStdHandle(WinCon.STD_INPUT_HANDLE));
        
        if (realloc || WinCon.INSTANCE.GetConsoleWindow() == null)
        {
            try
            {
                WinCon.INSTANCE.FreeConsole();
            }
            catch (Exception e)
            {
                //Ignore exception when used with javaw
            }
            WinCon.INSTANCE.AllocConsole();
        }
        // hStdOutput_ = WinCon.INSTANCE.GetStdHandle(WinCon.STD_OUTPUT_HANDLE);
        // hStdInput_ = WinCon.INSTANCE.GetStdHandle(WinCon.STD_INPUT_HANDLE);
        hStdOutput_ = FileAPI.INSTANCE.CreateFile("CONOUT$",
                FileAPI.GENERIC_READ | FileAPI.GENERIC_WRITE,
                FileAPI.FILE_SHARE_WRITE, Pointer.NULL, FileAPI.OPEN_EXISTING,
                0, Pointer.NULL);
        hStdInput_ = FileAPI.INSTANCE.CreateFile("CONIN$",
                FileAPI.GENERIC_READ | FileAPI.GENERIC_WRITE,
                FileAPI.FILE_SHARE_READ, Pointer.NULL, FileAPI.OPEN_EXISTING,
                0, Pointer.NULL);
        //System.err.println("hStdOutput_: " + hStdOutput_);
        //System.err.println("hStdInput_: " + hStdInput_);
        //System.err.println("STD_OUTPUT_HANDLE: " + WinCon.INSTANCE.GetStdHandle(WinCon.STD_OUTPUT_HANDLE));
        //System.err.println("STD_INPUT_HANDLE: " + WinCon.INSTANCE.GetStdHandle(WinCon.STD_INPUT_HANDLE));
        //getKey();

        WinCon.INSTANCE.GetConsoleScreenBufferInfo(hStdOutput_, savedcsbi_);
        //WinCon.INSTANCE.SetConsoleCP((short) 437);
        if (title != null)
            WinCon.INSTANCE.SetConsoleTitle(title);
        WinCon.INSTANCE.SetConsoleWindowInfo(hStdOutput_, true,
            new SMALL_RECT((short) 0, (short) 0, (short) 1, (short) 1));
        WinCon.INSTANCE.SetConsoleScreenBufferSize(hStdOutput_,
            new COORD((short) w, (short) h));
        WinCon.INSTANCE.SetConsoleWindowInfo(hStdOutput_, true,
            new SMALL_RECT((short) 0, (short) 0, (short) (h - 1), (short) (w - 1)));

        // Disable Ctrl+C
        IntByReference mode = new IntByReference();
        WinCon.INSTANCE.GetConsoleMode(hStdInput_, mode);
        WinCon.INSTANCE.SetConsoleMode(hStdInput_, (mode.getValue() | WinCon.ENABLE_WINDOW_INPUT) & ~WinCon.ENABLE_PROCESSED_INPUT);
    }

    static short convert(Color fg, Color bg) {
        return (short) ((bg.ordinal() << 4) | fg.ordinal());
    }

    static Color convertFg(short attr) {
        return Color.values()[attr & 0xF];
    }
    
    static Color convertBg(short attr) {
        return Color.values()[(attr >> 4) & 0xF];
    }
    
    private static CharKey convertKey(int code) {
        switch (code) {
        case WinUser.VK_RETURN:
            return CharKey.ENTER;
        case WinUser.VK_BACK:
            return CharKey.BACK_SPACE;
        case WinUser.VK_TAB:
            return CharKey.TAB;
        case WinUser.VK_CAPITAL:
            return CharKey.CAPS_LOCK;
        case WinUser.VK_NUMLOCK:
            return CharKey.NUM_LOCK;
        case WinUser.VK_ESCAPE:
            return CharKey.ESCAPE;
        case WinUser.VK_INSERT:
            return CharKey.INSERT;
        case WinUser.VK_DELETE:
            return CharKey.DELETE;
        case WinUser.VK_HOME:
            return CharKey.HOME;
        case WinUser.VK_END:
            return CharKey.END;
        case WinUser.VK_PRIOR:
            return CharKey.PAGE_UP;
        case WinUser.VK_NEXT:
            return CharKey.PAGE_DOWN;
        case WinUser.VK_SHIFT:
            return CharKey.SHIFT;
        case WinUser.VK_CONTROL:
            return CharKey.CONTROL;
        case WinUser.VK_MENU:
            return CharKey.ALT;
        case WinUser.VK_LEFT:
            return CharKey.LEFT;
        case WinUser.VK_UP:
            return CharKey.UP;
        case WinUser.VK_RIGHT:
            return CharKey.RIGHT;
        case WinUser.VK_DOWN:
            return CharKey.DOWN;
        case WinUser.VK_SPACE:
            return CharKey.SPACE;
        case WinUser.VK_OEM_COMMA:
            return CharKey.COMMA;
        case WinUser.VK_OEM_PERIOD:
            return CharKey.PERIOD;
        case WinUser.VK_OEM_MINUS:
            return CharKey.MINUS;
        case WinUser.VK_OEM_PLUS:
            return CharKey.EQUALS;
        case WinUser.VK_OEM_4:
            return CharKey.LEFT_BRACKET;
        case WinUser.VK_OEM_6:
            return CharKey.RIGHT_BRACKET;
        case WinUser.VK_OEM_1:
            return CharKey.SEMICOLON;
        case WinUser.VK_OEM_7:
            return CharKey.QUOTE;
        case WinUser.VK_OEM_2:
            return CharKey.SLASH;
        case WinUser.VK_OEM_5:
            return CharKey.BACK_SLASH;
        case WinUser.VK_OEM_3:
            return CharKey.BACK_QUOTE;
        case WinUser.VK_NUMPAD0:
            return CharKey.NUM0;
        case WinUser.VK_NUMPAD1:
            return CharKey.NUM1;
        case WinUser.VK_NUMPAD2:
            return CharKey.NUM2;
        case WinUser.VK_NUMPAD3:
            return CharKey.NUM3;
        case WinUser.VK_NUMPAD4:
            return CharKey.NUM4;
        case WinUser.VK_NUMPAD5:
            return CharKey.NUM5;
        case WinUser.VK_NUMPAD6:
            return CharKey.NUM6;
        case WinUser.VK_NUMPAD7:
            return CharKey.NUM7;
        case WinUser.VK_NUMPAD8:
            return CharKey.NUM8;
        case WinUser.VK_NUMPAD9:
            return CharKey.NUM0;
        case WinUser.VK_CLEAR:
            return CharKey.CLEAR;
        case WinUser.VK_DIVIDE:
            return CharKey.DIVIDE;
        case WinUser.VK_MULTIPLY:
            return CharKey.MULTIPLY;
        case WinUser.VK_SUBTRACT:
            return CharKey.SUBTRACT;
        case WinUser.VK_ADD:
            return CharKey.ADD;
        case WinUser.VK_DECIMAL:
            return CharKey.DECIMAL;
        case '0':
            return CharKey.N0;
        case '1':
            return CharKey.N1;
        case '2':
            return CharKey.N2;
        case '3':
            return CharKey.N3;
        case '4':
            return CharKey.N4;
        case '5':
            return CharKey.N5;
        case '6':
            return CharKey.N6;
        case '7':
            return CharKey.N7;
        case '8':
            return CharKey.N8;
        case '9':
            return CharKey.N9;
        case 'A':
            return CharKey.A;
        case 'B':
            return CharKey.B;
        case 'C':
            return CharKey.C;
        case 'D':
            return CharKey.D;
        case 'E':
            return CharKey.E;
        case 'F':
            return CharKey.F;
        case 'G':
            return CharKey.G;
        case 'H':
            return CharKey.H;
        case 'I':
            return CharKey.I;
        case 'J':
            return CharKey.J;
        case 'K':
            return CharKey.K;
        case 'L':
            return CharKey.L;
        case 'M':
            return CharKey.M;
        case 'N':
            return CharKey.N;
        case 'O':
            return CharKey.O;
        case 'P':
            return CharKey.P;
        case 'Q':
            return CharKey.Q;
        case 'R':
            return CharKey.R;
        case 'S':
            return CharKey.S;
        case 'T':
            return CharKey.T;
        case 'U':
            return CharKey.U;
        case 'V':
            return CharKey.V;
        case 'W':
            return CharKey.W;
        case 'X':
            return CharKey.X;
        case 'Y':
            return CharKey.Y;
        case 'Z':
            return CharKey.Z;
        case WinUser.VK_F1:
            return CharKey.F1;
        case WinUser.VK_F2:
            return CharKey.F2;
        case WinUser.VK_F3:
            return CharKey.F3;
        case WinUser.VK_F4:
            return CharKey.F4;
        case WinUser.VK_F5:
            return CharKey.F5;
        case WinUser.VK_F6:
            return CharKey.F6;
        case WinUser.VK_F7:
            return CharKey.F7;
        case WinUser.VK_F8:
            return CharKey.F8;
        case WinUser.VK_F9:
            return CharKey.F9;
        case WinUser.VK_F10:
            return CharKey.F10;
        case WinUser.VK_F11:
            return CharKey.F11;
        case WinUser.VK_F12:
            return CharKey.F12;
        case WinUser.VK_LWIN:
            return CharKey.WIN;
        case WinUser.VK_RWIN:
            return CharKey.MENU;
        case WinUser.VK_LBUTTON:
            return CharKey.MOUSE_BUTTON1;
        case WinUser.VK_MBUTTON:
            return CharKey.MOUSE_BUTTON2;
        case WinUser.VK_RBUTTON:
            return CharKey.MOUSE_BUTTONR;
        default:
            System.err.println("Unknown key code: " + code);
            return CharKey.NONE;
        }
    }

    private static int convertKey(CharKey key) {
        switch (key) {
        case ENTER:
            return WinUser.VK_RETURN;
        case BACK_SPACE:
            return WinUser.VK_BACK;
        case TAB:
            return WinUser.VK_TAB;
        case CAPS_LOCK:
            return WinUser.VK_CAPITAL;
        case NUM_LOCK:
            return WinUser.VK_NUMLOCK;
        case ESCAPE:
            return WinUser.VK_ESCAPE;
        case INSERT:
            return WinUser.VK_INSERT;
        case DELETE:
            return WinUser.VK_DELETE;
        case HOME:
            return WinUser.VK_HOME;
        case END:
            return WinUser.VK_END;
        case PAGE_UP:
            return WinUser.VK_PRIOR;
        case PAGE_DOWN:
            return WinUser.VK_NEXT;
        case SHIFT:
            return WinUser.VK_SHIFT;
        case CONTROL:
            return WinUser.VK_CONTROL;
        case ALT:
            return WinUser.VK_MENU;
        case LEFT:
            return WinUser.VK_LEFT;
        case UP:
            return WinUser.VK_UP;
        case RIGHT:
            return WinUser.VK_RIGHT;
        case DOWN:
            return WinUser.VK_DOWN;
        case SPACE:
            return WinUser.VK_SPACE;
        case COMMA:
            return WinUser.VK_OEM_COMMA;
        case PERIOD:
            return WinUser.VK_OEM_PERIOD;
        case MINUS:
            return WinUser.VK_OEM_MINUS;
        case EQUALS:
            return WinUser.VK_OEM_PLUS;
        case LEFT_BRACKET:
            return WinUser.VK_OEM_4;
        case RIGHT_BRACKET:
            return WinUser.VK_OEM_6;
        case SEMICOLON:
            return WinUser.VK_OEM_1;
        case QUOTE:
            return WinUser.VK_OEM_7;
        case SLASH:
            return WinUser.VK_OEM_2;
        case BACK_SLASH:
            return WinUser.VK_OEM_5;
        case BACK_QUOTE:
            return WinUser.VK_OEM_3;
        case NUM0:
            return WinUser.VK_NUMPAD0;
        case NUM1:
            return WinUser.VK_NUMPAD1;
        case NUM2:
            return WinUser.VK_NUMPAD2;
        case NUM3:
            return WinUser.VK_NUMPAD3;
        case NUM4:
            return WinUser.VK_NUMPAD4;
        case NUM5:
            return WinUser.VK_NUMPAD5;
        case NUM6:
            return WinUser.VK_NUMPAD6;
        case NUM7:
            return WinUser.VK_NUMPAD7;
        case NUM8:
            return WinUser.VK_NUMPAD8;
        case NUM9:
            return WinUser.VK_NUMPAD9;
        case CLEAR:
            return WinUser.VK_CLEAR;
        case DIVIDE:
            return WinUser.VK_DIVIDE;
        case MULTIPLY:
            return WinUser.VK_MULTIPLY;
        case SUBTRACT:
            return WinUser.VK_SUBTRACT;
        case ADD:
            return WinUser.VK_ADD;
        case DECIMAL:
            return WinUser.VK_DECIMAL;
        case N0:
            return '0';
        case N1:
            return '1';
        case N2:
            return '2';
        case N3:
            return '3';
        case N4:
            return '4';
        case N5:
            return '5';
        case N6:
            return '6';
        case N7:
            return '7';
        case N8:
            return '8';
        case N9:
            return '0';
        case A:
            return 'A';
        case B:
            return 'B';
        case C:
            return 'C';
        case D:
            return 'D';
        case E:
            return 'E';
        case F:
            return 'F';
        case G:
            return 'G';
        case H:
            return 'H';
        case I:
            return 'I';
        case J:
            return 'J';
        case K:
            return 'K';
        case L:
            return 'L';
        case M:
            return 'M';
        case N:
            return 'N';
        case O:
            return 'O';
        case P:
            return 'P';
        case Q:
            return 'Q';
        case R:
            return 'R';
        case S:
            return 'S';
        case T:
            return 'T';
        case U:
            return 'U';
        case V:
            return 'V';
        case W:
            return 'W';
        case X:
            return 'X';
        case Y:
            return 'Y';
        case Z:
            return 'Z';
        case F1:
            return WinUser.VK_F1;
        case F2:
            return WinUser.VK_F2;
        case F3:
            return WinUser.VK_F3;
        case F4:
            return WinUser.VK_F4;
        case F5:
            return WinUser.VK_F5;
        case F6:
            return WinUser.VK_F6;
        case F7:
            return WinUser.VK_F7;
        case F8:
            return WinUser.VK_F8;
        case F9:
            return WinUser.VK_F9;
        case F10:
            return WinUser.VK_F10;
        case F11:
            return WinUser.VK_F11;
        case F12:
            return WinUser.VK_F12;
        case WIN:
            return WinUser.VK_LWIN;
        case MENU:
            return WinUser.VK_RWIN;
        case MOUSE_BUTTON1:
            return WinUser.VK_LBUTTON;
        case MOUSE_BUTTON2:
            return WinUser.VK_MBUTTON;
        case MOUSE_BUTTONR:
            return WinUser.VK_RBUTTON;
        default:
            System.err.println("Unknown key code: " + key);
            return 0;
        }
    }
    
    @Override
    // from au.radsoft.console.Console
    public boolean isValid() {
        return true;
    }

    @Override
    // from au.radsoft.console.Console
    public void clear() {
        COORD pos = new COORD((short) 0, (short) 0);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.FillConsoleOutputCharacter(hStdOutput_, ' ', width_ * height_, pos, r);
        WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput_, convert(Color.WHITE, Color.BLACK), width_ * height_, pos, r);
        WinCon.INSTANCE.SetConsoleCursorPosition(hStdOutput_, pos);
    }

    @Override
    // from au.radsoft.console.Console
    public int getWidth() {
        return width_;
    }

    @Override
    // from au.radsoft.console.Console
    public int getHeight() {
        return height_;
    }
    
    @Override
    // from au.radsoft.console.Console
    public void enableMouse(boolean enable) {
        IntByReference mode = new IntByReference();
        WinCon.INSTANCE.GetConsoleMode(hStdInput_, mode);
        if (enable)
            WinCon.INSTANCE.SetConsoleMode(hStdInput_, (mode.getValue() | WinCon.ENABLE_MOUSE_INPUT | WinCon.ENABLE_EXTENDED_FLAGS) & ~WinCon.ENABLE_QUICK_EDIT_MODE);
        else
            WinCon.INSTANCE.SetConsoleMode(hStdInput_, mode.getValue() & ~WinCon.ENABLE_MOUSE_INPUT);
    }

    @Override
    // from au.radsoft.console.Console
    public int getMouseX() {
        return mousex_;
    }

    @Override
    // from au.radsoft.console.Console
    public int getMouseY() {
        return mousey_;
    }

    @Override
    // from au.radsoft.console.Console
    public void showCursor(boolean show) {
        CONSOLE_CURSOR_INFO.ByReference cci = new CONSOLE_CURSOR_INFO.ByReference();
        WinCon.INSTANCE.GetConsoleCursorInfo(hStdOutput_, cci);
        cci.bVisible = show;
        WinCon.INSTANCE.SetConsoleCursorInfo(hStdOutput_, cci);
    }

    @Override
    // from au.radsoft.console.Console
    public void setCursor(int x, int y) {
        COORD pos = new COORD((short) x, (short) y);
        WinCon.INSTANCE.SetConsoleCursorPosition(hStdOutput_, pos);
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, char c, Color fg, Color bg) {
        IntByReference r = new IntByReference();
        COORD pos = new COORD((short) x, (short) y);
        while (h > 0) {
            WinCon.INSTANCE.FillConsoleOutputCharacter(hStdOutput_, c, w, pos, r);
            WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput_, convert(fg, bg), w, pos, r);
            ++pos.Y;
            --h;
        }
    }
    
    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, CharInfo ci)
    {
        fill(x, y, w, h, ci.c, ci.fg, ci.fg);
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, char c) {
        IntByReference r = new IntByReference();
        COORD pos = new COORD((short) x, (short) y);
        while (h > 0) {
            WinCon.INSTANCE.FillConsoleOutputCharacter(hStdOutput_, c, w, pos, r);
            ++pos.Y;
            --h;
        }
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, Color fg, Color bg) {
        IntByReference r = new IntByReference();
        COORD pos = new COORD((short) x, (short) y);
        while (h > 0) {
            WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput_, convert(fg, bg), w, pos, r);
            ++pos.Y;
            --h;
        }
    }

    // @Override
    public void write(int x, int y, char[] ch) {
        COORD pos = new COORD((short) x, (short) y);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.WriteConsoleOutputCharacter(hStdOutput_, ch, ch.length, pos, r);
    }
    public void write(int x, int y, byte[] ch) {
        COORD pos = new COORD((short) x, (short) y);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.WriteConsoleOutputCharacterA(hStdOutput_, ch, ch.length, pos, r);
    }

    // @Override
    public void write(int x, int y, char[] ch, Color fg, Color bg) {
        COORD pos = new COORD((short) x, (short) y);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.WriteConsoleOutputCharacter(hStdOutput_, ch, ch.length, pos, r);
        WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput_, convert(fg, bg), ch.length, pos, r);
    }
    public void write(int x, int y, byte[] ch, Color fg, Color bg) {
        COORD pos = new COORD((short) x, (short) y);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.WriteConsoleOutputCharacterA(hStdOutput_, ch, ch.length, pos, r);
        WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput_, convert(fg, bg), ch.length, pos, r);
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, char ch) {
        if (useUnicode)
        {
            char[] chars = { ch };
            write(x, y, chars);
        }
        else
        {
            byte[] chars = { (byte) ch };
            write(x, y, chars);
        }
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, char ch, Color fg, Color bg) {
        if (useUnicode)
        {
            char[] chars = { ch };
            write(x, y, chars, fg, bg);
        }
        else
        {
            byte[] chars = { (byte) ch };
            write(x, y, chars, fg, bg);
        }
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, CharInfo ci) {
        write(x, y, ci.c, ci.fg, ci.bg);
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, String s) {
        if (useUnicode)
            write(x, y, s.toCharArray());
        else
            write(x, y, s.getBytes(java.nio.charset.Charset.forName("UTF-8")));
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, String s, Color fg, Color bg) {
        if (useUnicode)
            write(x, y, s.toCharArray(), fg, bg);
        else
            write(x, y, s.getBytes(java.nio.charset.Charset.forName("UTF-8")), fg, bg);
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, Buffer b) {
        CHAR_INFO[] chars = CHAR_INFO.createArray(b.getWidth() * b.getHeight());
        for (int xx = 0; xx < b.getWidth(); ++xx) {
            for (int yy = 0; yy < b.getHeight(); ++yy) {
                final CharInfo cell = b.get(xx, yy);
                int i = xx + yy * b.getWidth();
                if (useUnicode)
                    chars[i].uChar.set(cell.c);
                else
                    chars[i].uChar.set((byte) cell.c);
                chars[i].Attributes = convert(cell.fg, cell.bg);
            }
        }
        if (useUnicode)
            WinCon.INSTANCE.WriteConsoleOutput(hStdOutput_, chars,
                    new COORD((short) b.getWidth(), (short) b.getHeight()),
                    new COORD((short) 0, (short) 0),
                    new SMALL_RECT((short) y, (short) x, (short) (y + b.getHeight()), (short) (x + b.getWidth())));
        else
            WinCon.INSTANCE.WriteConsoleOutputA(hStdOutput_, chars,
                    new COORD((short) b.getWidth(), (short) b.getHeight()),
                    new COORD((short) 0, (short) 0),
                    new SMALL_RECT((short) y, (short) x, (short) (y + b.getHeight()), (short) (x + b.getWidth())));

        if (true)
        {   // Bug on some computers where c < 32 doesn't show correctly
            for (int xx = 0; xx < b.getWidth(); ++xx) {
                for (int yy = 0; yy < b.getHeight(); ++yy) {
                    final CharInfo cell = b.get(xx, yy);
                    if (cell.c < 32)
                        write(x + xx, y + yy, cell.c, cell.fg, cell.bg);
                }
            }
        }
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int dx, int dy, Buffer b, int sx, int sy, int sw, int sh) {
        CHAR_INFO[] chars = CHAR_INFO.createArray(sw * sh);
        for (int xx = sx; xx < (sx + sw); ++xx) {
            for (int yy = sy; yy < (sy + sh); ++yy) {
                final CharInfo cell = b.get(xx, yy);
                int i = (xx - sx) + (yy - sy) * sw;
                if (useUnicode)
                    chars[i].uChar.set(cell.c);
                else
                    chars[i].uChar.set((byte) cell.c);
                chars[i].Attributes = convert(cell.fg, cell.bg);
            }
        }
        if (useUnicode)
            WinCon.INSTANCE.WriteConsoleOutput(hStdOutput_, chars,
                    new COORD((short) sw, (short) sh),
                    new COORD((short) 0, (short) 0),
                    new SMALL_RECT((short) dy, (short) dx, (short) (dy + sh), (short) (dx + sw)));
        else
            WinCon.INSTANCE.WriteConsoleOutputA(hStdOutput_, chars,
                    new COORD((short) sw, (short) sh),
                    new COORD((short) 0, (short) 0),
                    new SMALL_RECT((short) dy, (short) dx, (short) (dy + sh), (short) (dx + sw)));
                    
        if (true)
        {   // Bug on some computers where c < 32 doesn't show correctly
            for (int xx = sx; xx < (sx + sw); ++xx) {
                for (int yy = sy; yy < (sy + sh); ++yy) {
                    final CharInfo cell = b.get(xx, yy);
                    if (cell.c < 32)
                        write(dx + xx, dy + yy, cell.c, cell.fg, cell.bg);
                }
            }
        }
    }
    
    @Override
    // from au.radsoft.console.Console
    public void read(int x, int y, Buffer b) {
        CHAR_INFO[] chars = CHAR_INFO.createArray(b.getWidth() * b.getHeight());
        WinCon.INSTANCE.ReadConsoleOutputA(hStdOutput_, chars,
                new COORD((short) b.getWidth(), (short) b.getHeight()),
                new COORD((short) 0, (short) 0),
                new SMALL_RECT((short) y, (short) x, (short) (y + b.getHeight()), (short) (x + b.getWidth())));
        for (int xx = 0; xx < b.getWidth(); ++xx) {
            for (int yy = 0; yy < b.getHeight(); ++yy) {
                final CharInfo cell = b.get(xx, yy);
                int i = xx + yy * b.getWidth();
                cell.c = (char) chars[i].uChar.AsciiChar;
                cell.fg = convertFg(chars[i].Attributes);
                cell.bg = convertBg(chars[i].Attributes);
            }
        }
    }

    @Override
    // from au.radsoft.console.Console
    public CharKey getKey() {
        INPUT_RECORD[] ir = new INPUT_RECORD[1];
        IntByReference r = new IntByReference();
        while (true) {
            WinCon.INSTANCE.ReadConsoleInput(hStdInput_, ir, ir.length, r);
            for (int i = 0; i < r.getValue(); ++i) {
                switch (ir[i].EventType) {
                case INPUT_RECORD.KEY_EVENT:
                    KEY_EVENT_RECORD ke = ir[i].Event.KeyEvent;
                    if (ke.bKeyDown)
                        return convertKey(ke.wVirtualKeyCode);
                    break;
                    
                case INPUT_RECORD.MOUSE_EVENT:
                    MOUSE_EVENT_RECORD me = ir[i].Event.MouseEvent;
                    mousex_ = me.dwMousePosition.X;
                    mousey_ = me.dwMousePosition.Y;
                    if (me.dwEventFlags == 0)
                    {
                        int origmousebutton = mousebutton_;
                        mousebutton_ = me.dwButtonState;
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) != 0)
                            return CharKey.MOUSE_BUTTON1;
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) != 0)
                            return CharKey.MOUSE_BUTTONR;
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) != 0)
                            return CharKey.MOUSE_BUTTON2;
                    }
                    else if ((me.dwEventFlags & MOUSE_EVENT_RECORD.MOUSE_MOVED) != 0)
                        return CharKey.MOUSE_MOVED;
                    break;
                }
            }
        }
    }

    @Override
    // from au.radsoft.console.Console
    public CharKey getKeyNoWait() {
        INPUT_RECORD[] ir = new INPUT_RECORD[1];
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.GetNumberOfConsoleInputEvents(hStdInput_, r);
        while (r.getValue() > 0) {
            WinCon.INSTANCE.ReadConsoleInput(hStdInput_, ir, ir.length, r);
            for (int i = 0; i < r.getValue(); ++i) {
                switch (ir[i].EventType) {
                case INPUT_RECORD.KEY_EVENT:
                    KEY_EVENT_RECORD ke = ir[i].Event.KeyEvent;
                    if (ke.bKeyDown)
                        return convertKey(ke.wVirtualKeyCode);
                    break;
                    
                case INPUT_RECORD.MOUSE_EVENT:
                    MOUSE_EVENT_RECORD me = ir[i].Event.MouseEvent;
                    mousex_ = me.dwMousePosition.X;
                    mousey_ = me.dwMousePosition.Y;
                    if (me.dwEventFlags == 0)
                    {
                        int origmousebutton = mousebutton_;
                        mousebutton_ = me.dwButtonState;
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) != 0)
                            return CharKey.MOUSE_BUTTON1;
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) != 0)
                            return CharKey.MOUSE_BUTTONR;
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) != 0)
                            return CharKey.MOUSE_BUTTON2;
                    }
                    else if ((me.dwEventFlags & MOUSE_EVENT_RECORD.MOUSE_MOVED) != 0)
                        return CharKey.MOUSE_MOVED;
                    break;
                }
            }
            WinCon.INSTANCE.GetNumberOfConsoleInputEvents(hStdInput_, r);
        }
        return null;
    }

    @Override
    // from au.radsoft.console.Console
    public Event getEvent() {
        INPUT_RECORD[] ir = new INPUT_RECORD[1];
        IntByReference r = new IntByReference();
        while (true) {
            WinCon.INSTANCE.ReadConsoleInput(hStdInput_, ir, ir.length, r);
            for (int i = 0; i < r.getValue(); ++i) {
                switch (ir[i].EventType) {
                case INPUT_RECORD.KEY_EVENT:
                    KEY_EVENT_RECORD ke = ir[i].Event.KeyEvent;
                    return new Event.Key(convertKey(ke.wVirtualKeyCode), ke.bKeyDown ? Event.State.PRESSED : Event.State.RELEASED);
                    
                case INPUT_RECORD.MOUSE_EVENT:
                    MOUSE_EVENT_RECORD me = ir[i].Event.MouseEvent;
                    mousex_ = me.dwMousePosition.X;
                    mousey_ = me.dwMousePosition.Y;
                    if (me.dwEventFlags == 0)
                    {
                        int origmousebutton = mousebutton_;
                        mousebutton_ = me.dwButtonState;
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) != 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) == 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTON1, Event.State.PRESSED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) != 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTON1, Event.State.RELEASED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) != 0 && (origmousebutton & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) == 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTONR, Event.State.PRESSED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) != 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTONR, Event.State.RELEASED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) != 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) == 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTON2, Event.State.PRESSED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) != 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTON2, Event.State.RELEASED, mousex_, mousey_);
                    }
                    else if ((me.dwEventFlags & MOUSE_EVENT_RECORD.MOUSE_MOVED) != 0)
                        return new Event.MouseMoved(mousex_, mousey_);
                    break;
                    
                case INPUT_RECORD.WINDOW_BUFFER_SIZE_EVENT:
                    WINDOW_BUFFER_SIZE_RECORD wbse = ir[i].Event.WindowBufferSizeEvent;
                    return new Event.WindowBufferSize(wbse.dwSize.X, wbse.dwSize.Y);
                }
            }
        }
    }

    @Override
    // from au.radsoft.console.Console
    public Event getEventNoWait() {
        INPUT_RECORD[] ir = new INPUT_RECORD[1];
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.GetNumberOfConsoleInputEvents(hStdInput_, r);
        while (r.getValue() > 0) {
            WinCon.INSTANCE.ReadConsoleInput(hStdInput_, ir, ir.length, r);
            for (int i = 0; i < r.getValue(); ++i) {
                switch (ir[i].EventType) {
                case INPUT_RECORD.KEY_EVENT:
                    KEY_EVENT_RECORD ke = ir[i].Event.KeyEvent;
                    return new Event.Key(convertKey(ke.wVirtualKeyCode), ke.bKeyDown ? Event.State.PRESSED : Event.State.RELEASED);
                    
                case INPUT_RECORD.MOUSE_EVENT:
                    MOUSE_EVENT_RECORD me = ir[i].Event.MouseEvent;
                    mousex_ = me.dwMousePosition.X;
                    mousey_ = me.dwMousePosition.Y;
                    if (me.dwEventFlags == 0)
                    {
                        int origmousebutton = mousebutton_;
                        mousebutton_ = me.dwButtonState;
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) != 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) == 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTON1, Event.State.PRESSED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_1ST_BUTTON_PRESSED) != 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTON1, Event.State.RELEASED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) != 0 && (origmousebutton & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) == 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTONR, Event.State.PRESSED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.RIGHTMOST_BUTTON_PRESSED) != 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTONR, Event.State.RELEASED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) != 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) == 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTON2, Event.State.PRESSED, mousex_, mousey_);
                        if ((mousebutton_ & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) == 0 && (origmousebutton & MOUSE_EVENT_RECORD.FROM_LEFT_2ND_BUTTON_PRESSED) != 0)
                            return new Event.MouseButton(CharKey.MOUSE_BUTTON2, Event.State.RELEASED, mousex_, mousey_);
                    }
                    else if ((me.dwEventFlags & MOUSE_EVENT_RECORD.MOUSE_MOVED) != 0)
                        return new Event.MouseMoved(mousex_, mousey_);
                    break;
                    
                case INPUT_RECORD.WINDOW_BUFFER_SIZE_EVENT:
                    WINDOW_BUFFER_SIZE_RECORD wbse = ir[i].Event.WindowBufferSizeEvent;
                    return new Event.WindowBufferSize(wbse.dwSize.X, wbse.dwSize.Y);
                }
            }
            WinCon.INSTANCE.GetNumberOfConsoleInputEvents(hStdInput_, r);
        }
        return null;
    }
    
    @Override
    // from au.radsoft.console.Console
    public boolean getKeyDown(CharKey key) {
        int code = convertKey(key);
        return (WinUser.INSTANCE.GetKeyState(code) & 0x80) == 0x80;
    }

    @Override
    // from au.radsoft.console.Console
    public void close() {
        WinCon.INSTANCE.SetConsoleWindowInfo(hStdOutput_, true, new SMALL_RECT((short) 0, (short) 0, (short) 1, (short) 1));
        WinCon.INSTANCE.SetConsoleScreenBufferSize(hStdOutput_, savedcsbi_.dwSize);
        WinCon.INSTANCE.SetConsoleWindowInfo(hStdOutput_, true, savedcsbi_.srWindow);
        showCursor(true);
        clear();
    }
}
