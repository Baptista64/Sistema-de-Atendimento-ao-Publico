/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
