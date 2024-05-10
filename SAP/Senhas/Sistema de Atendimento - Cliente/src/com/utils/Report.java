/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import java.io.File;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import com.view.Senhas;
import message.MessageDialog;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author tobi
 */
public class Report extends connection.Connect {

    public static String logo = ".//src//logoE.png";
    public static String foto = "foto.png";
    public static String senhaIcon = ".//src//com//icons//senha.png";
    public static String nomeE;

    public void imprimirRelatorio(Senhas frame, int id) {
        try {
            HashMap params = new HashMap();
            params.put("id", id);
            File file = new File(senhaIcon);
            if (!file.canRead()) {
                MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Não foi possível carregar a imagem");
                return;
            }
            params.put("foto", senhaIcon);
            JasperPrint print = JasperFillManager.fillReport(".//src//com//utils//senha.jasper", params, this.getConnection());
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
            java.util.logging.Logger.getLogger(Report.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
    }
}
