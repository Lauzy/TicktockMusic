package com.lauzy.freedom.librarys.widght.music.lrc;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desc : 歌词解析
 * Author : Lauzy
 * Date : 2017/10/13
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class LrcParser {

    //[03:56.00][03:18.00][02:06.00][01:07.00]原谅我这一生不羁放纵爱自由
    private static final String LINE_REGEX = "((\\[\\d{2}:\\d{2}\\.\\d{2}])+)(.*)";
    private static final String TIME_REGEX = "\\[(\\d{2}):(\\d{2})\\.(\\d{2})]";

    public static List<Lrc> parseLrcFromAssets(Context context, String fileName) {
        try {
            return parseInputStream(context.getResources().getAssets().open(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Lrc> parseLrcFromFile(File file) {
        try {
            return parseInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Lrc> parseInputStream(InputStream inputStream) {
        List<Lrc> lrcs = new ArrayList<>();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) {
                    continue;
                }
                List<Lrc> lrcList = parseLrc(line);
                if (lrcList != null && lrcList.size() != 0) {
                    lrcs.addAll(lrcList);
                }
            }
            sortLrcs(lrcs);
            return lrcs;
        } catch (Exception e) {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return lrcs;
    }

    private static void sortLrcs(List<Lrc> lrcs) {
        Collections.sort(lrcs, new Comparator<Lrc>() {
            @Override
            public int compare(Lrc o1, Lrc o2) {
                return (int) (o1.getTime() - o2.getTime());
            }
        });
    }

    private static List<Lrc> parseLrc(String lrcLine) {
        List<Lrc> lrcs = new ArrayList<>();
        Matcher matcher = Pattern.compile(LINE_REGEX).matcher(lrcLine);
        if (!matcher.matches()) {
            return null;
        }

        String time = matcher.group(1);
        String content = matcher.group(3);
        Matcher timeMatcher = Pattern.compile(TIME_REGEX).matcher(time);

        while (timeMatcher.find()) {
            String min = timeMatcher.group(1);
            String sec = timeMatcher.group(2);
            String mil = timeMatcher.group(3);
            Lrc lrc = new Lrc();
            if (content != null && content.length() != 0) {
                lrc.setTime(Long.parseLong(min) * 60 * 1000 + Long.parseLong(sec) * 1000
                        + Long.parseLong(mil) * 10);
                lrc.setText(content);
                lrcs.add(lrc);
            }
        }
        return lrcs;
    }
}