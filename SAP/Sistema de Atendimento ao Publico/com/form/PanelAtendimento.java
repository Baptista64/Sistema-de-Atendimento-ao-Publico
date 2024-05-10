/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.form;

import com.controller.ControllerCaixa;
import com.controller.ControllerConfiguracoes;
import com.controller.ControllerSenha;
import com.model.ModelAtendimentoFeito;
import com.model.ModelCaixa;
import com.model.ModelSenha;
import com.swing.custom.components.Combobox;
import com.swing.custom.components.TextField;
import com.utils.Utils;
import datechooser.Button;
import java.awt.Cursor;
import java.util.ArrayList;
import load.Loading;
import message.MessageChooser;
import message.MessageCliente;
import message.MessageDialog;

/**
 *
 * @author baptista
 */
public class PanelAtendimento extends javax.swing.JPanel {

    private ArrayList<ModelCaixa> caixas;
    private ArrayList<ModelSenha> senhasEmEspera;
    private ArrayList<ModelSenha> senhasNaoConcluidas;
    private ModelSenha senhaNaoConcluidaActual;
    private ModelCaixa cai;
    private final com.form.Main fram;

    public PanelAtendimento(com.form.Main frame, ModelCaixa caixa) {
        this.fram = frame;
        initComponents();
        init();
        cai = caixa;
        abrirCaixa(true);
    }

    /**
     * Creates new form PanelServico
     *
     * @param frame
     */
    public PanelAtendimento(com.form.Main frame) {
        this.fram = frame;
        initComponents();
        init();
    }

    private void init() {
        getCaixas();
    }

    private void getCaixas() {
        comboBoxCaixa.removeAllItems();
        caixas = new ControllerCaixa().select(fram);
        for (ModelCaixa caixa : caixas) {
            comboBoxCaixa.addItem(caixa.getNome());
        }
    }

    private void proximaSenha() {
        if (senhaNaoConcluidaActual != null) {
            MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Conclua primeiro a senha não atendida actual ou atualize");
            return;
        }

        load.Loading load = new Loading(fram, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (senhasEmEspera.size() > 0) {
                    for (ModelSenha s : senhasEmEspera) {

                        if (txtSenhaA.getText().contains(s.getNome())) {
                            int idServico = s.getId();
                            int update = new ControllerSenha().setServicoNaoConcluido(fram, idServico);
                            load.dispose();
                            if (update > 0) {
                                senhasEmEspera.remove(s);
                                atualizar();
                                break;
                            } else {
                                MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
                                message.showMessage("Aviso!", "Não foi possível chamar a senha seguinte! Tente novamente");
                            }
                        }
                    }

                }
                load.dispose();
            }
        }).start();
        load.setVisible(true);

    }

    private void abrirCaixa(boolean initial) {

        load.Loading l = new Loading(fram, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                caixas = new ControllerCaixa().select(fram);
                l.dispose();
            }
        }).start();
        l.setVisible(true);

        if (!initial) {
            if (btnACaixa.getText().contains("Fechar caixa")) {
                message.MessageDialog messa = new MessageDialog(fram, MessageDialog.Type.QUESTION);
                messa.showMessage("Aviso", "Deseja fechar este caixa?");
                if (messa.getMessageType() == MessageDialog.MessageType.OK) {

                    load.Loading load = new Loading(fram, true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int update = new ControllerCaixa().abrirFecharCaixa(fram, 0, cai.getId());
                            if (update > 0) {
                                txtSenhaA.setText("");
                                txtSenhaS.setText("");
                                caixas = new ControllerCaixa().select(fram);
                                getCaixas();
                                btnACaixa.setText("Abrir caixa");
                                repaint();
                                revalidate();
                                cai = null;
                                fram.setCaixa(cai);
                                load.dispose();
                            }
                        }
                    }).start();
                    load.setVisible(true);

                    return;
                }

            }
            int index = comboBoxCaixa.getSelectedIndex();

            if (caixas.get(index).getAberto() == 1) {
                message.MessageDialog messa = new MessageDialog(fram, MessageDialog.Type.INFO);
                messa.showMessage("Aviso", "Esse caixa já foi aberto por outro usuário");
                return;
            }
            if (cai != null) {
                if (cai != caixas.get(index) && cai != null) {
                    message.MessageDialog messa = new MessageDialog(fram, MessageDialog.Type.QUESTION);
                    messa.showMessage("Aviso", "Ao abrir um outro caixa, o caixa que abriu anteriormente será fechado! Deseja continuar?");
                    if (messa.getMessageType() == MessageDialog.MessageType.CANCEL) {
                        return;
                    } else {

                        load.Loading load = new Loading(fram, true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                int update = new ControllerCaixa().abrirFecharCaixa(fram, 0, cai.getId());
                                load.dispose();
                                if (update < 1) {
                                    return;
                                }
                            }
                        }).start();
                        load.setVisible(true);

                    }
                }
            }

            MessageChooser message = new MessageChooser(fram, MessageChooser.Type.INPUT, "Usuário");
            message.showMessage("Digite", "Digite o seu usuário");
            String usuario = message.getItemChoosen();
            message = new MessageChooser(fram, MessageChooser.Type.INPUT, "Senha");
            message.showMessage("Digite", "Digite a sua senha");
            String senha = message.getItemChoosen();
            if (!fram.verificarSenhaDoUsuario(usuario, senha)) {
                MessageDialog mes = new MessageDialog(fram, MessageDialog.Type.INFO);
                mes.showMessage("Aviso", "Usuário e/ou senha inválidos! Tente novamente");
                return;
            }

            load.Loading load = new Loading(fram, true);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    int update = new ControllerCaixa().abrirFecharCaixa(fram, 1, caixas.get(index).getId());

                    if (update < 1) {
                        load.dispose();
                        return;

                    }

                    if (caixas != null && caixas.size() > 0) {
                        btnACaixa.setText("Fechar caixa");
                        cai = caixas.get(comboBoxCaixa.getSelectedIndex());
                        fram.setCaixa(cai);
                        ControllerSenha cSer = new ControllerSenha();
                        senhasEmEspera = cSer.getAllServicesInWait(fram, Utils.getFormatedDate(), cai.getPrioridade() == 0, 0);
                        load.dispose();
                    }
                }
            }).start();
            load.setVisible(true);

        } else {

            load.Loading load = new Loading(fram, true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    btnACaixa.setText("Fechar caixa");
                    ControllerSenha cSer = new ControllerSenha();
                    senhasEmEspera = cSer.getAllServicesInWait(fram, Utils.getFormatedDate(), cai.getPrioridade() == 0, 0);
                    load.dispose();
                }
            }).start();
            load.setVisible(true);

        }

        atualizar();

    }

    private void concluirAtendimento() {

        load.Loading load = new Loading(fram, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ModelSenha ser = new ModelSenha();
                if (senhaNaoConcluidaActual != null) {
                    ser = senhaNaoConcluidaActual;
                } else {
                    if (senhasEmEspera.size() > 0) {
                        for (ModelSenha s : senhasEmEspera) {
                            if (txtSenhaA.getText().contains(s.getNome())) {
                                ser = s;
                            }
                        }
                    }
                }
                ModelAtendimentoFeito atendimentoFeito = new ModelAtendimentoFeito();
                atendimentoFeito.setData(Utils.getFormatedDate());
                atendimentoFeito.setIdServico(ser.getId());
                atendimentoFeito.setIdUsuario(fram.getUserId());
                int save = new ControllerSenha().setServiceConcluido(fram, atendimentoFeito.getIdUsuario(), atendimentoFeito.getIdServico(), Utils.getFormatedDate());
                load.dispose();
                if (save > 0) {
                    if (senhaNaoConcluidaActual == null) {
                        if (senhasEmEspera.size() > 0) {
                            senhasEmEspera.remove(0);
                        }
                    } else {
                        senhasNaoConcluidas.remove(senhaNaoConcluidaActual);
                        senhaNaoConcluidaActual = null;
                    }
                    atualizar();
                    MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
                    message.showMessage("Sucesso!", "Antedimento concluido");
                } else {
                    MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
                    message.showMessage("Aviso!", "Antedimento não concluido! Tente novamente!");
                }
            }
        }).start();
        load.setVisible(true);

    }

    private void getSenhaNaoConcluida(boolean emEspera) {

        load.Loading load = new Loading(fram, true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                senhaNaoConcluidaActual = null;

                if (!emEspera) {

                    senhasNaoConcluidas = new ControllerSenha().getAllServicesInWait(fram, Utils.getFormatedDate(), cai.getPrioridade() == 0, 2);

                } else {

                    senhasNaoConcluidas = new ControllerSenha().getAllServicesInWait(fram, Utils.getFormatedDate(), cai.getPrioridade() == 0, 0);

                }

                if (senhasNaoConcluidas.size() > 0) {
                    load.dispose();
                    message.MessageChooser chooser = new MessageChooser(fram, MessageChooser.Type.CHOOSE, "");
                    for (ModelSenha senha : senhasNaoConcluidas) {
                        if (!emEspera) {

                            chooser.addItem(senha.getNome() + ": Não atendida");
                        } else {

                            chooser.addItem(senha.getNome() + ": Em espera");
                        }
                    }
                    chooser.showMessage("Escolha", "Escolha a senha que prentende atender");
                    String itemChoosen = chooser.getItemChoosen();
                    for (ModelSenha senha : senhasNaoConcluidas) {
                        if (itemChoosen.contains(senha.getNome())) {
                            senhaNaoConcluidaActual = senha;
                            txtSenhaS.setText(txtSenhaA.getText());
                            txtSenhaA.setText(itemChoosen);
                            break;
                        }
                    }
                } else {
                    load.dispose();
                    MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
                    message.showMessage("Aviso!", "Não há nenhuma senha em espera de hoje");
                }

            }
        }).start();
        load.setVisible(true);

    }

    private void atualizar() {
        if (cai == null) {
            MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Abra primeiro um caixa");
            return;
        }

        load.Loading load = new Loading(fram, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                senhasNaoConcluidas = new ControllerSenha().getAllServicesInWait(fram, Utils.getFormatedDate(), cai.getPrioridade() == 0, 2);

                senhasEmEspera = new ControllerSenha().getAllServicesInWait(fram, Utils.getFormatedDate(), cai.getPrioridade() == 0, 0);
                if (senhasEmEspera.size() > 0) {
                    for (int j = 0; j < senhasEmEspera.size(); j++) {

                        if (senhasEmEspera.get(j).getUsuario() == 0 || senhasEmEspera.get(j).getUsuario() == fram.getUserId()) {
                            txtSenhaA.setText(senhasEmEspera.get(j).getNome() + ": Em espera");
                            break;
                        } else {
                            txtSenhaA.setText("Não há mais senhas em espera");
                        }
                    }
                } else {
                    MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
                    message.showMessage("Aviso", "Não há nenhuma senha em espera");
                    txtSenhaA.setText("Não há mais senhas em espera");
                }
                if (senhasEmEspera.size() > 1) {
                    for (int i = 1; i < senhasEmEspera.size(); i++) {
                        if ((senhasEmEspera.get(i).getUsuario() == 0 || senhasEmEspera.get(i).getUsuario() == fram.getUserId()) && !txtSenhaA.getText().contains(senhasEmEspera.get(i).getNome())) {
                            txtSenhaS.setText(senhasEmEspera.get(i).getNome() + ": Em espera");
                            break;
                        } else {
                            txtSenhaS.setText("Não há mais senhas em espera");
                        }
                    }

                } else {
                    txtSenhaS.setText("Não há mais senhas em espera");
                }
                load.dispose();
            }
        }).start();
        load.setVisible(true);

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
        btnNext = new  Button();
        btnConcl = new  Button();
        btnSenhaActual = new  Button();
        btnAtualizar = new  Button();
        btnSenhaNaoC = new  Button();
        jPanel2 = new javax.swing.JPanel();
        comboBoxCaixa = new  Combobox();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnACaixa = new  Button();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtSenhaA = new TextField();
        txtSenhaS = new TextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(626, 50));

        btnNext.setBackground(new java.awt.Color(25, 182, 247));
        btnNext.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText("Senha seguinte");
        btnNext.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnNext.setOpaque(true);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel1.add(btnNext);

        btnConcl.setBackground(new java.awt.Color(25, 182, 247));
        btnConcl.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnConcl.setForeground(new java.awt.Color(255, 255, 255));
        btnConcl.setText("Concluir atendimento");
        btnConcl.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnConcl.setOpaque(true);
        btnConcl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConclActionPerformed(evt);
            }
        });
        jPanel1.add(btnConcl);

        btnSenhaActual.setBackground(new java.awt.Color(25, 182, 247));
        btnSenhaActual.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSenhaActual.setText("Chamar senha actual");
        btnSenhaActual.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnSenhaActual.setOpaque(true);
        btnSenhaActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSenhaActualActionPerformed(evt);
            }
        });
        jPanel1.add(btnSenhaActual);

        btnAtualizar.setBackground(new java.awt.Color(25, 182, 247));
        btnAtualizar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnAtualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnAtualizar.setText("Atualizar");
        btnAtualizar.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnAtualizar.setOpaque(true);
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnAtualizar);

        btnSenhaNaoC.setBackground(new java.awt.Color(25, 182, 247));
        btnSenhaNaoC.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSenhaNaoC.setForeground(new java.awt.Color(255, 255, 255));
        btnSenhaNaoC.setText("Lista de senhas");
        btnSenhaNaoC.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnSenhaNaoC.setOpaque(true);
        btnSenhaNaoC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSenhaNaoCActionPerformed(evt);
            }
        });
        jPanel1.add(btnSenhaNaoC);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(626, 70));
        jPanel2.setLayout(new java.awt.BorderLayout());

        comboBoxCaixa.setLabeText("Caixa actual");
        comboBoxCaixa.setPreferredSize(new java.awt.Dimension(200, 36));
        comboBoxCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxCaixaActionPerformed(evt);
            }
        });
        jPanel2.add(comboBoxCaixa, java.awt.BorderLayout.LINE_END);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(140, 329));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel7, java.awt.BorderLayout.LINE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel8, java.awt.BorderLayout.LINE_END);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel10, java.awt.BorderLayout.PAGE_END);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setPreferredSize(new java.awt.Dimension(120, 39));
        jPanel11.setLayout(new java.awt.BorderLayout());

        btnACaixa.setBackground(new java.awt.Color(25, 182, 247));
        btnACaixa.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnACaixa.setForeground(new java.awt.Color(255, 255, 255));
        btnACaixa.setText("Abrir caixa");
        btnACaixa.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnACaixa.setOpaque(true);
        btnACaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnACaixaActionPerformed(evt);
            }
        });
        jPanel11.add(btnACaixa, java.awt.BorderLayout.PAGE_START);

        jPanel5.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.LINE_START);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(4, 0));

        jPanel4.setOpaque(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 837, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(2, 0));

        txtSenhaA.setEditable(false);
        txtSenhaA.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSenhaA.setLabelText("Senha Actual");
        jPanel6.add(txtSenhaA);

        txtSenhaS.setEditable(false);
        txtSenhaS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSenhaS.setLabelText("Senha Seguinte");
        jPanel6.add(txtSenhaS);

        jPanel3.add(jPanel6);

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnACaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnACaixaActionPerformed
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        abrirCaixa(false);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnACaixaActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed

        if (cai == null) {
            MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Abra primeiro um caixa");
            return;
        }

        if (txtSenhaS.getText().contains("Não há mais senhas em espera")) {
            message.MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Não há senhas em espera");
            return;
        }
        message.MessageDialog message = new MessageDialog(fram, MessageDialog.Type.QUESTION);
        message.showMessage("Proximo", "Chamar próximo cliente?");
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            proximaSenha();
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

    }//GEN-LAST:event_btnNextActionPerformed

    private void btnConclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConclActionPerformed

        if (cai == null) {
            MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Abra primeiro um caixa");
            return;
        }

        if (txtSenhaA.getText().contains("Não há mais senhas em espera")) {
            message.MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Não há senhas em espera");
            return;
        }
        message.MessageDialog message = new MessageDialog(fram, MessageDialog.Type.QUESTION);
        message.showMessage("Concluir", "Concluir o atendimento deste cliente?");
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            concluirAtendimento();
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_btnConclActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        atualizar();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void comboBoxCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxCaixaActionPerformed
        if (cai == null || comboBoxCaixa.getItemCount() < 1) {
            return;
        }
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if ((!cai.getNome().equals(comboBoxCaixa.getSelectedItem().toString()))) {
            btnACaixa.setText("Abrir caixa");
        } else {
            btnACaixa.setText("Fechar caixa");
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_comboBoxCaixaActionPerformed

    private void btnSenhaNaoCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSenhaNaoCActionPerformed

        if (cai == null) {
            MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Abra primeiro um caixa");
            return;
        }

        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        MessageDialog ms = new MessageDialog(fram, MessageDialog.Type.MULTICHOISES);
        ms.showMessage("Escolha", "Exibir lista:" + "\n" + "Sim - Senhas não atendidas" + "\n" + "Não - Senhas em espera");
        if (ms.getMessageType() == MessageDialog.MessageType.NO) {
            getSenhaNaoConcluida(true);
        } else if (ms.getMessageType() == MessageDialog.MessageType.OK) {
            getSenhaNaoConcluida(false);
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }//GEN-LAST:event_btnSenhaNaoCActionPerformed

    private void chamarSenha() {

        load.Loading load = new Loading(fram, true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                int id = 0;

                if (senhaNaoConcluidaActual != null) {

                    id = senhaNaoConcluidaActual.getId();

                } else {

                    if (txtSenhaA == null) {
                        load.dispose();
                        return;
                    }

                    int c = new ControllerConfiguracoes().chamando_cliente(fram);

                    if (c == 1) {
                        load.dispose();
                        MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Outro usuário está chamando um cliente! Tente novamente em alguns momentos");
                        return;
                    }

                    int i = 0;

                    for (ModelSenha s : senhasEmEspera) {
                        if (txtSenhaA.getText().contains(s.getNome())) {
                            i = s.getId();
                            id = i;
                            break;
                        }
                    }

                    int senhaUs = new ControllerSenha().callService(fram, i);
                    if ((senhaUs != fram.getUserId() && senhaUs != 0)) {

                        load.dispose();
                        MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Essa senha já foi chamada por outro usuário! Clique em atualizar");
                        return;
                    }
                }

                int update = new ControllerSenha().setCallServiceUser(fram, fram.getUserId(), id);

                if (update < 1) {
                    load.dispose();
                    return;
                }

                String s[] = txtSenhaA.getText().split(":");
                String s1[] = comboBoxCaixa.getSelectedItem().toString().split(" ");

                int call = new ControllerConfiguracoes().chamarCliente(fram, s[0], s1[1], 1);

                if (call < 1) {
                    load.dispose();
                    return;
                }

                if (!txtSenhaA.getText().contains("Não há mais senhas em espera")) {

                    load.dispose();

                    message.MessageCliente message = new MessageCliente(fram, "Seguinte: " + txtSenhaA.getText());
                    message.showMessage();
                    if (message.isCanceled()) {
                        call = new ControllerConfiguracoes().chamarCliente(fram, "", "", 0);
                        if (call < 1) {
                            MessageDialog mess = new MessageDialog(fram, MessageDialog.Type.INFO);
                            mess.showMessage("AVISO!", "Alguma operação não terminou devidamente ao cancelar o chamado do cliente");
                        }
                    }
                }

                load.dispose();

            }
        }).start();
        load.setVisible(true);

    }


    private void btnSenhaActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSenhaActualActionPerformed

        if (cai == null) {
            MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Abra primeiro um caixa");
            return;
        }

        if (txtSenhaA.getText().contains("Não há mais senhas em espera")) {
            message.MessageDialog message = new MessageDialog(fram, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Não há senhas em espera");
            return;
        }
        chamarSenha();
    }//GEN-LAST:event_btnSenhaActualActionPerformed

    public int getCaixaAberto() {
        if (cai == null) {
            return -1;
        }
        return this.cai.getId();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Button btnACaixa;
    private Button btnAtualizar;
    private Button btnConcl;
    private Button btnNext;
    private Button btnSenhaActual;
    private Button btnSenhaNaoC;
    private Combobox comboBoxCaixa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private TextField txtSenhaA;
    private TextField txtSenhaS;
    // End of variables declaration//GEN-END:variables
}
