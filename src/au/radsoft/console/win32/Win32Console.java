// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console.win32;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import au.radsoft.win32.CHAR_INFO;
import au.radsoft.win32.CONSOLE_CURSOR_INFO;
import au.radsoft.win32.COORD;
import au.radsoft.win32.INPUT_RECORD;
import au.radsoft.win32.KEY_EVENT_RECORD;
import au.radsoft.win32.SMALL_RECT;
import au.radsoft.win32.FileAPI;
import au.radsoft.win32.WinCon;
import au.radsoft.win32.WinUser;

import au.radsoft.console.CharInfo;
import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Window;

public class Win32Console implements au.radsoft.console.Console {
    private Pointer hStdOutput;
    private Pointer hStdInput;
    private int w;
    private int h;

    public static Win32Console create(String title, int w, int h) {
        if (WinCon.INSTANCE == null)
            return null;
        else
            return new Win32Console(title, w, h);
    }

    public Win32Console(String title, int w, int h) {
        this.w = w;
        this.h = h;

        if (WinCon.INSTANCE.GetConsoleWindow() == null)
            WinCon.INSTANCE.AllocConsole();
        // hStdOutput =
        // WinCon.INSTANCE.GetStdHandle(WinCon.INSTANCE.STD_OUTPUT_HANDLE);
        // hStdInput =
        // WinCon.INSTANCE.GetStdHandle(WinCon.INSTANCE.STD_INPUT_HANDLE);
        hStdOutput = FileAPI.INSTANCE.CreateFile("CONOUT$",
                FileAPI.GENERIC_READ | FileAPI.GENERIC_WRITE,
                FileAPI.FILE_SHARE_WRITE, Pointer.NULL, FileAPI.OPEN_EXISTING,
                0, Pointer.NULL);
        hStdInput = FileAPI.INSTANCE.CreateFile("CONIN$", FileAPI.GENERIC_READ
                | FileAPI.GENERIC_WRITE, FileAPI.FILE_SHARE_READ, Pointer.NULL,
                FileAPI.OPEN_EXISTING, 0, Pointer.NULL);
        // System.err.println("hStdOutput: " + hStdOutput);

        //WinCon.INSTANCE.SetConsoleCP((short) 437);
        WinCon.INSTANCE.SetConsoleTitle(title);
        WinCon.INSTANCE.SetConsoleWindowInfo(hStdOutput, true, new SMALL_RECT(
                (short) 0, (short) 0, (short) 1, (short) 1));
        WinCon.INSTANCE.SetConsoleScreenBufferSize(hStdOutput, new COORD(
                (short) w, (short) h));
        WinCon.INSTANCE.SetConsoleWindowInfo(hStdOutput, true, new SMALL_RECT(
                (short) 0, (short) 0, (short) (h - 1), (short) (w - 1)));
    }

    static short convert(Color fg, Color bg) {
        return (short) ((bg.ordinal() << 4) | fg.ordinal());
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
        default:
            System.err.println("Unknown key code: " + code);
            return CharKey.NONE;
        }
    }

    @Override
    // from au.radsoft.console.Console
    public boolean isvalid() {
        return true;
    }

    @Override
    // from au.radsoft.console.Console
    public void cls() {
        COORD pos = new COORD((short) 0, (short) 0);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.FillConsoleOutputCharacter(hStdOutput, ' ', w * h, pos,
                r);
        WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput,
                convert(Color.WHITE, Color.BLACK), w * h, pos, r);
        WinCon.INSTANCE.SetConsoleCursorPosition(hStdOutput, pos);
    }

    @Override
    // from au.radsoft.console.Console
    public int width() {
        return w;
    }

    @Override
    // from au.radsoft.console.Console
    public int height() {
        return h;
    }

    @Override
    // from au.radsoft.console.Console
    public void showcursor(boolean show) {
        CONSOLE_CURSOR_INFO.ByReference cci = new CONSOLE_CURSOR_INFO.ByReference();
        WinCon.INSTANCE.GetConsoleCursorInfo(hStdOutput, cci);
        cci.bVisible = show;
        WinCon.INSTANCE.SetConsoleCursorInfo(hStdOutput, cci);
    }

    @Override
    // from au.radsoft.console.Console
    public void setcursor(int x, int y) {
        COORD pos = new COORD((short) x, (short) y);
        WinCon.INSTANCE.SetConsoleCursorPosition(hStdOutput, pos);
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, char c, Color fg, Color bg) {
        IntByReference r = new IntByReference();
        COORD pos = new COORD((short) x, (short) y);
        while (h > 0) {
            WinCon.INSTANCE.FillConsoleOutputCharacter(hStdOutput, c, w, pos,
                    r);
            WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput,
                    convert(Color.WHITE, Color.BLACK), w, pos, r);
            ++pos.Y;
            --h;
        }
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, char c) {
        IntByReference r = new IntByReference();
        COORD pos = new COORD((short) x, (short) y);
        while (h > 0) {
            WinCon.INSTANCE.FillConsoleOutputCharacter(hStdOutput, c, w, pos,
                    r);
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
            WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput,
                    convert(Color.WHITE, Color.BLACK), w, pos, r);
            ++pos.Y;
            --h;
        }
    }

    // @Override
    public void write(int x, int y, char[] ch) {
        COORD pos = new COORD((short) x, (short) y);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.WriteConsoleOutputCharacter(hStdOutput, ch, ch.length,
                pos, r);
    }
    public void write(int x, int y, byte[] ch) {
        COORD pos = new COORD((short) x, (short) y);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.WriteConsoleOutputCharacterA(hStdOutput, ch, ch.length,
                pos, r);
    }

    // @Override
    public void write(int x, int y, char[] ch, Color fg, Color bg) {
        COORD pos = new COORD((short) x, (short) y);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.WriteConsoleOutputCharacter(hStdOutput, ch, ch.length,
                pos, r);
        WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput, convert(fg, bg),
                ch.length, pos, r);
    }
    public void write(int x, int y, byte[] ch, Color fg, Color bg) {
        COORD pos = new COORD((short) x, (short) y);
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.WriteConsoleOutputCharacterA(hStdOutput, ch, ch.length,
                pos, r);
        WinCon.INSTANCE.FillConsoleOutputAttribute(hStdOutput, convert(fg, bg),
                ch.length, pos, r);
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, char ch) {
        byte[] chars = { (byte) ch };
        write(x, y, chars);
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, char ch, Color fg, Color bg) {
        byte[] chars = { (byte) ch };
        write(x, y, chars, fg, bg);
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, String s, Color fg, Color bg) {
        write(x, y, s.toCharArray(), fg, bg);
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, Window w) {
        CHAR_INFO[] chars = CHAR_INFO.createArray(w.width() * w.height());
        for (int xx = 0; xx < w.width(); ++xx) {
            for (int yy = 0; yy < w.height(); ++yy) {
                final CharInfo cell = w.get(xx, yy);
                int i = xx + yy * w.width();
                // chars[i] = new CHAR_INFO(cell.c, convert(cell.fg, cell.bg));
                chars[i].uChar.set((byte) cell.c);
                chars[i].Attributes = convert(cell.fg, cell.bg);
            }
        }
        WinCon.INSTANCE.WriteConsoleOutputA(hStdOutput, chars, new COORD(
                (short) w.width(), (short) w.height()), new COORD((short) 0,
                (short) 0),
                new SMALL_RECT((short) y, (short) x, (short) (y + w.height()),
                        (short) (x + w.width())));
    }

    @Override
    // from au.radsoft.console.Console
    public CharKey getkey() {
        INPUT_RECORD[] ir = new INPUT_RECORD[1];
        IntByReference r = new IntByReference();
        while (true) {
            WinCon.INSTANCE.ReadConsoleInput(hStdInput, ir, ir.length, r);
            for (int i = 0; i < r.getValue(); ++i) {
                if (ir[i].EventType == INPUT_RECORD.KEY_EVENT) {
                    KEY_EVENT_RECORD ke = ir[i].Event.KeyEvent;
                    if (ke.bKeyDown)
                        return convertKey(ke.wVirtualKeyCode);
                }
            }
        }
    }

    @Override
    // from au.radsoft.console.Console
    public CharKey getkeynowait() {
        INPUT_RECORD[] ir = new INPUT_RECORD[1];
        IntByReference r = new IntByReference();
        WinCon.INSTANCE.GetNumberOfConsoleInputEvents(hStdInput, r);
        while (r.getValue() > 0) {
            WinCon.INSTANCE.ReadConsoleInput(hStdInput, ir, ir.length, r);
            for (int i = 0; i < r.getValue(); ++i) {
                if (ir[i].EventType == INPUT_RECORD.KEY_EVENT) {
                    KEY_EVENT_RECORD ke = ir[i].Event.KeyEvent;
                    if (ke.bKeyDown)
                        return convertKey(ke.wVirtualKeyCode);
                }
            }
            WinCon.INSTANCE.GetNumberOfConsoleInputEvents(hStdInput, r);
        }
        return null;
    }

    @Override
    // from au.radsoft.console.Console
    public void close() {
        showcursor(true);
        cls();
    }
}
