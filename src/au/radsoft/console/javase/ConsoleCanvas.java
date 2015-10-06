// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.console.javase;

import au.radsoft.console.CharInfo;
import au.radsoft.console.CharKey;
import au.radsoft.console.Color;
import au.radsoft.console.Event;
import au.radsoft.console.Buffer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ConsoleCanvas extends java.awt.Canvas implements au.radsoft.console.Console
{
    public static ConsoleCanvas create(String title, int w, int h)
            throws java.io.IOException {
        java.awt.Frame f = null;
        try {
            f = new java.awt.Frame(title);
            final ConsoleCanvas comp = new ConsoleCanvas(w, h);
            f.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    java.awt.Window w = e.getWindow();
                    w.dispose();
                }
            });

            f.add(comp);
            f.pack();
            f.setResizable(false);
            comp.requestFocusInWindow();
            center(f);
            f.setVisible(true);

            f = null;
            return comp;
        } finally {
            if (f != null)
                f.dispose();
            f = null;
        }
    }

    private static void center(java.awt.Window w) {
        java.awt.Dimension ss = w.getToolkit().getScreenSize();
        java.awt.Dimension ws = w.getSize();

        int x = (ss.width - ws.width) / 2;
        int y = (ss.height - ws.height) / 2;

        w.setLocation(x, y);
    }

    private static java.awt.Color convert(Color c) {
        switch (c) {
        case BLACK:
            return java.awt.Color.BLACK;
        case DARK_BLUE:
            return java.awt.Color.BLUE.darker();
        case GREEN:
            return java.awt.Color.GREEN.darker();
        case TEAL:
            return java.awt.Color.CYAN.darker();
        case DARK_RED:
            return java.awt.Color.RED.darker();
        case PURPLE:
            return java.awt.Color.MAGENTA.darker();
        case BROWN:
            return java.awt.Color.YELLOW.darker();
        case LIGHT_GRAY:
            return java.awt.Color.LIGHT_GRAY;
        case GRAY:
            return java.awt.Color.GRAY;
        case BLUE:
            return java.awt.Color.BLUE;
        case LEMON:
            return java.awt.Color.GREEN;
        case CYAN:
            return java.awt.Color.CYAN;
        case RED:
            return java.awt.Color.RED;
        case MAGENTA:
            return java.awt.Color.MAGENTA;
        case YELLOW:
            return java.awt.Color.YELLOW;
        default:
        case WHITE:
            return java.awt.Color.WHITE;
        }
    }

    private static CharKey convertKey(int code) {
        switch (code) {
        case KeyEvent.VK_BACK_SPACE:
            return CharKey.BACK_SPACE;
        case KeyEvent.VK_CAPS_LOCK:
            return CharKey.CAPS_LOCK;
        case KeyEvent.VK_NUM_LOCK:
            return CharKey.NUM_LOCK;
        case KeyEvent.VK_SCROLL_LOCK:
            return CharKey.SCROLL_LOCK;
        case KeyEvent.VK_PRINTSCREEN:
            return CharKey.PRINTSCREEN;
        case KeyEvent.VK_PAUSE:
            return CharKey.PAUSE;
        case KeyEvent.VK_TAB:
            return CharKey.TAB;
        case KeyEvent.VK_ENTER:
            return CharKey.ENTER;
        case KeyEvent.VK_ESCAPE:
            return CharKey.ESCAPE;
        case KeyEvent.VK_SHIFT:
            return CharKey.SHIFT;
        case KeyEvent.VK_CONTROL:
            return CharKey.CONTROL;
        case KeyEvent.VK_UP:
            return CharKey.UP;
        case KeyEvent.VK_DOWN:
            return CharKey.DOWN;
        case KeyEvent.VK_LEFT:
            return CharKey.LEFT;
        case KeyEvent.VK_RIGHT:
            return CharKey.RIGHT;
        case KeyEvent.VK_INSERT:
            return CharKey.INSERT;
        case KeyEvent.VK_DELETE:
            return CharKey.DELETE;
        case KeyEvent.VK_HOME:
            return CharKey.HOME;
        case KeyEvent.VK_END:
            return CharKey.END;
        case KeyEvent.VK_PAGE_UP:
            return CharKey.PAGE_UP;
        case KeyEvent.VK_PAGE_DOWN:
            return CharKey.PAGE_DOWN;
        case KeyEvent.VK_CLEAR:
            return CharKey.CLEAR;
        case KeyEvent.VK_ALT:
            return CharKey.ALT;
        case KeyEvent.VK_SPACE:
            return CharKey.SPACE;
        case KeyEvent.VK_COMMA:
            return CharKey.COMMA;
        case KeyEvent.VK_PERIOD:
            return CharKey.PERIOD;
        case KeyEvent.VK_MINUS:
            return CharKey.MINUS;
        case KeyEvent.VK_EQUALS:
            return CharKey.EQUALS;
        case KeyEvent.VK_OPEN_BRACKET:
            return CharKey.LEFT_BRACKET;
        case KeyEvent.VK_CLOSE_BRACKET:
            return CharKey.RIGHT_BRACKET;
        case KeyEvent.VK_SEMICOLON:
            return CharKey.SEMICOLON;
        case KeyEvent.VK_QUOTE:
            return CharKey.QUOTE;
        case KeyEvent.VK_SLASH:
            return CharKey.SLASH;
        case KeyEvent.VK_BACK_SLASH:
            return CharKey.BACK_SLASH;
        case KeyEvent.VK_BACK_QUOTE:
            return CharKey.BACK_QUOTE;
        case KeyEvent.VK_NUMPAD0:
            return CharKey.NUM0;
        case KeyEvent.VK_NUMPAD1:
            return CharKey.NUM1;
        case KeyEvent.VK_NUMPAD2:
            return CharKey.NUM2;
        case KeyEvent.VK_NUMPAD3:
            return CharKey.NUM3;
        case KeyEvent.VK_NUMPAD4:
            return CharKey.NUM4;
        case KeyEvent.VK_NUMPAD5:
            return CharKey.NUM5;
        case KeyEvent.VK_NUMPAD6:
            return CharKey.NUM6;
        case KeyEvent.VK_NUMPAD7:
            return CharKey.NUM7;
        case KeyEvent.VK_NUMPAD8:
            return CharKey.NUM8;
        case KeyEvent.VK_NUMPAD9:
            return CharKey.NUM0;
        case KeyEvent.VK_DIVIDE:
            return CharKey.DIVIDE;
        case KeyEvent.VK_MULTIPLY:
            return CharKey.MULTIPLY;
        case KeyEvent.VK_SUBTRACT:
            return CharKey.SUBTRACT;
        case KeyEvent.VK_ADD:
            return CharKey.ADD;
        case KeyEvent.VK_DECIMAL:
            return CharKey.DECIMAL;
        case KeyEvent.VK_0:
            return CharKey.N0;
        case KeyEvent.VK_1:
            return CharKey.N1;
        case KeyEvent.VK_2:
            return CharKey.N2;
        case KeyEvent.VK_3:
            return CharKey.N3;
        case KeyEvent.VK_4:
            return CharKey.N4;
        case KeyEvent.VK_5:
            return CharKey.N5;
        case KeyEvent.VK_6:
            return CharKey.N6;
        case KeyEvent.VK_7:
            return CharKey.N7;
        case KeyEvent.VK_8:
            return CharKey.N8;
        case KeyEvent.VK_9:
            return CharKey.N9;
        case KeyEvent.VK_A:
            return CharKey.A;
        case KeyEvent.VK_B:
            return CharKey.B;
        case KeyEvent.VK_C:
            return CharKey.C;
        case KeyEvent.VK_D:
            return CharKey.D;
        case KeyEvent.VK_E:
            return CharKey.E;
        case KeyEvent.VK_F:
            return CharKey.F;
        case KeyEvent.VK_G:
            return CharKey.G;
        case KeyEvent.VK_H:
            return CharKey.H;
        case KeyEvent.VK_I:
            return CharKey.I;
        case KeyEvent.VK_J:
            return CharKey.J;
        case KeyEvent.VK_K:
            return CharKey.K;
        case KeyEvent.VK_L:
            return CharKey.L;
        case KeyEvent.VK_M:
            return CharKey.M;
        case KeyEvent.VK_N:
            return CharKey.N;
        case KeyEvent.VK_O:
            return CharKey.O;
        case KeyEvent.VK_P:
            return CharKey.P;
        case KeyEvent.VK_Q:
            return CharKey.Q;
        case KeyEvent.VK_R:
            return CharKey.R;
        case KeyEvent.VK_S:
            return CharKey.S;
        case KeyEvent.VK_T:
            return CharKey.T;
        case KeyEvent.VK_U:
            return CharKey.U;
        case KeyEvent.VK_V:
            return CharKey.V;
        case KeyEvent.VK_W:
            return CharKey.W;
        case KeyEvent.VK_X:
            return CharKey.X;
        case KeyEvent.VK_Y:
            return CharKey.Y;
        case KeyEvent.VK_Z:
            return CharKey.Z;
        case KeyEvent.VK_F1:
            return CharKey.F1;
        case KeyEvent.VK_F2:
            return CharKey.F2;
        case KeyEvent.VK_F3:
            return CharKey.F3;
        case KeyEvent.VK_F4:
            return CharKey.F4;
        case KeyEvent.VK_F5:
            return CharKey.F5;
        case KeyEvent.VK_F6:
            return CharKey.F6;
        case KeyEvent.VK_F7:
            return CharKey.F7;
        case KeyEvent.VK_F8:
            return CharKey.F8;
        case KeyEvent.VK_F9:
            return CharKey.F9;
        case KeyEvent.VK_F10:
            return CharKey.F10;
        case KeyEvent.VK_F11:
            return CharKey.F11;
        case KeyEvent.VK_F12:
            return CharKey.F12;
        case KeyEvent.VK_WINDOWS:
            return CharKey.WIN;
        case KeyEvent.VK_CONTEXT_MENU:
            return CharKey.MENU;
        default:
            System.err.println("Unknown key code: " + code);
            return CharKey.NONE;
        }
    }

    private static CharKey convertButton(int b) {
        switch (b)
        {
        default:
        case 1:
            return CharKey.MOUSE_BUTTON1;
            
        case 2:
            return CharKey.MOUSE_BUTTON2;
            
        case 3:
            return CharKey.MOUSE_BUTTONR;
        }
    }
    
    class Cursor extends Thread {
        private volatile boolean exit_ = false;
        private volatile boolean show_ = true;
        private boolean draw_ = true;
        private volatile int x_ = 0;
        private volatile int y_ = 0;

        void set(int x, int y) {
            x_ = x;
            y_ = y;
        }

        void show(boolean show) {
            show_ = show;
            repaint();
        }

        public void run() {
            while (!exit_) {
                try {
                    sleep(250);
                } catch (InterruptedException e) {
                }
                draw_ = !draw_;
                if (show_ && draw_) {
                    final int w = ConsoleCanvas.super.getWidth();
                    final int h = ConsoleCanvas.super.getHeight();
                    final int tw = font_.getWidth();
                    final int th = font_.getHeight();
                    final int xo = Math.round((w - asciiData_.getWidth() * tw) / 2);
                    final int yo = Math.round((h - asciiData_.getHeight() * th) / 2);
                    int sx = xo + x_ * tw;
                    int sy = yo + y_ * th;

                    java.awt.Graphics g = getGraphics();
                    if (g != null) {
                        int size = 5;
                        g.setColor(java.awt.Color.BLACK);
                        g.setXORMode(java.awt.Color.WHITE);
                        g.fillRect(sx, sy + th - size, tw, size);
                        g.setPaintMode();
                    }
                }
            }
        }

        void stopWait() {
            exit_ = true;
            try {
                join();
            } catch (InterruptedException ex) {
            }
        }
    }

    private final Buffer asciiData_;
    private final BitmapFont font_;
    private final java.util.concurrent.BlockingQueue<Event> events_ = new java.util.concurrent.ArrayBlockingQueue<Event>(100);
    private final boolean down_[] = new boolean[CharKey.values().length];
    private final Cursor cursor_;
    private boolean mouse_ = false;
    private int mousex_ = -1;
    private int mousey_ = -1;

    ConsoleCanvas(int w, int h) throws java.io.IOException {
        asciiData_ = new Buffer(w, h);
        font_ = new BitmapFont();

        setPreferredSize(w * font_.getWidth(), h * font_.getHeight());
        enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK
                | java.awt.AWTEvent.MOUSE_EVENT_MASK
                | java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK
                | java.awt.AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        setBackground(java.awt.Color.black);
        setFocusTraversalKeysEnabled(false);
        // setIgnoreRepaint(true);

        cursor_ = new Cursor();
        cursor_.start();
    }

    public void setPreferredSize(int w, int h) {
        setPreferredSize(new java.awt.Dimension(w, h));
    }

    @Override
    // from java.awt.Canvas
    public void addNotify() {
        super.addNotify();
        createBufferStrategy(2);
    }

    @Override
    // from java.awt.Canvas
    public void update(java.awt.Graphics g) {
        // Needed because super.update(g) erases the background
        paint(g);
    }

    @Override
    // from java.awt.Canvas
    public void paint(java.awt.Graphics g) {
        java.awt.image.BufferStrategy strategy = getBufferStrategy();
        if (strategy != null) {
            java.awt.Graphics ng = strategy.getDrawGraphics();
            render(ng);
            ng.dispose();
            strategy.show();
        } else {
            render(g);
        }
    }

    @Override
    // from java.awt.Canvas
    protected void processKeyEvent(KeyEvent e) {
        try {
            //System.err.println("processKeyEvent: " + e);
            if (e.getID() == KeyEvent.KEY_PRESSED)
            {
                CharKey key = convertKey(e.getKeyCode());
                down_[key.ordinal()] = true;
                events_.put(new Event.Key(key, Event.State.PRESSED));
            }
            else if (e.getID() == KeyEvent.KEY_RELEASED)
            {
                CharKey key = convertKey(e.getKeyCode());
                down_[key.ordinal()] = false;
                events_.put(new Event.Key(key, Event.State.RELEASED));
            }
        } catch (InterruptedException ex) {
        }
    }

    @Override
    // from java.awt.Canvas
    protected void processMouseEvent(MouseEvent e) {
        if (mouse_) {
            try {
                // System.err.println("processMouseEvent: " + e);
                final int w = super.getWidth();
                final int h = super.getHeight();
                
                final int tw = font_.getWidth();
                final int th = font_.getHeight();

                final int xo = Math.round((w - asciiData_.getWidth() * tw) / 2);
                final int yo = Math.round((h - asciiData_.getHeight() * th) / 2);
                
                mousex_ = (e.getX() - xo)/tw;
                mousey_ = (e.getY() - yo)/th;
                
                //if (e.getID() == MouseEvent.MOUSE_CLICKED)
                if (e.getID() == MouseEvent.MOUSE_RELEASED)
                {
                    CharKey key = convertButton(e.getButton());
                    down_[key.ordinal()] = false;
                    events_.put(new Event.MouseButton(key, Event.State.RELEASED, mousex_, mousey_));
                }
                else if (e.getID() == MouseEvent.MOUSE_PRESSED)
                {
                    CharKey key = convertButton(e.getButton());
                    down_[key.ordinal()] = true;
                    events_.put(new Event.MouseButton(key, Event.State.PRESSED, mousex_, mousey_));
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    // from java.awt.Canvas
    protected void processMouseMotionEvent(MouseEvent e) {
        if (mouse_) {
            // System.err.println("processMouseMotionEvent: " + e);
            // TODO dont update mouse position until event is processed in getKey
            try {
                final int w = super.getWidth();
                final int h = super.getHeight();
                
                final int tw = font_.getWidth();
                final int th = font_.getHeight();

                final int xo = Math.round((w - asciiData_.getWidth() * tw) / 2);
                final int yo = Math.round((h - asciiData_.getHeight() * th) / 2);
                
                mousex_ = (e.getX() - xo)/tw;
                mousey_ = (e.getY() - yo)/th;
            
                events_.put(new Event.MouseMoved(mousex_, mousey_));
            } catch (InterruptedException ex) {
            }
        }
    }
    
    public void render(java.awt.Graphics g) {
        // System.err.prinltn("Render: " + System.currentTimeMillis());
        final int w = super.getWidth();
        final int h = super.getHeight();
        
        g.clearRect(0, 0, w, h);

        if (asciiData_ != null && font_ != null) {
            final int tw = font_.getWidth();
            final int th = font_.getHeight();

            final int xo = Math.round((w - asciiData_.getWidth() * tw) / 2);
            final int yo = Math.round((h - asciiData_.getHeight() * th) / 2);

            for (int y = 0; y < asciiData_.getHeight(); ++y) {
                for (int x = 0; x < asciiData_.getWidth(); ++x) {
                    final CharInfo cc = asciiData_.get(x, y);
                    if (cc != null) {
                        int sx = xo + x * tw;
                        int sy = yo + y * th;
                        font_.paint(g, cc.c, convert(cc.fg), convert(cc.bg), sx, sy);
                    }
                }
            }
        }
    }
    
    @Override
    // from au.radsoft.console.Console
    public boolean isValid() {
        return isShowing();
    }

    @Override
    // from au.radsoft.console.Console
    // TODO java.awt.Canvas also has this function, dont want to override it
    public int getWidth() {
        return asciiData_.getWidth();
    }

    @Override
    // from au.radsoft.console.Console
    // TODO java.awt.Canvas also has this function, dont want to override it
    public int getHeight() {
        return asciiData_.getHeight();
    }

    @Override
    // from au.radsoft.console.Console
    public void enableMouse(boolean enable) {
        mouse_ = enable;
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
    public void clear() {
        asciiData_.clear();
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void showCursor(boolean show) {
        cursor_.show(show);
    }

    @Override
    // from au.radsoft.console.Console
    public void setCursor(int x, int y) {
        cursor_.set(x, y);
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, char c, Color fg, Color bg) {
        asciiData_.fill(x, y, w, h, c, fg, bg);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, CharInfo ci) {
        asciiData_.fill(x, y, w, h, ci);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, char c) {
        asciiData_.fill(x, y, w, h, c);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void fill(int x, int y, int w, int h, Color fg, Color bg) {
        asciiData_.fill(x, y, w, h, fg, bg);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, char ch) {
        asciiData_.write(x, y, ch);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, char ch, Color fg, Color bg) {
        asciiData_.write(x, y, ch, fg, bg);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, CharInfo ci) {
        asciiData_.write(x, y, ci);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, String s) {
        asciiData_.write(x, y, s);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, String s, Color fg, Color bg) {
        asciiData_.write(x, y, s, fg, bg);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int x, int y, Buffer b) {
        asciiData_.write(x, y, b);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void write(int dx, int dy, Buffer b, int sx, int sy, int sw, int sh) {
        asciiData_.write(dx, dy, b, sx, sy, sw, sh);
        repaint();
    }

    @Override
    // from au.radsoft.console.Console
    public void read(int x, int y, Buffer b) {
        asciiData_.read(x, y, b);
    }
    
    private static class KeyHelper implements Event.Handler
    {
        public void handle(Event.Key ke)
        {
            if (ke.state == Event.State.PRESSED)
                key = ke.key;
        }
        
        public void handle(Event.MouseButton mbe)
        {
            if (mbe.state == Event.State.RELEASED)
                key = mbe.key;
        }
        
        public void handle(Event.MouseMoved mme)
        {
            key = CharKey.MOUSE_MOVED;;
        }
        
        CharKey key = null;
    }
        
    public CharKey getKeyHelper(Event e) {
        KeyHelper kh = new KeyHelper();
        e.handle(kh);
        return kh.key;
    }

    @Override
    // from au.radsoft.console.Console
    public CharKey getKey() {
        try {
            CharKey ck = null;
            while (ck == null && isValid())
            {
                Event e = events_.poll(1, java.util.concurrent.TimeUnit.SECONDS);
                if (e != null)
                    ck = getKeyHelper(e);
            }
            return ck == null ? CharKey.NONE : ck;
        } catch (InterruptedException e) {
            return CharKey.NONE;
        }
    }

    @Override
    // from au.radsoft.console.Console
    public CharKey getKeyNoWait() {
        try {
            synchronized(events_) {
                CharKey ck = null;
                while (!events_.isEmpty() && ck == null && isValid())
                {
                    Event e = events_.poll(1, java.util.concurrent.TimeUnit.SECONDS);
                    if (e != null)
                        ck = getKeyHelper(e);
                }
                return ck;
            }
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    // from au.radsoft.console.Console
    public Event getEvent() {
        try {
            Event e = null;
            while (e == null && isValid())
            {
                e = events_.poll(1, java.util.concurrent.TimeUnit.SECONDS);
            }
            return e;
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    // from au.radsoft.console.Console
    public Event getEventNoWait() {
        try {
            synchronized(events_) {
                Event e = null;
                if (!events_.isEmpty()) {
                    while (e == null && isValid())
                    {
                        e = events_.poll(1, java.util.concurrent.TimeUnit.SECONDS);
                    }
                }
                return e;
            }
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    // from au.radsoft.console.Console
    public boolean getKeyDown(CharKey key) {
        return down_[key.ordinal()];
    }
    
    @Override
    // from au.radsoft.console.Console
    public void close() {
        cursor_.stopWait();
        java.awt.Window w = (java.awt.Window) getParent();
        w.dispose();
    }
}
