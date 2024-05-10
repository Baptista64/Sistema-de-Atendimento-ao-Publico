

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.form;

import com.controller.ControllerCaixa;
import com.controller.ControllerConfiguracoes;
import com.controller.ControllerServico;
import com.model.ModelCaixa;
import com.model.ModelConfiguracao;
import com.model.ModelServico;
import com.swing.custom.components.ImageAvatar;
import com.swing.custom.components.JCheckBoxCustom;
import com.swing.custom.components.MaterialTabbed;
import com.swing.custom.components.ScrollPane;
import com.swing.custom.components.TextField;
import datechooser.Button;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import load.Loading;
import message.MessageChooser;
import message.MessageDialog;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author baptista
 */
public class PanelConfiguration extends javax.swing.JPanel {
    
    private final JFrame frame;
    private File logoFile;

    /**
     * Creates new form PanelConfigurations
     *
     * @param frame
     */
    public PanelConfiguration(JFrame frame) {
        initComponents();
        init();
        this.frame = frame;
    }
    
    public ModelConfiguracao getConfigs() {
        ModelConfiguracao config = new ControllerConfiguracoes().getConfig(frame);
        return config;
    }
    
    private void init() {
        ModelConfiguracao configs = getConfigs();
        txtNome.setText(configs.getNome());
        logo.setImage(new ImageIcon(configs.getLogo()));
    }
    
    private void KeyTyped( TextField text) {
        text.setHelperText("");
    }
    
    private void saveConfig() {
        
        ModelConfiguracao config = getConfigs();
        if (config == null) {
            return;
        }
        
        if (logoFile != null) {
            try {
                config.setLogo(FileUtils.readFileToByteArray(logoFile));
            } catch (IOException ex) {
                MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Um ou mais arquivos de configuração não foram encontrados!");
                return;
            }
        }
        
        if (txtNome.getText().length() < 5) {
            txtNome.setHelperText("Digite um nome válido com pelo menos 5 caracteres");
            return;
        }
        
        load.Loading load = new Loading(frame, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                config.setNome(txtNome.getText());
                
                int save = new ControllerConfiguracoes().saveConfiguracao(frame, config);
                load.dispose();
                if (save < 1) {
                    if (save == 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Alguma coisa correu mal ao tentar salvar as configurações! Tente novamente");
                    }
                } else if (save > 0) {
                    MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                    message.showMessage("AVISO!", "Configuração salva");
                }
            }
        }).start();
        load.setVisible(true);
        
    }
    
    private void selectLogo() {
        JFileChooser fChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Somente arquivos(png, jpeg, jpg)", "png", "jpeg", "jpg");
        fChooser.setAcceptAllFileFilterUsed(false);
        fChooser.setFileFilter(filter);
        int confirm = fChooser.showDialog(null, "Selecionar");
        if (confirm == JFileChooser.APPROVE_OPTION) {
            logoFile = fChooser.getSelectedFile();
            logo.setImage(new ImageIcon(logoFile.getAbsolutePath()));
            repaint();
            revalidate();
        }
    }
    
    private void novoServico() {
        txtIdServ.setText("");
        txtNomeServ.setText("");
        txtNomeServ.setHelperText("");
        txtAreaServ.setText("");
    }
    
    private void novoCaixa() {
        txtIdCaixa.setText("");
        txtNomeCaixa.setText("");
        txtNomeCaixa.setHelperText("");
        checkPrioridade.setSelected(false);
    }
    
    private boolean verifCaixa() {
        if (txtNomeCaixa.getText().length() < 5) {
            txtNomeCaixa.setHelperText("O nome do caixa deve conter ao menos 5 caracteres");
            return false;
        }
        if (txtNomeCaixa.getText().endsWith(" ")) {
            txtNomeCaixa.setHelperText("O nome do caixa não deve terminar com espaço");
            return false;
        }
        return true;
    }
    
    private boolean verifServ() {
        if (txtNomeServ.getText().length() < 5) {
            txtNomeServ.setHelperText("O nome do serviço deve conter ao menos 5 caracteres");
            return false;
        }
        if (txtNomeServ.getText().endsWith(" ")) {
            txtNomeServ.setHelperText("O nome do serviço não deve terminar com espaço");
            return false;
        }
        return true;
    }
    
    private void saveServico() {
        if (verifServ()) {
            
            load.Loading load = new Loading(frame, true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    ModelServico servico = new ModelServico();
                    servico.setNome(txtNomeServ.getText());
                    servico.setDescricao(txtAreaServ.getText());
                    int save = new ControllerServico().save(frame, servico);
                    load.dispose();
                    if (save == 1) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Serviço salvo com sucesso!");
                        novoCaixa();
                    } else if (save == 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Algo correu mal! Tente novamente");
                    }
                    
                }
            }).start();
            load.setVisible(true);
            
        }
    }
    
    private void saveCaixa() {
        
        if (verifCaixa()) {
            
            load.Loading load = new Loading(frame, true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ModelCaixa caixa = new ModelCaixa();
                    caixa.setNome(txtNomeCaixa.getText());
                    if (checkPrioridade.isSelected()) {
                        caixa.setPrioridade(0);
                    } else {
                        caixa.setPrioridade(1);
                    }
                    int save = new ControllerCaixa().save(frame, caixa);
                    load.dispose();
                    if (save == 1) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Caixa salvo com sucesso!");
                        novoCaixa();
                    } else if (save == 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Algo correu mal! Tente novamente");
                    }
                }
            }).start();
            load.setVisible(true);
            
        }
        
    }
    
    private void updateServico() {
        if (txtIdServ.getText().length() < 1) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("AVISO!", "O serviço não existe! Cerifique que procurou o serviço clicando no botão [Procurar]");
            return;
        }
        
        if (verifServ()) {
            
            load.Loading load = new Loading(frame, true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    ModelServico servico = new ModelServico();
                    servico.setId(Integer.parseInt(txtIdServ.getText()));
                    servico.setNome(txtNomeServ.getText());
                    servico.setDescricao(txtAreaServ.getText());
                    int save = new ControllerServico().edit(frame, servico);
                    load.dispose();
                    if (save > 0) {
                        MessageDialog messageDialog = new MessageDialog(frame, MessageDialog.Type.INFO);
                        messageDialog.showMessage("AVISO!", "Dados atualizados com sucesso!");
                    } else if (save == 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Algo correu mal! Tente novamente");
                    }
                    
                }
            }).start();
            load.setVisible(true);
            
        }
    }
    
    private void updateCaixa() {
        if (txtIdCaixa.getText().length() < 1) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("AVISO!", "O caixa não existe! Cerifique que procurou o caixa clicando no botão [Procurar]");
            return;
        }
        
        if (verifCaixa()) {
            
            load.Loading load = new Loading(frame, true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    ModelCaixa caixa = new ModelCaixa();
                    caixa.setId(Integer.parseInt(txtIdCaixa.getText()));
                    caixa.setNome(txtNomeCaixa.getText());
                    if (checkPrioridade.isSelected()) {
                        caixa.setPrioridade(0);
                    } else {
                        caixa.setPrioridade(1);
                    }
                    int save = new ControllerCaixa().edit(frame, caixa);
                    load.dispose();
                    if (save > 0) {
                        MessageDialog messageDialog = new MessageDialog(frame, MessageDialog.Type.INFO);
                        messageDialog.showMessage("AVISO!", "Dados atualizados com sucesso!");
                    } else if (save == 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("AVISO!", "Algo correu mal! Tente novamente");
                    }
                    
                }
            }).start();
            load.setVisible(true);
            
        }
    }
    
    private void deleteServico() {
        if (txtIdServ.getText().length() < 1) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("AVISO!", "O serviço não existe! Cerifique que procurou o caixa clicando no botão [Procurar]");
            return;
        }
        
        load.Loading load = new Loading(frame, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                
                int update = new ControllerServico().delete(frame, Integer.parseInt(txtIdCaixa.getText()));
                load.dispose();
                if (update == 1) {
                    MessageDialog messageDialog = new MessageDialog(frame, MessageDialog.Type.INFO);
                    messageDialog.showMessage("AVISO!", "Serviço apagado com sucesso!");
                    novoCaixa();
                } else if (update == 0) {
                    MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                    message.showMessage("AVISO!", "Algo correu mal! Tente novamente");
                }
                
            }
        }).start();
        load.setVisible(true);
        
    }
    
    private void deleteCaixa() {
        if (txtIdCaixa.getText().length() < 1) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("AVISO!", "O caixa não existe! Cerifique que procurou o caixa clicando no botão [Procurar]");
            return;
        }
        
        load.Loading load = new Loading(frame, true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int update = new ControllerCaixa().delete(frame, Integer.parseInt(txtIdCaixa.getText()));
                load.dispose();
                if (update == 1) {
                    MessageDialog messageDialog = new MessageDialog(frame, MessageDialog.Type.INFO);
                    messageDialog.showMessage("AVISO!", "Caixa apagado com sucesso!");
                    novoCaixa();
                } else if (update == 0) {
                    MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                    message.showMessage("AVISO!", "Algo correu mal! Tente novamente");
                }
            }
        }).start();
        load.setVisible(true);
        
    }
    
    private void searchServic() {
        Loading load = new Loading(frame, true);
        MessageChooser chooser = new MessageChooser(frame, MessageChooser.Type.CHOOSE, "Lista de serviços");
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    ArrayList<com.model.ModelServico> servs = new ControllerServico().select(frame, chooser.getItemChoosen());
                    if (servs.size() > 0) {
                        
                        for (ModelServico s : servs) {
                            chooser.addItem(s.getNome());
                        }
                        chooser.showMessage("Procurar serviço", "Escolha o serviço que deseja atualizar os dados");
                        String itemChoosen = chooser.getItemChoosen();
                       ModelServico servico = new ControllerServico().selects(frame, itemChoosen);
                        if (servico != null) {
                            txtIdServ.setText(servico.getId() + "");
                            txtNomeServ.setText(servico.getNome());
                            txtAreaServ.setText(servico.getDescricao());
                            load.dispose();
                        }
                        
                    }
                    
                }
            }).start();
            load.setVisible(true);
            
        } catch (Exception e) {
            load.dispose();
        }
    }
    
    public void searchCaixa() {
        Loading load = new Loading(frame, true);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    ArrayList<ModelCaixa> caixas = new ControllerCaixa().select(frame);
                    if (caixas.size() > 0) {
                                MessageChooser chooser = new MessageChooser(frame, MessageChooser.Type.CHOOSE, "Total de caixas: " + caixas.size());

                        for (ModelCaixa c : caixas) {
                            chooser.addItem(c.getNome());
                        }
                        chooser.showMessage("Procurar caixa", "Escolha o caixa que deseja atualizar os dados");
                        String itemChoosen = chooser.getItemChoosen();
                        ModelCaixa caixa = new ControllerCaixa().select(frame, itemChoosen);
                        if (caixa != null) {
                            txtIdCaixa.setText(caixa.getId() + "");
                            txtNomeCaixa.setText(caixa.getNome());
                            checkPrioridade.setSelected(caixa.getPrioridade() == 0);
                            load.dispose();
                        }
                        
                    }
                    
                }
            }).start();
            load.setVisible(true);
            
        } catch (Exception e) {
            load.dispose();
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

        panelNorth = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        panelSouth = new javax.swing.JPanel();
        panelCenter = new javax.swing.JPanel();
        materialTabbed1 = new  MaterialTabbed();
        tab1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        button1 = new  Button();
        jPanel6 = new javax.swing.JPanel();
        lblTitle1 = new javax.swing.JLabel();
        logo = new  ImageAvatar();
        button2 = new  Button();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtNome = new  TextField();
        tab2 = new javax.swing.JPanel();
        txtIdCaixa = new  TextField();
        jPanel12 = new javax.swing.JPanel();
        btnNovoCaixa = new  Button();
        btnSaveCaixa = new  Button();
        btnUpdateCaixa = new  Button();
        btnDeleteCaixa = new  Button();
        btnSearchCaixa = new  Button();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        txtNomeCaixa = new  TextField();
        checkPrioridade = new  JCheckBoxCustom();
        tab3 = new javax.swing.JPanel();
        txtIdServ = new  TextField();
        jPanel10 = new javax.swing.JPanel();
        btnNovoServ = new  Button();
        btnSaveServ = new  Button();
        btnUpdateServ = new  Button();
        btnDeleteServ = new  Button();
        btnSearchServ = new  Button();
        jPanel11 = new javax.swing.JPanel();
        scrollPane2 = new  ScrollPane();
        txtAreaServ = new javax.swing.JTextArea();
        txtNomeServ = new  TextField();

        setBackground(new java.awt.Color(254, 254, 254));
        setLayout(new java.awt.BorderLayout());

        panelNorth.setOpaque(false);
        panelNorth.setPreferredSize(new java.awt.Dimension(293, 50));
        panelNorth.setLayout(new java.awt.BorderLayout());

        lblTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(126, 210, 244));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Configurações");
        lblTitle.setPreferredSize(new java.awt.Dimension(243, 79));
        panelNorth.add(lblTitle, java.awt.BorderLayout.CENTER);

        add(panelNorth, java.awt.BorderLayout.PAGE_START);

        panelSouth.setBackground(new java.awt.Color(210, 99, 99));
        panelSouth.setOpaque(false);
        panelSouth.setPreferredSize(new java.awt.Dimension(525, 60));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout1.setAlignOnBaseline(true);
        panelSouth.setLayout(flowLayout1);
        add(panelSouth, java.awt.BorderLayout.PAGE_END);

        panelCenter.setOpaque(false);
        panelCenter.setLayout(new javax.swing.BoxLayout(panelCenter, javax.swing.BoxLayout.LINE_AXIS));

        materialTabbed1.setForeground(new java.awt.Color(5, 182, 254));
        materialTabbed1.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N

        tab1.setOpaque(false);
        tab1.setLayout(new java.awt.BorderLayout());

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(124, 150));
        jPanel5.setLayout(new java.awt.BorderLayout());

        button1.setBackground(new java.awt.Color(25, 182, 247));
        button1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Selecionar");
        button1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        jPanel5.add(button1, java.awt.BorderLayout.PAGE_END);

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new java.awt.BorderLayout());

        lblTitle1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(5, 182, 254));
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText("Logo da empresa");
        lblTitle1.setPreferredSize(new java.awt.Dimension(243, 30));
        jPanel6.add(lblTitle1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);
        jPanel5.add(logo, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        button2.setBackground(new java.awt.Color(25, 182, 247));
        button2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button2.setForeground(new java.awt.Color(254, 254, 254));
        button2.setText("Salvar");
        button2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel3.add(button2, java.awt.BorderLayout.PAGE_END);

        tab1.add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(254, 254, 254));
        jPanel2.setPreferredSize(new java.awt.Dimension(10, 90));
        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel7.setBackground(new java.awt.Color(254, 254, 254));
        jPanel7.setPreferredSize(new java.awt.Dimension(100, 10));
        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(254, 254, 254));
        jPanel8.setPreferredSize(new java.awt.Dimension(10, 90));
        jPanel1.add(jPanel8, java.awt.BorderLayout.LINE_END);

        jPanel9.setBackground(new java.awt.Color(254, 254, 254));
        jPanel9.setPreferredSize(new java.awt.Dimension(100, 10));
        jPanel1.add(jPanel9, java.awt.BorderLayout.PAGE_END);

        txtNome.setLabelText("Nome da empresa");
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeKeyTyped(evt);
            }
        });
        jPanel1.add(txtNome, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tab1.add(jPanel4, java.awt.BorderLayout.CENTER);

        materialTabbed1.addTab("Geral", tab1);

        tab2.setBackground(new java.awt.Color(254, 254, 254));
        tab2.setLayout(new java.awt.BorderLayout());

        txtIdCaixa.setLabelText("ID");
        tab2.add(txtIdCaixa, java.awt.BorderLayout.PAGE_START);

        jPanel12.setBackground(new java.awt.Color(254, 254, 254));
        jPanel12.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));

        btnNovoCaixa.setBackground(new java.awt.Color(25, 182, 247));
        btnNovoCaixa.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnNovoCaixa.setForeground(new java.awt.Color(255, 255, 255));
        btnNovoCaixa.setText("Novo");
        btnNovoCaixa.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnNovoCaixa.setOpaque(true);
        btnNovoCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoCaixaActionPerformed(evt);
            }
        });
        jPanel12.add(btnNovoCaixa);

        btnSaveCaixa.setBackground(new java.awt.Color(25, 182, 247));
        btnSaveCaixa.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSaveCaixa.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveCaixa.setText("Salvar");
        btnSaveCaixa.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnSaveCaixa.setOpaque(true);
        btnSaveCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveCaixaActionPerformed(evt);
            }
        });
        jPanel12.add(btnSaveCaixa);

        btnUpdateCaixa.setBackground(new java.awt.Color(25, 182, 247));
        btnUpdateCaixa.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnUpdateCaixa.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateCaixa.setText("Atualizar");
        btnUpdateCaixa.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnUpdateCaixa.setOpaque(true);
        btnUpdateCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCaixaActionPerformed(evt);
            }
        });
        jPanel12.add(btnUpdateCaixa);

        btnDeleteCaixa.setBackground(new java.awt.Color(25, 182, 247));
        btnDeleteCaixa.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnDeleteCaixa.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteCaixa.setText("Apagar");
        btnDeleteCaixa.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnDeleteCaixa.setOpaque(true);
        btnDeleteCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCaixaActionPerformed(evt);
            }
        });
        jPanel12.add(btnDeleteCaixa);

        btnSearchCaixa.setBackground(new java.awt.Color(25, 182, 247));
        btnSearchCaixa.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSearchCaixa.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchCaixa.setText("Procurar");
        btnSearchCaixa.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnSearchCaixa.setOpaque(true);
        btnSearchCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCaixaActionPerformed(evt);
            }
        });
        jPanel12.add(btnSearchCaixa);

        tab2.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        jPanel13.setBackground(new java.awt.Color(254, 254, 254));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(254, 254, 254));
        jPanel14.setPreferredSize(new java.awt.Dimension(100, 140));
        jPanel14.setLayout(new java.awt.GridLayout(2, 0));

        txtNomeCaixa.setLabelText("Nome");
        txtNomeCaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeCaixaKeyTyped(evt);
            }
        });
        jPanel14.add(txtNomeCaixa);

        checkPrioridade.setText("Prioridade");
        jPanel14.add(checkPrioridade);

        jPanel13.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        tab2.add(jPanel13, java.awt.BorderLayout.CENTER);

        materialTabbed1.addTab("Caixas", tab2);

        tab3.setBackground(new java.awt.Color(254, 254, 254));
        tab3.setLayout(new java.awt.BorderLayout());

        txtIdServ.setEditable(false);
        txtIdServ.setLabelText("ID");
        tab3.add(txtIdServ, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(254, 254, 254));
        jPanel10.setPreferredSize(new java.awt.Dimension(10, 50));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));

        btnNovoServ.setBackground(new java.awt.Color(25, 182, 247));
        btnNovoServ.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnNovoServ.setForeground(new java.awt.Color(255, 255, 255));
        btnNovoServ.setText("Novo");
        btnNovoServ.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnNovoServ.setOpaque(true);
        btnNovoServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoServActionPerformed(evt);
            }
        });
        jPanel10.add(btnNovoServ);

        btnSaveServ.setBackground(new java.awt.Color(25, 182, 247));
        btnSaveServ.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSaveServ.setForeground(new java.awt.Color(255, 255, 255));
        btnSaveServ.setText("Salvar");
        btnSaveServ.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnSaveServ.setOpaque(true);
        btnSaveServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveServActionPerformed(evt);
            }
        });
        jPanel10.add(btnSaveServ);

        btnUpdateServ.setBackground(new java.awt.Color(25, 182, 247));
        btnUpdateServ.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnUpdateServ.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateServ.setText("Atualizar");
        btnUpdateServ.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnUpdateServ.setOpaque(true);
        btnUpdateServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateServActionPerformed(evt);
            }
        });
        jPanel10.add(btnUpdateServ);

        btnDeleteServ.setBackground(new java.awt.Color(25, 182, 247));
        btnDeleteServ.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnDeleteServ.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteServ.setText("Apagar");
        btnDeleteServ.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnDeleteServ.setOpaque(true);
        btnDeleteServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteServActionPerformed(evt);
            }
        });
        jPanel10.add(btnDeleteServ);

        btnSearchServ.setBackground(new java.awt.Color(25, 182, 247));
        btnSearchServ.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSearchServ.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchServ.setText("Procurar");
        btnSearchServ.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnSearchServ.setOpaque(true);
        btnSearchServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchServActionPerformed(evt);
            }
        });
        jPanel10.add(btnSearchServ);

        tab3.add(jPanel10, java.awt.BorderLayout.PAGE_END);

        jPanel11.setBackground(new java.awt.Color(254, 254, 254));
        jPanel11.setLayout(new java.awt.BorderLayout());

        scrollPane2.setBackground(new java.awt.Color(254, 254, 254));
        scrollPane2.setLabelText("Descrição");
        scrollPane2.setPreferredSize(new java.awt.Dimension(8, 170));

        txtAreaServ.setColumns(20);
        txtAreaServ.setRows(5);
        scrollPane2.setViewportView(txtAreaServ);

        jPanel11.add(scrollPane2, java.awt.BorderLayout.PAGE_END);

        txtNomeServ.setLabelText("Nome");
        txtNomeServ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeServKeyTyped(evt);
            }
        });
        jPanel11.add(txtNomeServ, java.awt.BorderLayout.PAGE_START);

        tab3.add(jPanel11, java.awt.BorderLayout.CENTER);

        materialTabbed1.addTab("Serviços", tab3);

        panelCenter.add(materialTabbed1);

        add(panelCenter, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeServKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeServKeyTyped
        KeyTyped(txtNomeServ);
    }//GEN-LAST:event_txtNomeServKeyTyped

    private void btnSearchServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchServActionPerformed
        searchServic();
    }//GEN-LAST:event_btnSearchServActionPerformed

    private void btnDeleteServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteServActionPerformed
        setCursor(new Cursor(Cursor.WAIT_CURSOR) {
        });
        
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("AVISO!", "Apagar este serviço?");
        
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            deleteServico();
        }
        
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR) {
        });
    }//GEN-LAST:event_btnDeleteServActionPerformed

    private void btnUpdateServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateServActionPerformed
        setCursor(new Cursor(Cursor.WAIT_CURSOR) {
        });
        
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("AVISO!", "Atualizar os dados  deste serviço?");
        
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            updateServico();
        }
        
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR) {
        });
    }//GEN-LAST:event_btnUpdateServActionPerformed

    private void btnSaveServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveServActionPerformed
        setCursor(new Cursor(Cursor.WAIT_CURSOR) {
        });
        
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("AVISO!", "Salvar este serviço?");
        
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            saveServico();
        }
        
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR) {
        });
    }//GEN-LAST:event_btnSaveServActionPerformed

    private void btnNovoServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoServActionPerformed
        novoServico();
    }//GEN-LAST:event_btnNovoServActionPerformed

    private void txtNomeCaixaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeCaixaKeyTyped
        KeyTyped(txtNomeCaixa);
        
        if (txtNomeCaixa.getText().contains("-")) {
            
            if ("-".equals(evt.getKeyChar() + "")) {
                evt.consume();
            }
            if (!"0987654321".contains(evt.getKeyChar() + "")) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtNomeCaixaKeyTyped

    private void btnSearchCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCaixaActionPerformed
        searchCaixa();
    }//GEN-LAST:event_btnSearchCaixaActionPerformed

    private void btnDeleteCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCaixaActionPerformed
        setCursor(new Cursor(Cursor.WAIT_CURSOR) {
        });
        
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("AVISO!", "Deseja apagar este caixa?");
        
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            deleteCaixa();
        }
        
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR) {
        });
    }//GEN-LAST:event_btnDeleteCaixaActionPerformed

    private void btnUpdateCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCaixaActionPerformed
        
        String text[] = txtNomeCaixa.getText().split("-");
        
        if (text.length != 2) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Insira um nome válido \n Exemplo: Caixa-3");
            return;
        }
        
        try {
            int num = Integer.parseInt(text[1]);
        } catch (NumberFormatException e) {
            
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Insira um nome válido \n Exemplo: Caixa-3");
            return;
            
        }
        
        setCursor(new Cursor(Cursor.WAIT_CURSOR) {
        });
        
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("AVISO!", "Atualizar os dados do caixa?");
        
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            updateCaixa();
        }
        
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR) {
        });
    }//GEN-LAST:event_btnUpdateCaixaActionPerformed

    private void btnSaveCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveCaixaActionPerformed
        
        String text[] = txtNomeCaixa.getText().split("-");
        
        if (text.length != 2) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Insira um nome válido \n Exemplo: Caixa-3");
            return;
        }
        
        try {
            int num = Integer.parseInt(text[1]);
            
            if (num < 1) {
                
                MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                message.showMessage("Aviso!", "0 [zero] não é válido! Digite outro número");
                return;
            }
            
        } catch (NumberFormatException e) {
            
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Insira um nome válido \n Exemplo: Caixa-3");
            return;
            
        }
        
        setCursor(new Cursor(Cursor.WAIT_CURSOR) {
        });
        
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("AVISO!", "Salvar o caixa?");
        
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            saveCaixa();
        }
        
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR) {
        });
    }//GEN-LAST:event_btnSaveCaixaActionPerformed

    private void btnNovoCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoCaixaActionPerformed
        novoCaixa();
    }//GEN-LAST:event_btnNovoCaixaActionPerformed

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
        KeyTyped(txtNome);
    }//GEN-LAST:event_txtNomeKeyTyped

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        setCursor(new Cursor(Cursor.WAIT_CURSOR) {
        });
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("AVISO!", "Salvar as configurações?");
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            saveConfig();
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR) {
        });
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        selectLogo();
    }//GEN-LAST:event_button1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private  Button btnDeleteCaixa;
    private  Button btnDeleteServ;
    private  Button btnNovoCaixa;
    private  Button btnNovoServ;
    private  Button btnSaveCaixa;
    private  Button btnSaveServ;
    private  Button btnSearchCaixa;
    private  Button btnSearchServ;
    private  Button btnUpdateCaixa;
    private  Button btnUpdateServ;
    private  Button button1;
    private  Button button2;
    private  JCheckBoxCustom checkPrioridade;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private  ImageAvatar logo;
    private  MaterialTabbed materialTabbed1;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelNorth;
    private javax.swing.JPanel panelSouth;
    private  ScrollPane scrollPane2;
    private javax.swing.JPanel tab1;
    private javax.swing.JPanel tab2;
    private javax.swing.JPanel tab3;
    private javax.swing.JTextArea txtAreaServ;
    private  TextField txtIdCaixa;
    private  TextField txtIdServ;
    private  TextField txtNome;
    private  TextField txtNomeCaixa;
    private  TextField txtNomeServ;
    // End of variables declaration//GEN-END:variables
}
