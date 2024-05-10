/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.model.ModelConfiguracao;
import java.io.File;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import message.MessageDialog;
import net.sf.jasperreports.engine.JRException;
import com.controller.ControllerConfiguracoes;
import javax.swing.JFrame;

/**
 *
 * @author tobi
 */
public class Report extends connection.Connect {

    public static String logo = ".//src//logoE.png";
    public static String foto = "foto.png";
    public static String senhaIcon = ".//src//com//icons//senha.png";
    public static String nomeE;

    public void imprimirRelatorio(JFrame frame, String bi, String usuario) {

        ModelConfiguracao m = new ControllerConfiguracoes().getConfig(frame);

        if (m != null) {
            nomeE = m.getNome();
            File file = new File(Report.logo);
            file.deleteOnExit();
            try {
                FileUtils.writeByteArrayToFile(file, m.getLogo());
            } catch (IOException ex) {
                logo = ".//scr//com//icons//icon.png";
            }
        } else {
            logo = ".//scr//com//icons//icon.png";

            nomeE = "Null";

        }

        try {
            HashMap params = new HashMap();
            params.put("bi", bi);
            params.put("nomeE", nomeE);
            params.put("usuario", usuario);
            File file = new File(logo);
            if (!file.canRead()) {
                MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Não foi possível localizar a imagem que contem o logo da empresa");
                return;
            }
            params.put("logo", logo);

            JasperPrint print = JasperFillManager.fillReport(".//src//com//utils//func.jasper", params, this.getConnection());
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
        }
    }

    public void imprimirRelatorio(JFrame frame, String usuario, boolean atendidas) {

        ModelConfiguracao m = new ControllerConfiguracoes().getConfig(frame);

        if (m != null) {
            nomeE = m.getNome();
            File file = new File(Report.logo);
            file.deleteOnExit();
            try {
                FileUtils.writeByteArrayToFile(file, m.getLogo());
            } catch (IOException ex) {
                logo = ".//scr//com//icons//icon.png";
            }
        } else {
            logo = ".//scr//com//icons//icon.png";

            nomeE = "Null";

        }

        try {
            HashMap params = new HashMap();
            params.put("nomeE", nomeE);
            params.put("usuario", usuario);
            File file = new File(logo);
            if (!file.canRead()) {
                MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Não foi possível localizar a imagem que contem o logo da empresa");
                return;
            }
            params.put("logo", logo);
            JasperPrint print;
            if (atendidas) {
                print = JasperFillManager.fillReport(".//src//com//utils//func-2.jasper", params, this.getConnection());
            } else {
                print = JasperFillManager.fillReport(".//src//com//utils//func-3.jasper", params, this.getConnection());
            }
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
        }
    }

}
