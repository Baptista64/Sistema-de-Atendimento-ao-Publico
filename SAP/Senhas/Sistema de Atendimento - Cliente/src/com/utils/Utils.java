/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author baptista
 */
public class Utils {

    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        String data = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1)
                + "-" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY)
                + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        return data;
    }

    public static String getFormatedDate() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        String sDate;
        if (day < 10) {
            sDate = "0" + day;
        } else {
            sDate = Integer.toString(day);
        }
        String sMonth;
        if (month < 10) {
            sMonth = "0" + month;
        } else {
            sMonth = Integer.toString(month);
        }
        String data = cal.get(Calendar.YEAR) + "-" + sMonth
                + "-" + sDate + " " + cal.get(Calendar.HOUR_OF_DAY)
                + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        return data;
    }

    public static String getTime() {
        String time;
        int hour = Calendar.getInstance().get(Calendar.HOUR);
        if (hour < 10) {
            time = "0" + Calendar.getInstance().get(Calendar.HOUR) + ":"
                    + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
        } else {
            time = Calendar.getInstance().get(Calendar.HOUR) + ":"
                    + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);
        }

        return time;
    }

    public static Clip getAudio(String URL) {
        Clip clip;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(URL).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioStream);

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            clip = null;
        }
        return clip;
    }

}
