package dev.clng.common;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;
import java.util.List;
import javax.swing.*;

public class TextAreaOutputStream extends OutputStream
{
    private final byte[] oneByte;
    private Appender appender;

    public TextAreaOutputStream(JTextArea txtara)
    {
        this(txtara, 1000);
    }

    public TextAreaOutputStream(JTextArea txtara, int maxlin)
    {
        this(txtara, maxlin, null);
    }

    public TextAreaOutputStream(JTextArea txtara, int maxlin, Pattern rmvptn)
    {
        if (maxlin < 1)
        {
            throw new IllegalArgumentException("TextAreaOutputStream maximum lines must be positive (value=" + maxlin + ")");
        }
        oneByte = new byte[1];
        appender = new Appender(txtara, maxlin, rmvptn);
    }

    /**
     * Clear the current console text area.
     */
    public synchronized void clear()
    {
        if (appender != null)
        {
            appender.clear();
        }
    }

    public synchronized void close()
    {
        appender = null;
    }

    public synchronized void flush() { }

    public synchronized void write(int val)
    {
        oneByte[0] = (byte) val;
        write(oneByte, 0, 1);
    }

    public synchronized void write(byte[] ba)
    {
        write(ba, 0, ba.length);
    }

    public synchronized void write(byte[] ba, int str, int len)
    {
        if (appender != null)
        {
            appender.append(bytesToString(ba, str, len));
        }
    }

    static private String bytesToString(byte[] ba, int str, int len)
    {
        return new String(ba, str, len, StandardCharsets.UTF_8);
    }

    static class Appender
            implements Runnable
    {
        private final StringBuilder line = new StringBuilder(1000);
        private final List<String> lines = new ArrayList<String>();
        private final LinkedList<Integer> lengths = new LinkedList<Integer>();

        private final JTextArea textArea;
        private final int maxLines;
        private final Pattern rmvPattern;

        private boolean clear;
        private boolean queue;

        Appender(JTextArea txtara, int maxlin, Pattern rmvptn)
        {
            textArea = txtara;
            maxLines = maxlin;
            rmvPattern = rmvptn;

            clear = false;
            queue = true;
        }

        synchronized void append(String val)
        {
            boolean eol = val.endsWith(EOL1) || val.endsWith(EOL2);

            line.append(val);
            while (line.length() > LINE_MAX)
            {
                emitLine(line.substring(0, LINE_MAX) + EOL1);
                line.replace(0, LINE_MAX, "[>>] ");
            }
            if (eol)
            {
                emitLine(line.toString());
                line.setLength(0);
            }
        }

        private void emitLine(String lin)
        {
            if (lines.size() > 10_000)
            {
                lines.clear();
                lines.add("<console-overflowed>\n");
            } else
            {
                if (rmvPattern != null)
                {
                    lin = rmvPattern.matcher(lin).replaceAll("");
                }
                lines.add(lin);
            }
            if (queue)
            {
                queue = false;
                EventQueue.invokeLater(this);
            }
        }

        synchronized void clear()
        {
            clear = true;
            if (queue)
            {
                queue = false;
                EventQueue.invokeLater(this);
            }
        }

        public synchronized void run()
        {
            int don = 0;

            if (clear)
            {
                lengths.clear();
                lines.clear();
                textArea.setText("");
                clear = false;
            }

            for (String lin : lines)
            {
                don += 1;
                lengths.addLast(lin.length());
                if (lengths.size() >= maxLines)
                {
                    textArea.replaceRange("", 0, lengths.removeFirst());
                }
                textArea.append(lin);
                if (don >= 100)
                {
                    break;
                }
            }
            if (don == lines.size())
            {
                lines.clear();
                queue = true;
            } else
            {
                lines.subList(0, don).clear();
                EventQueue.invokeLater(this);
            }
        }

        static private final String EOL1 = "\n";
        static private final String EOL2 = System.getProperty("line.separator", EOL1);
        static private final int LINE_MAX = 1000;
    }

}