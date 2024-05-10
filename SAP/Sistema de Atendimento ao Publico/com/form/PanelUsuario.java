/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.form;

import com.controller.ControllerUsuario;
import com.model.ModelUser;
import datechooser.DateChooser;
import com.swing.custom.components.Combobox;
import com.swing.custom.components.ImageAvatar;
import com.swing.custom.components.MaterialTabbed;
import com.swing.custom.components.TextField;
import datechooser.Button;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import load.Loading;
import message.MessageDialog;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author baptista
 */
public class PanelUsuario extends javax.swing.JPanel {

    private File file;
    private final JFrame frame;
    private final ModelUser user;
    private final DateChooser dataChooser;

    /**
     * Creates new form PanelUsuario
     */
    public PanelUsuario(JFrame frame, ModelUser user, int tipo) {
        initComponents();
        this.user = user;
        this.frame = frame;
        init();
        dataChooser = new DateChooser();
        dataChooser.setDateFormat("");
        dataChooser.setDateFormat("yyyy-MM-dd");
        dataChooser.setTextRefernce(dataNascimento);
        if (tipo != 0) {
            cBoxTipo.setEnabled(false);
            btnNovo.setEnabled(false);
            btnNovo.setForeground(new java.awt.Color(255, 255, 255));
            btnNovo.setBackground(new java.awt.Color(204, 204, 204));
            btnApagar.setEnabled(false);
            btnApagar.setForeground(new java.awt.Color(255, 255, 255));
            btnApagar.setBackground(new java.awt.Color(204, 204, 204));
        }
    }

    private void changueMunin() {
        cBoxProv.setToolTipText("");
        cBoxMun.removeAllItems();
        cBoxMun.setEditable(false);
        cBoxProv.setEditable(false);
        switch (cBoxProv.getSelectedIndex()) {
            case 0:
                cBoxMun.addItem("Ambriz");
                cBoxMun.addItem("Bula Atumba");
                cBoxMun.addItem("Dande");
                cBoxMun.addItem("Dembos");
                cBoxMun.addItem("Nambuangongo");
                cBoxMun.addItem("Pango Aluquém");
                break;
            case 1:
                cBoxMun.addItem("Baía Farta");
                cBoxMun.addItem("Balombo");
                cBoxMun.addItem("Benguela");
                cBoxMun.addItem("Bocolo");
                cBoxMun.addItem("Caimbambo");
                cBoxMun.addItem("Catumbela");
                cBoxMun.addItem("Chongorol");
                cBoxMun.addItem("Cubal");
                cBoxMun.addItem("Ganda");
                cBoxMun.addItem("Lobito");
                break;
            case 2:
                cBoxMun.addItem("Andulo");
                cBoxMun.addItem("Camacupa");
                cBoxMun.addItem("Catabola");
                cBoxMun.addItem("Chinguar");
                cBoxMun.addItem("Chitembo");
                cBoxMun.addItem("Cuemba");
                cBoxMun.addItem("Cunhinga");
                cBoxMun.addItem("Cuito");
                cBoxMun.addItem("Nharea");
                break;
            case 3:
                cBoxMun.addItem("Cabinda");
                cBoxMun.addItem("Cacongo");
                cBoxMun.addItem("Buco-Zau");
                cBoxMun.addItem("Belize");
                break;
            case 4:
                cBoxMun.addItem("Calai");
                cBoxMun.addItem("Cuangar");
                cBoxMun.addItem("Cuchi");
                cBoxMun.addItem("Cuito Cuanavale");
                cBoxMun.addItem("Dirico");
                cBoxMun.addItem("Mavinga");
                cBoxMun.addItem("Menongue");
                cBoxMun.addItem("Nancova");
                cBoxMun.addItem("Rivungo");
                break;
            case 5:
                cBoxMun.addItem("Ambanca");
                cBoxMun.addItem("Banga");
                cBoxMun.addItem("Bolongongo");
                cBoxMun.addItem("Cambambe");
                cBoxMun.addItem("Cazengo");
                cBoxMun.addItem("Golungo Alto");
                cBoxMun.addItem("Gonguembo");
                cBoxMun.addItem("Lucala");
                cBoxMun.addItem("Quiculungo");
                cBoxMun.addItem("Samba Caju");
                break;
            case 6:
                cBoxMun.addItem("Sumbe");
                cBoxMun.addItem("Porto Amboim");
                cBoxMun.addItem("Amboim");
                cBoxMun.addItem("Seles");
                cBoxMun.addItem("Conda");
                cBoxMun.addItem("Ebo");
                cBoxMun.addItem("Quibala");
                cBoxMun.addItem("Quilenda");
                cBoxMun.addItem("Cassongue");
                cBoxMun.addItem("Cela");
                cBoxMun.addItem("Libolo");
                cBoxMun.addItem("Mussende");
                break;
            case 7:
                cBoxMun.addItem("Cahama");
                cBoxMun.addItem("Cuanhama");
                cBoxMun.addItem("Curoca");
                cBoxMun.addItem("Cuvelai");
                cBoxMun.addItem("Namacunde");
                cBoxMun.addItem("Ombadja");
                break;
            case 8:
                cBoxMun.addItem("Huambo");
                cBoxMun.addItem("Bailundo");
                cBoxMun.addItem("Ekunha");
                cBoxMun.addItem("Cáala");
                cBoxMun.addItem("Catchiungo");
                cBoxMun.addItem("Londuimbale");
                cBoxMun.addItem("Longonjo");
                cBoxMun.addItem("Mungo");
                cBoxMun.addItem("Tchicala");
                cBoxMun.addItem("Tcholoanga");
                cBoxMun.addItem("Tchindjenje");
                cBoxMun.addItem("Ucuma");
                break;
            case 9:
                cBoxMun.addItem("Caconda");
                cBoxMun.addItem("Cacula");
                cBoxMun.addItem("Caluquembe");
                cBoxMun.addItem("Chiange");
                cBoxMun.addItem("Chicomba");
                cBoxMun.addItem("Chipindo");
                cBoxMun.addItem("Cuvango");
                cBoxMun.addItem("Humpata");
                cBoxMun.addItem("Jamba");
                cBoxMun.addItem("Lubango");
                cBoxMun.addItem("Matala");
                cBoxMun.addItem("Quilengues");
                cBoxMun.addItem("Quipungo");
                break;
            case 10:
                cBoxMun.addItem("Cacuaco");
                cBoxMun.addItem("Belas");
                cBoxMun.addItem("Cazenga");
                cBoxMun.addItem("Icolo e Bengo");
                cBoxMun.addItem("Luanda");
                cBoxMun.addItem("Quissama");
                cBoxMun.addItem("Quilamba Quiaxi");
                cBoxMun.addItem("Talatona");
                cBoxMun.addItem("Viana");
                break;
            case 11:
                cBoxMun.addItem("Cambulo");
                cBoxMun.addItem("Capenda-Camulemba");
                cBoxMun.addItem("Caungula");
                cBoxMun.addItem("Chiato");
                cBoxMun.addItem("Cuango");
                cBoxMun.addItem("Cuilo");
                cBoxMun.addItem("Lubalo");
                cBoxMun.addItem("Xá-Muteba");
                break;
            case 12:
                cBoxMun.addItem("Cacolo");
                cBoxMun.addItem("Dala");
                cBoxMun.addItem("Muconda");
                cBoxMun.addItem("Saurimo");
                break;
            case 13:
                cBoxMun.addItem("Cacuso");
                cBoxMun.addItem("Calandula");
                cBoxMun.addItem("Cambundi-Catembo");
                cBoxMun.addItem("Cangandala");
                cBoxMun.addItem("Caombo");
                cBoxMun.addItem("Cuaba Nzogo");
                cBoxMun.addItem("Cunda-Diaza");
                cBoxMun.addItem("Luquembo");
                cBoxMun.addItem("Malange");
                cBoxMun.addItem("Marimba");
                cBoxMun.addItem("Massango");
                cBoxMun.addItem("Caculama-Mucari");
                cBoxMun.addItem("Quela");
                cBoxMun.addItem("Quirima");
                break;
            case 14:
                cBoxMun.addItem("Alto Zambeze");
                cBoxMun.addItem("Bundas");
                cBoxMun.addItem("Camanongue");
                cBoxMun.addItem("Cameia");
                cBoxMun.addItem("Luau");
                cBoxMun.addItem("Luacano");
                cBoxMun.addItem("Luchazes");
                cBoxMun.addItem("Léua");
                cBoxMun.addItem("Moxico");
                break;
            case 15:
                cBoxMun.addItem("Bibala");
                cBoxMun.addItem("Camulo");
                cBoxMun.addItem("Namibe");
                cBoxMun.addItem("Tómbua");
                cBoxMun.addItem("Virei");
                break;
            case 16:
                cBoxMun.addItem("Alto Cauale");
                cBoxMun.addItem("Ambuíla");
                cBoxMun.addItem("Bembe");
                cBoxMun.addItem("Buengas");
                cBoxMun.addItem("Bungo");
                cBoxMun.addItem("Damba");
                cBoxMun.addItem("Macocola");
                cBoxMun.addItem("Mucaba");
                cBoxMun.addItem("Negage");
                cBoxMun.addItem("Puri");
                cBoxMun.addItem("Quimbele");
                cBoxMun.addItem("Quitexe");
                cBoxMun.addItem("Sanza Pombo");
                cBoxMun.addItem("Songo");
                cBoxMun.addItem("Uíge");
                cBoxMun.addItem("Maquela Zombo");
                break;
            case 17:
                cBoxMun.addItem("Cuimba");
                cBoxMun.addItem("M´Banza Kongo");
                cBoxMun.addItem("Noqui");
                cBoxMun.addItem("N´Zeto");
                cBoxMun.addItem("Soyo");
                cBoxMun.addItem("Tomboco");
                break;
            default:
                cBoxProv.setToolTipText("Apage e escreva");
                cBoxMun.setToolTipText("escreva");
                cBoxProv.setEditable(true);
                cBoxMun.setEditable(true);
        }
    }

    private void init() {
        cBoxProv.addItem("Bengo");
        cBoxProv.addItem("Benguela");
        cBoxProv.addItem("Bié");
        cBoxProv.addItem("Cabinda");
        cBoxProv.addItem("Cuando Cubango");
        cBoxProv.addItem("Cuanza Norte");
        cBoxProv.addItem("Cuanza Sul");
        cBoxProv.addItem("Cunene");
        cBoxProv.addItem("Huambo");
        cBoxProv.addItem("Huila");
        cBoxProv.addItem("Luanda");
        cBoxProv.addItem("Lunda Norte");
        cBoxProv.addItem("Lunda Sul");
        cBoxProv.addItem("Malanje");
        cBoxProv.addItem("Moxico");
        cBoxProv.addItem("Namibe");
        cBoxProv.addItem("Uige");
        cBoxProv.addItem("Zaire");
        cBoxTipo.addItem("Administrador");
        cBoxTipo.addItem("Comum");
        cBoxTipo.setSelectedIndex(1);
        if (user == null) {
            setTitle("Cadastro de usuario");
            logo.setImage(new ImageIcon(".//src//com//icons//user.png"));
        } else {
            setTitle("Dados do usuário");
            setValues();
        }
        txtBI.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                txtBIKeyTyped(evt);
            }
        });
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        txtUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                txtUserKeyTyped(evt);
            }
        });
        btnApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnApagarActionPerformed(evt);
            }
        });
    }

    private void btnApagarActionPerformed(ActionEvent evt) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        delete(Integer.parseInt(txtId.getText()));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void txtUserKeyTyped(KeyEvent evt) {
        if (txtUser.getText().length() > 19) {
            txtUser.setHelperText("O nome de usuário não pode conter mais que 20 caracteres");
        } else {
            txtUser.setHelperText("");
        }
    }

    private void txtPassKeyTyped(KeyEvent evt) {
        if (txtPassword.getText().length() > 14) {
            txtPassword.setHelperText("A senha não pode conter mais que 15 caracteres");
        } else {
            txtPassword.setHelperText("");
        }
    }

    private void setValues() {
        cBoxProv.setSelectedIndex(user.getProvincia());
        dataNascimento.setText(user.getDataNascimeto());
        button1.setText("Trocar a foto");
        txtBI.setText(user.getBi());
        txtNome.setText(user.getName());
        txtPassword.setText(user.getPassword());
        txtUser.setText(user.getUser());
        cBoxTipo.setSelectedIndex(user.getTipo());
        txtId.setText(Integer.toString(user.getId()));
        btnApagar.setForeground(btnNovo.getBackground());
        btnEditar.setForeground(btnNovo.getBackground());
        btnApagar.setEnabled(true);
        btnEditar.setEnabled(true);
        logo.setImage(new ImageIcon(user.getFoto()));
        btnSalvar.setEnabled(false);
        btnEditar.setForeground(btnSalvar.getForeground());
        btnEditar.setBackground(btnSalvar.getBackground());
        btnApagar.setForeground(btnSalvar.getForeground());
        btnApagar.setBackground(btnSalvar.getBackground());
        btnApagar.setEnabled(true);
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setBackground(new java.awt.Color(204, 204, 204));
        btnEditar.setEnabled(true);
    }

    private void limparCampos() {
        button1.setText("Selecione uma foto");
        txtBI.setText("");
        txtId.setText("");
        txtNome.setText("");
        txtPassword.setText("");
        txtUser.setText("");
        cBoxTipo.setSelectedIndex(1);
        btnSalvar.setForeground(btnNovo.getForeground());
        btnSalvar.setBackground(btnNovo.getBackground());
        btnSalvar.setEnabled(true);
        btnApagar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setBackground(new java.awt.Color(204, 204, 204));
        btnApagar.setForeground(new java.awt.Color(255, 255, 255));
        btnApagar.setBackground(new java.awt.Color(204, 204, 204));
        file = null;
        logo.setImage(new ImageIcon(".//src//com//icons//user.png"));
        this.revalidate();
        this.repaint();
    }

    public void setTitle(String text) {
        lblTitle.setText(text);
    }

    private void save() {

        load.Loading load = new Loading(frame, true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                ModelUser luser = new ModelUser();
                ControllerUsuario cUser = new ControllerUsuario();
                luser.setName(txtNome.getText());
                luser.setPassword(txtPassword.getText());
                luser.setUser(txtUser.getText());
                luser.setBi(txtBI.getText());
                luser.setDataNascimeto(dataNascimento.getText());
                luser.setTipo(cBoxTipo.getSelectedIndex());
                int save = cUser.save(frame, luser, file);

                load.dispose();

                if (save > 0) {
                    MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                    message.showMessage("Sucesso", "Usuário salvo com sucesso");
                    limparCampos();
                } else if (save == 0) {
                    MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                    message.showMessage("Aviso", "Usuário não salvo");
                }

            }
        }).start();
        load.setVisible(true);

    }

    private void selectPic() {
        JFileChooser fChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Somente arquivos(png, jpeg, jpg)", "png", "jpeg", "jpg");
        fChooser.setAcceptAllFileFilterUsed(false);
        fChooser.setFileFilter(filter);
        int confirm = fChooser.showDialog(null, "Selecionar");
        if (confirm == JFileChooser.APPROVE_OPTION) {
            file = fChooser.getSelectedFile();
            logo.setImage(new ImageIcon(file.getPath()));
        }
    }

    public void txtBIKeyTyped(KeyEvent evt) {
        if (txtBI.getText().length() == 14) {
            txtBI.setHelperText("Apage para inserir outro caracter");
            evt.consume();
            return;
        }
        if ((txtBI.getText().length() <= 8 || txtBI.getText().length() > 10)
                && (!"0987654321".contains(evt.getKeyChar() + ""))) {
            txtBI.setHelperText("Digite apenas números");
            evt.consume();
            return;
        }
        if ((txtBI.getText().length() >= 9 && txtBI.getText().length() < 11)
                && (!"MNBVCXZASDFGHJKLPOIUYTREWQ".contains(evt.getKeyChar() + ""))) {
            txtBI.setHelperText("Digite somente letras maísculas");
            evt.consume();
            return;
        }
        txtBI.setHelperText("");
    }

    private boolean validarCampos() {
        txtBI.setHelperText("");
        txtNome.setHelperText("");
        txtPassword.setHelperText("");
        txtUser.setHelperText("");
        if (txtBI.getText().length() < 14) {
            txtBI.setHelperText("Digite um número de B.I. válido");
            return false;
        }
        if (txtNome.getText().length() < 10) {
            txtNome.setHelperText("Digite um nome com pelo menos 10 caracteres");
            return false;
        }
        if (txtUser.getText().length() < 5) {
            txtUser.setHelperText("Digite um nome de usuário com pelo menos 5 caracteres");
            return false;
        }
        if (txtPassword.getText().length() < 4) {
            txtPassword.setHelperText("Digite uma senha com pelo menos 5 caracteres");
            return false;
        }

        if (file == null || logo.getImage()== new ImageIcon(".//src//com//icons//user.png")) {
            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
            message.showMessage("Aviso!", "Selecione uma foto");
            return false;
        } else {

            return true;
        }
    }

    private void delete(int id) {
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("Editar", "Deseja apagar os dados do usuario");
        if (message.getMessageType() == MessageDialog.MessageType.OK) {

            load.Loading load = new Loading(frame, true);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    int delete = new ControllerUsuario().delete(frame, id);
                    load.dispose();
                    if (delete > 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("Sucesso!", "Dados do usuario apagados");
                        limparCampos();
                    } else if (delete == 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("Aviso", "Dados não apagados! Tente novamente");
                    }

                }
            }).start();
            load.setVisible(true);

        }
    }

    private void edit() {
        if (!validarCampos()) {
            return;
        }
        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("Editar", "Deseja alterar os dados do usuario");
        if (message.getMessageType() == MessageDialog.MessageType.OK) {

            load.Loading load = new Loading(frame, true);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    setCursor(new Cursor(Cursor.WAIT_CURSOR));
                    ModelUser luser = new ModelUser();
                    ControllerUsuario cUser = new ControllerUsuario();
                    luser.setBi(txtBI.getText());
                    luser.setId(Integer.parseInt(txtId.getText()));
                    luser.setName(txtNome.getText());
                    luser.setPassword(txtPassword.getText());
                    luser.setTipo(cBoxTipo.getSelectedIndex());
                    luser.setDataNascimeto(dataNascimento.getText());
                    luser.setUser(txtUser.getText());
                    if (file == null) {
                        luser.setFoto(user.getFoto());
                    } else {
                        try {
                            luser.setFoto(FileUtils.readFileToByteArray(file));
                        } catch (IOException ex) {
                            MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                            message.showMessage("ERRO!", "Imagem não encontrada!");
                            load.dispose();
                            return;
                        }
                    }
                    int edit = cUser.edit(frame, luser);
                    load.dispose();
                    if (edit > 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("Sucesso", "Dados alterados com sucesso");
                    } else if (edit == 0) {
                        MessageDialog message = new MessageDialog(frame, MessageDialog.Type.INFO);
                        message.showMessage("Aviso", "Dados não alterado! Tente novamente");
                    }
                    setCursor(new Cursor(Cursor.WAIT_CURSOR));

                }
            }).start();
            load.setVisible(true);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelNorth = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        button1 = new  Button();
        logo = new  ImageAvatar();
        panelSouth = new javax.swing.JPanel();
        btnNovo = new  Button();
        btnSalvar = new  Button();
        btnEditar = new  Button();
        btnApagar = new  Button();
        materialTabbed1 = new  MaterialTabbed();
        panelCenter = new javax.swing.JPanel();
        txtId = new  TextField();
        txtNome = new  TextField();
        txtBI = new  TextField();
        txtUser = new  TextField();
        txtPassword = new  TextField();
        cBoxTipo = new  Combobox();

        setBackground(new java.awt.Color(254, 254, 254));
        setLayout(new java.awt.BorderLayout());

        panelNorth.setOpaque(false);
        panelNorth.setPreferredSize(new java.awt.Dimension(100, 150));
        panelNorth.setLayout(new java.awt.BorderLayout());

        lblTitle.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(126, 210, 244));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setPreferredSize(new java.awt.Dimension(243, 79));
        panelNorth.add(lblTitle, java.awt.BorderLayout.CENTER);

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 100));
        jPanel1.setLayout(new java.awt.BorderLayout());

        button1.setBackground(new java.awt.Color(0, 175, 255));
        button1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button1.setText("Selecione uma foto");
        button1.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        jPanel1.add(button1, java.awt.BorderLayout.PAGE_END);
        jPanel1.add(logo, java.awt.BorderLayout.CENTER);

        panelNorth.add(jPanel1, java.awt.BorderLayout.LINE_END);

        add(panelNorth, java.awt.BorderLayout.PAGE_START);

        panelSouth.setOpaque(false);
        panelSouth.setPreferredSize(new java.awt.Dimension(593, 60));
        java.awt.FlowLayout flowLayout3 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5);
        flowLayout3.setAlignOnBaseline(true);
        panelSouth.setLayout(flowLayout3);

        btnNovo.setBackground(new java.awt.Color(25, 182, 247));
        btnNovo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnNovo.setForeground(new java.awt.Color(255, 255, 255));
        btnNovo.setText("Novo");
        btnNovo.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnNovo.setOpaque(true);
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        panelSouth.add(btnNovo);

        btnSalvar.setBackground(new java.awt.Color(25, 182, 247));
        btnSalvar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSalvar.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvar.setText("Salvar");
        btnSalvar.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnSalvar.setOpaque(true);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        panelSouth.add(btnSalvar);

        btnEditar.setBackground(new java.awt.Color(204, 204, 204));
        btnEditar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setEnabled(false);
        btnEditar.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnEditar.setOpaque(true);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        panelSouth.add(btnEditar);

        btnApagar.setBackground(new java.awt.Color(204, 204, 204));
        btnApagar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnApagar.setForeground(new java.awt.Color(255, 255, 255));
        btnApagar.setText("Apagar");
        btnApagar.setEnabled(false);
        btnApagar.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnApagar.setOpaque(true);
        panelSouth.add(btnApagar);

        add(panelSouth, java.awt.BorderLayout.PAGE_END);

        panelCenter.setOpaque(false);
        panelCenter.setPreferredSize(new java.awt.Dimension(6, 300));
        panelCenter.setLayout(new java.awt.GridLayout(4, 20, 10, 5));

        txtId.setEditable(false);
        txtId.setLabelText("ID");
        panelCenter.add(txtId);

        txtNome.setLabelText("Nome Completo");
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeKeyTyped(evt);
            }
        });
        panelCenter.add(txtNome);

        txtBI.setLabelText("B.I.");
        panelCenter.add(txtBI);

        txtUser.setLabelText("Usuário");
        panelCenter.add(txtUser);

        txtPassword.setLabelText("Senha");
        panelCenter.add(txtPassword);

        cBoxTipo.setLabeText("Tipo");

        cBoxProv = new Combobox();
        cBoxProv.setLabeText("Província");
        cBoxProv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                cBoxProvActionPerformed(evt);
            }
        });

        cBoxMun = new Combobox();
        cBoxMun.setLabeText("Município");

        cBoxGenero = new Combobox();
        cBoxGenero.setLabeText("Gênero");
        cBoxGenero.addItem("Masculino");
        cBoxGenero.addItem("Femenino");

        dataNascimento = new  TextField();
        dataNascimento.setLabelText("Data de Nascimento");
        dataNascimento.setEditable(false);

        dataNascimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dataNascimentoActionPerdormed(evt);
            }
        });

        panelCenter.add(dataNascimento);
        panelCenter.add(cBoxGenero);
        panelCenter.add(cBoxProv);
        panelCenter.add(cBoxMun);
        panelCenter.add(cBoxTipo);

        materialTabbed1.addTab("Dados", panelCenter);

        add(materialTabbed1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>                        

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {
        if (!"zxcvbnmlkjhgfdsaqwertyuiop ´^~`ç MNBVCXZASDFGHJKLPOIUYTREWQéÉÀàáÁíÍìÌóÓòÒúÚùÙãÃõÕâÂêÊ".contains(evt.getKeyChar() + "")) {
            txtNome.setHelperText("Caracter inválido");
            evt.consume();
            return;
        }
        if (txtNome.getText().length() > 100) {
            txtNome.setHelperText("O nome não pode conter mais do que 100 caracters");
            evt.consume();
            return;
        }
        txtNome.setHelperText("");
    }

    private void cBoxProvActionPerformed(ActionEvent evt) {
        changueMunin();
    }

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        if (!validarCampos()) {
            return;
        }

        message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.QUESTION);
        message.showMessage("Salvar", "Deseja salvar os dados");
        if (message.getMessageType() == MessageDialog.MessageType.OK) {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            save();
            setCursor(new Cursor(Cursor.WAIT_CURSOR));

        }
    }

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {
        selectPic();
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {

        edit();
    }

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {
        limparCampos();
    }

    private void dataNascimentoActionPerdormed(ActionEvent evt) {
        dataChooser.showPopup();
    }

    // Variables declaration - do not modify                     
    private Button btnApagar;
    private  Button btnEditar;
    private  Button btnNovo;
    private  Button btnSalvar;
    private  Button button1;
    private  Combobox cBoxTipo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitle;
    private  ImageAvatar logo;
    private  MaterialTabbed materialTabbed1;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelNorth;
    private javax.swing.JPanel panelSouth;
    private  TextField txtBI;
    private  TextField txtId;
    private  TextField txtNome;
    private  TextField txtPassword;
    private  TextField txtUser;
    private  Combobox cBoxProv;
    private  Combobox cBoxMun;
    private  Combobox cBoxGenero;
    private  TextField dataNascimento;
    // End of variables declaration                   
}
