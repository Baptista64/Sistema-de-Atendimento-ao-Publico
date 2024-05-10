/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms;

import Model.ModelSenha;
import com.utils.Utils;
import com.view.Principal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import message.MessageDialog;
import org.netbeans.examples.lib.timerbean.TimerListener;

/**
 *
 * @author baptista
 */
public class PanelCall extends javax.swing.JPanel {

    private final Color TIMER_COLORON_1 = new Color(1, 1, 1);
    private final Color TIMER_COLORON_2 = new Color(255, 255, 255);

    private message.MessageDialog message;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private final Principal frame;
    private Clip clip;
    org.netbeans.examples.lib.timerbean.Timer timer;
    private boolean timerOn;

    /**
     * Creates new form PanelCall
     */
    public PanelCall(Principal fram) {
        this.frame = fram;
        initComponents();
        init();
        File file = new File("./src/com/audio/A-1.wav");
        
        if(file.canRead()){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
    }

    boolean isChamandoCliente() {
        return new Controller.ControllerConfiguracoes().chamando_cliente(frame) == 1;
    }

    private void setSenhas() {

        label1.setForeground(TIMER_COLORON_1);
        label2.setForeground(TIMER_COLORON_1);
        label3.setForeground(TIMER_COLORON_1);
        label4.setForeground(TIMER_COLORON_1);

        ArrayList<ModelSenha> senhas = new Controller.ControllerSenha().getAllServicesInWait(frame);

        if (senhas == null) {
            return;
        }

        if (senhas.size() > 3) {
            label1.setText(senhas.get(0).getNome());
            label2.setText(senhas.get(1).getNome());
            label3.setText(senhas.get(2).getNome());
            label4.setText(senhas.get(3).getNome());
        } else if (senhas.size() == 3) {
            label1.setText(senhas.get(0).getNome());
            label2.setText(senhas.get(1).getNome());
            label3.setText(senhas.get(2).getNome());
            label4.setText("");
        } else if (senhas.size() == 2) {
            label1.setText(senhas.get(0).getNome());
            label2.setText(senhas.get(1).getNome());
            label3.setText("");
            label4.setText("");
        } else if (senhas.size() == 1) {
            label1.setText(senhas.get(0).getNome());
            label2.setText("");
            label3.setText("");
            label4.setText("");
        }
    }

    private void init() {

        label1 = new JLabel();
        label1.setFont(new Font("Verdana", 1, 20));
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setHorizontalAlignment(JLabel.CENTER);
        add(label1);

        label2 = new JLabel();
        label2.setFont(new Font("Verdana", 1, 20));
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        add(label2);

        label3 = new JLabel();
        label3.setFont(new Font("Verdana", 1, 20));
        label3.setVerticalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);
        add(label3);

        label4 = new JLabel();
        label4.setFont(new Font("Verdana", 1, 20));
        label4.setVerticalAlignment(JLabel.CENTER);
        label4.setHorizontalAlignment(JLabel.CENTER);
        add(label4);

        setSenhas();

        timer = new org.netbeans.examples.lib.timerbean.Timer();
        timer.setDelay(1000);
        timer.addTimerListener(new TimerListener() {
            @Override
            public void onTime(ActionEvent arg0) {
                timeOut();
            }
        });
        timer.start();
    }

    private void call(String senha, String caixa, JLabel label) {

        if (timerOn) {
            timerOn = false;
            label.setForeground(TIMER_COLORON_1);
        } else {
            timerOn = true;
            label.setForeground(TIMER_COLORON_2);
        }

        if (clip != null && clip.isActive()) {
            return;
        }

        if (clip != null && !clip.isActive()) {
            clip.stop();
        }

        if (!isChamandoCliente()) {
            setSenhas();
        }

        try {
            AudioInputStream input1 = AudioSystem.getAudioInputStream(new File(".//src//com//audio//" + senha + ".wav"));
            AudioInputStream input2 = AudioSystem.getAudioInputStream(new File(".//src//com//audio//Caixa" + caixa + ".wav"));
            AudioInputStream joined = new AudioInputStream(new SequenceInputStream(input1, input2), input1.getFormat(), input1.getFrameLength() + input2.getFrameLength());
            AudioSystem.write(joined, AudioFileFormat.Type.WAVE, new File(".//src//com//audio//joined.wav"));
            File file = new File(".//src//com//audio//joined.wav");
            file.deleteOnExit();
            clip = Utils.getAudio(".//src//com//audio//joined.wav");
            clip.start();
        } catch (UnsupportedAudioFileException | IOException ex) {
            if (message != null) {
                message.closeMessage();
            }
            java.util.logging.Logger.getLogger(PanelCall.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);

            message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Um ou mais arquivos não foram encontrados");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(254, 254, 254));
        setLayout(new java.awt.GridLayout(4, 0));
    }// </editor-fold>//GEN-END:initComponents

    private void timeOut() {

        String senha = new Controller.ControllerConfiguracoes().getSenha(frame);
        String caixa = new Controller.ControllerConfiguracoes().getCaixa(frame);
        if (!senha.equals("")) {
            if (senha.equals(label1.getText())) {
                call(senha, caixa, label1);
            } else if (senha.equals(label2.getText())) {
                call(senha, caixa, label2);
            } else if (senha.equals(label3.getText())) {
                call(senha, caixa, label3);
            } else if (senha.equals(label4.getText())) {
                call(senha, caixa, label4
                );
            } else {
                label1.setText(senha);
                call(senha, caixa, label1);
            }
        } else {
            setSenhas();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
