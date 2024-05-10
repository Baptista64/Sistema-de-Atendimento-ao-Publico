/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.controller.ControllerAtendimento;
import com.controller.ControllerServico;
import com.model.ModelSenha;
import com.model.ModelServico;
import com.utils.Utils;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import load.Loading;
import message.MessageDialog;

/**
 *
 * @author tobi
 */
public class Senhas extends javax.swing.JFrame {

    String senhasEmEspera;
    private Senhas senha;
    private ArrayList<String> services;
    private com.swing.custom.components.TextField txtNum;

    /**
     * Creates new form PanelSenhas
     */
    public Senhas() {
        initComponents();
        this.setSize(new Dimension(432, 495));
        setLocationRelativeTo(null);
        panelBottom.setVisible(false);
        panelNorth.setVisible(false);
        panelText.setVisible(false);
        this.senha = this;
    }

    private void init() {

        Loading loading = new Loading(this, true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ModelServico> servicos = new ControllerServico().selectAllServices(senha);
                if (servicos == null) {
                    loading.dispose();
                    return;
                }
                panelNorth.setVisible(true);
                panelService.removeAll();
                icon.setIcon(new ImageIcon());
                services = new ArrayList<>();
                panelBottom.setVisible(false);
                panelServiceRight.setPreferredSize(new Dimension(100, 100));
                panelServiceLeft.setPreferredSize(new Dimension(100, 100));
                for (ModelServico ser : servicos) {
                    services.add(ser.getNome());
                }
                panelService.setLayout(new GridLayout(services.size(), 0, 0, 10));
                panelText.setVisible(true);
                for (String s : services) {
                    com.swing.custom.components.Button button = new com.swing.custom.components.Button();
                    button.setText(s);
                    button.setBackground(btSenha.getBackground());
                    button.setFont(btSenha.getFont());
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            setService(button.getText());
                        }

                    });
                    panelService.add(button);
                }
                loading.dispose();
            }
        }).start();
        loading.setVisible(true);

    }

    public String getLetra(int position) {
        switch (position) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "G";
            case 7:
                return "H";
            case 8:
                return "I";
            case 9:
                return "J";
            case 10:
                return "K";
            case 11:
                return "L";
            case 12:
                return "M";
            case 13:
                return "N";
            case 14:
                return "O";
            case 15:
                return "P";
            case 16:
                return "Q";
            case 17:
                return "R";
            case 18:
                return "S";
            case 19:
                return "T";
            case 20:
                return "U";
            case 21:
                return "V";
            case 22:
                return "W";
            case 23:
                return "X";
            case 24:
                return "Y";
            default:
                return "Z";
        }
    }

    public void setService(String buttonText) {
        String letra = "";
        for (int i = 0; i < services.size(); i++) {
            if (buttonText.equals(services.get(i))) {
                letra = getLetra(i);
                break;
            }
        }
        String nome = new ControllerAtendimento().select(this, letra, Utils.getFormatedDate(), 0);
        if (nome != null) {
            addServico(nome);
        }
    }

    private void addServico(String name) {
        panelService.setVisible(false);
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        panelServiceRight.setPreferredSize(new Dimension(10, 100));
       com.swing.custom.components.TextField txtPrioridade = new com.swing.custom.components.TextField();
        txtPrioridade.setLabelText("Prioridade");
        com.swing.custom.components.TextField txtData = new com.swing.custom.components.TextField();
        txtData.setLabelText("Data");
        txtNum = new com.swing.custom.components.TextField();
        txtNum.setLabelText("Senha");
        com.swing.custom.components.TextField txtEstado = new com.swing.custom.components.TextField();
        txtEstado.setLabelText("Estado");
        message.MessageDialog message = new MessageDialog(this, MessageDialog.Type.QUESTION);
        String data = Utils.getFormatedDate();
        ControllerAtendimento cServi = new ControllerAtendimento();
        message.showMessage("Escolha", "Requer prioridade?");
        ModelSenha ser = new ModelSenha();
        ser.setNome(name);
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            txtPrioridade.setText("Sim");
            ser.setPrioridade(0);
        } else {
            txtPrioridade.setText("Não");
            ser.setPrioridade(1);
        }
        ser.setEstado(0);
        ser.setData(data);
        txtData.setText(Utils.getDate());
        txtNum.setText(ser.getNome());
        txtEstado.setText("Em espera");
        int save = cServi.save(this, ser);
        if (save > 0) {
            panelService.removeAll();
            panelText.setVisible(false);
            icon.setIcon(new ImageIcon(".//src//com//icons//senha.png"));
            panelService.setLayout(new GridLayout(5, 0, 0, 0));
            JLabel label = new JLabel();
            label.setText("Informações da senha");
            label.setFont(lblTitle.getFont());
            label.setForeground(lblTitle.getForeground());
            panelService.add(label);
            panelService.add(txtNum);
            panelService.add(txtData);
            panelService.add(txtPrioridade);
            panelService.add(txtEstado);
            panelBottom.setVisible(true);
        } else if (save == 0) {
            message = new MessageDialog(this, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro a criar a senha! Tente novamente!");
        }
        panelService.setVisible(true);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelNorth = new javax.swing.JPanel();
        panelServiceLeft = new javax.swing.JPanel();
        icon = new javax.swing.JLabel();
        panelServiceRight = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        scrollPane1 = new com.swing.custom.components.ScrollPane();
        panelService = new javax.swing.JPanel();
        panelCenter = new javax.swing.JPanel();
        panelText = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        panelBtt = new javax.swing.JPanel();
        btSenha = new com.swing.custom.components.Button();
        panelBottom = new javax.swing.JPanel();
        btnPrint = new com.swing.custom.components.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panelNorth.setBackground(new java.awt.Color(255, 255, 255));
        panelNorth.setPreferredSize(new java.awt.Dimension(100, 350));
        panelNorth.setLayout(new java.awt.BorderLayout());

        panelServiceLeft.setBackground(new java.awt.Color(254, 254, 254));
        panelServiceLeft.setLayout(new java.awt.BorderLayout());

        icon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        panelServiceLeft.add(icon, java.awt.BorderLayout.CENTER);

        panelNorth.add(panelServiceLeft, java.awt.BorderLayout.LINE_START);

        panelServiceRight.setBackground(new java.awt.Color(254, 254, 254));
        panelServiceRight.setPreferredSize(new java.awt.Dimension(10, 100));
        panelNorth.add(panelServiceRight, java.awt.BorderLayout.LINE_END);

        jPanel7.setBackground(new java.awt.Color(254, 254, 254));
        panelNorth.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setBackground(new java.awt.Color(254, 254, 254));
        scrollPane1.setLabelText("");

        panelService.setBackground(new java.awt.Color(254, 254, 254));
        panelService.setLayout(new java.awt.GridLayout(4, 0, 0, 5));
        scrollPane1.setViewportView(panelService);

        panelNorth.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelNorth, java.awt.BorderLayout.PAGE_END);

        panelCenter.setBackground(new java.awt.Color(255, 255, 255));
        panelCenter.setPreferredSize(new java.awt.Dimension(146, 50));
        panelCenter.setLayout(new java.awt.BorderLayout());

        panelText.setBackground(new java.awt.Color(254, 254, 254));
        panelText.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        panelText.setPreferredSize(new java.awt.Dimension(20, 40));
        panelText.setLayout(new java.awt.BorderLayout());

        lblTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(126, 210, 244));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Selecione o serviço");
        lblTitle.setPreferredSize(new java.awt.Dimension(243, 79));
        panelText.add(lblTitle, java.awt.BorderLayout.CENTER);

        panelCenter.add(panelText, java.awt.BorderLayout.PAGE_END);

        panelBtt.setBackground(new java.awt.Color(254, 254, 254));

        btSenha.setBackground(new java.awt.Color(65, 184, 240));
        btSenha.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btSenha.setText("Solicitar senha");
        btSenha.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSenhaActionPerformed(evt);
            }
        });
        panelBtt.add(btSenha);

        panelCenter.add(panelBtt, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelCenter, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        panelBottom.setBackground(new java.awt.Color(255, 255, 255));
        panelBottom.setPreferredSize(new java.awt.Dimension(100, 50));

        btnPrint.setBackground(new java.awt.Color(65, 184, 240));
        btnPrint.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnPrint.setText("Imprimir senha");
        btnPrint.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        panelBottom.add(btnPrint);

        getContentPane().add(panelBottom, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void imprimir() {
        Loading loading = new Loading(senha, true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ModelSenha> allServicesInWait;
                allServicesInWait = new ControllerAtendimento().getAllServicesInWait(senha, Utils.getFormatedDate(), true, 0);
                int id = 0;
                if (allServicesInWait.size() > 0) {
                    for (ModelSenha s : allServicesInWait) {
                        if (s.getNome().equals(txtNum.getText())) {
                            id = s.getId();
                        }
                    }
                    new com.utils.Report().imprimirRelatorio(senha, id);
                }
                loading.dispose();
            }
        }).start();
        loading.setVisible(true);

    }

    private void btnSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        init();
    }//GEN-LAST:event_button1ActionPerformed

    private void btnSenha1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        imprimir();
    }//GEN-LAST:event_button3ActionPerformed

    private void btSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSenhaActionPerformed
        init();
    }//GEN-LAST:event_btSenhaActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        imprimir();
    }//GEN-LAST:event_btnPrintActionPerformed
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Senhas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Senhas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Senhas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Senhas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Senhas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.custom.components.Button btSenha;
    private com.swing.custom.components.Button btnPrint;
    private javax.swing.JLabel icon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelBottom;
    private javax.swing.JPanel panelBtt;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelNorth;
    private javax.swing.JPanel panelService;
    private javax.swing.JPanel panelServiceLeft;
    private javax.swing.JPanel panelServiceRight;
    private javax.swing.JPanel panelText;
    private com.swing.custom.components.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables
}
