// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface FileAPI extends StdCallLibrary {
    public static final String name = "kernel32";
    public static final FileAPI INSTANCE = (FileAPI) Native.loadLibrary(name,
            FileAPI.class, W32APIOptions.UNICODE_OPTIONS);

    // Desired Access
    public final static int GENERIC_ALL = 0x10000000;
    public final static int GENERIC_READ = 0x80000000;
    public final static int GENERIC_WRITE = 0x40000000;
    public final static int GENERIC_EXECUTE = 0x20000000;

    // Share Mode
    public final static int FILE_SHARE_READ = 0x00000001;
    public final static int FILE_SHARE_WRITE = 0x00000002;
    public final static int FILE_SHARE_DELETE = 0x00000004;

    // Creation Disposition
    public final static int CREATE_NEW = 1;
    public final static int CREATE_ALWAYS = 2;
    public final static int OPEN_EXISTING = 3;
    public final static int OPEN_ALWAYS = 4;
    public final static int TRUNCATE_EXISTING = 5;

    // Flags And Attributes
    public final static int FILE_ATTRIBUTE_READONLY = 0x1;
    public final static int FILE_ATTRIBUTE_HIDDEN = 0x2;
    public final static int FILE_ATTRIBUTE_SYSTEM = 0x4;
    public final static int FILE_ATTRIBUTE_ARCHIVE = 0x20;
    public final static int FILE_ATTRIBUTE_NORMAL = 0x80;

    // HANDLE WINAPI CreateFile(
    // _In_ LPCTSTR lpFileName,
    // _In_ DWORD dwDesiredAccess,
    // _In_ DWORD dwShareMode,
    // _In_opt_ LPSECURITY_ATTRIBUTES lpSecurityAttributes,
    // _In_ DWORD dwCreationDisposition,
    // _In_ DWORD dwFlagsAndAttributes,
    // _In_opt_ HANDLE hTemplateFile);
    public Pointer CreateFile(String in_lpFileName, int in_dwDesiredAccess,
            int in_dwShareMode, Pointer in_lpSecurityAttributes,
            int in_dwCreationDisposition, int in_dwFlagsAndAttributes,
            Pointer in_hTemplateFile) throws LastErrorException;
}
