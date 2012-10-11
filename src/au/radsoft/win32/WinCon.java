// RadConsole  Copyright (C) 2012  Adam Gates
// This program comes with ABSOLUTELY NO WARRANTY; for license see COPYING.TXT.

package au.radsoft.win32;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface WinCon extends StdCallLibrary {
    public static final String name = "kernel32";
    public static final WinCon INSTANCE = (WinCon) Native.loadLibrary(name,
            WinCon.class, W32APIOptions.UNICODE_OPTIONS);

    public static final int STD_INPUT_HANDLE = -10;
    public static final int STD_OUTPUT_HANDLE = -11;
    public static final int STD_ERROR_HANDLE = -12;

    // HANDLE WINAPI GetStdHandle(
    // __in DWORD nStdHandle
    // );
    public Pointer GetStdHandle(int nStdHandle);

    // BOOL WINAPI AllocConsole(void);
    void AllocConsole() throws LastErrorException;

    // HWND WINAPI GetConsoleWindow(void);
    Pointer GetConsoleWindow();

    // BOOL WINAPI FillConsoleOutputCharacter(
    // _In_ HANDLE hConsoleOutput,
    // _In_ TCHAR cCharacter,
    // _In_ DWORD nLength,
    // _In_ COORD dwWriteCoord,
    // _Out_ LPDWORD lpNumberOfCharsWritten);
    public void FillConsoleOutputCharacter(Pointer in_hConsoleOutput,
            char in_cCharacter, int in_nLength, COORD in_dwWriteCoord,
            IntByReference out_lpNumberOfCharsWritten)
            throws LastErrorException;

    // BOOL WINAPI FillConsoleOutputAttribute(
    // _In_ HANDLE hConsoleOutput,
    // _In_ WORD wAttribute,
    // _In_ DWORD nLength,
    // _In_ COORD dwWriteCoord,
    // _Out_ LPDWORD lpNumberOfAttrsWritten);
    public void FillConsoleOutputAttribute(Pointer in_hConsoleOutput,
            short in_wAttribute, int in_nLength, COORD in_dwWriteCoord,
            IntByReference out_lpNumberOfAttrsWritten)
            throws LastErrorException;

    // BOOL WINAPI GetConsoleCursorInfo(
    // _In_ HANDLE hConsoleOutput,
    // _Out_ PCONSOLE_CURSOR_INFO lpConsoleCursorInfo);
    void GetConsoleCursorInfo(Pointer in_hConsoleOutput,
            CONSOLE_CURSOR_INFO.ByReference out_lpConsoleCursorInfo)
            throws LastErrorException;

    // BOOL WINAPI GetNumberOfConsoleInputEvents(
    // _In_ HANDLE hConsoleInput,
    // _Out_ LPDWORD lpcNumberOfEvents);
    void GetNumberOfConsoleInputEvents(Pointer in_hConsoleOutput,
            IntByReference out_lpcNumberOfEvents) throws LastErrorException;

    // BOOL WINAPI ReadConsoleInput(
    // _In_ HANDLE hConsoleInput,
    // _Out_ PINPUT_RECORD lpBuffer,
    // _In_ DWORD nLength,
    // _Out_ LPDWORD lpNumberOfEventsRead);
    public void ReadConsoleInput(Pointer in_hConsoleOutput,
            INPUT_RECORD[] out_lpBuffer, int in_nLength,
            IntByReference out_lpNumberOfEventsRead) throws LastErrorException;

    // BOOL WINAPI SetConsoleCursorInfo(
    // _In_ HANDLE hConsoleOutput,
    // _In_ const CONSOLE_CURSOR_INFO *lpConsoleCursorInfo);
    void SetConsoleCursorInfo(Pointer in_hConsoleOutput,
            CONSOLE_CURSOR_INFO in_lpConsoleCursorInfo)
            throws LastErrorException;

    // BOOL WINAPI SetConsoleCP(
    // _In_ UINT wCodePageID);
    void SetConsoleCP(int in_wCodePageID) throws LastErrorException;

    // BOOL WINAPI SetConsoleCursorPosition(
    // _In_ HANDLE hConsoleOutput,
    // _In_ COORD dwCursorPosition);
    public void SetConsoleCursorPosition(Pointer in_hConsoleOutput,
            COORD in_dwCursorPosition) throws LastErrorException;

    // BOOL WINAPI SetConsoleScreenBufferSize(
    // __in HANDLE hConsoleOutput,
    // __in COORD dwSize
    // );
    public void SetConsoleScreenBufferSize(Pointer in_hConsoleOutput,
            COORD in_dwSize) throws LastErrorException;

    // BOOL WINAPI SetConsoleTitle(
    // _In_ LPCTSTR lpConsoleTitle
    // );
    public void SetConsoleTitle(String in_lpConsoleTitle)
            throws LastErrorException;

    // BOOL WINAPI SetConsoleWindowInfo(
    // _In_ HANDLE hConsoleOutput,
    // _In_ BOOL bAbsolute,
    // _In_ const SMALL_RECT *lpConsoleWindow);
    public void SetConsoleWindowInfo(Pointer in_hConsoleOutput,
            boolean in_bAbsolute, SMALL_RECT in_lpConsoleWindow)
            throws LastErrorException;

    // BOOL WINAPI WriteConsoleOutput(
    // _In_ HANDLE hConsoleOutput,
    // _In_ const CHAR_INFO *lpBuffer,
    // _In_ COORD dwBufferSize,
    // _In_ COORD dwBufferCoord,
    // _Inout_ PSMALL_RECT lpWriteRegion);
    void WriteConsoleOutput(Pointer in_hConsoleOutput, CHAR_INFO[] in_lpBuffer,
            COORD in_dwBufferSize, COORD in_dwBufferCoord,
            SMALL_RECT inout_lpWriteRegion) throws LastErrorException;
    void WriteConsoleOutputA(Pointer in_hConsoleOutput, CHAR_INFO[] in_lpBuffer,
            COORD in_dwBufferSize, COORD in_dwBufferCoord,
            SMALL_RECT inout_lpWriteRegion) throws LastErrorException;

    // BOOL WINAPI WriteConsoleOutputCharacter(
    // _In_ HANDLE hConsoleOutput,
    // _In_ LPCTSTR lpCharacter,
    // _In_ DWORD nLength,
    // _In_ COORD dwWriteCoord,
    // _Out_ LPDWORD lpNumberOfCharsWritten);
    public void WriteConsoleOutputCharacter(Pointer in_hConsoleOutput,
            char[] in_lpCharacter, int in_nLength, COORD in_dwWriteCoord,
            IntByReference out_lpNumberOfCharsWritten)
            throws LastErrorException;
    public void WriteConsoleOutputCharacterA(Pointer in_hConsoleOutput,
            byte[] in_lpCharacter, int in_nLength, COORD in_dwWriteCoord,
            IntByReference out_lpNumberOfCharsWritten)
            throws LastErrorException;
}
