package com.DAO;

import com.model.ModelUser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
import java.util.ArrayList;
import javax.swing.JFrame;
import message.MessageDialog;

/**
 *
 * @author baptista
 */
public class DAOUsuario extends connection.Connect {

    public ModelUser getUser(JFrame frame, String user, String password) {
        ModelUser us = new ModelUser();
        String sql = "SELECT * FROM usuario "
                + "WHERE usuario = ?"
                + " AND senha = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, user);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                us.setBi(rs.getString("bi"));
                us.setFoto(rs.getBytes("foto"));
                us.setId(rs.getInt("id"));
                us.setName(rs.getString("nome"));
                us.setPassword(rs.getString("senha"));
                us.setTipo(rs.getInt("tipo"));
                us.setUser(rs.getString("usuario"));
                us.setDataNascimeto(rs.getString("data_nascimento"));
                us.setGenero(rs.getInt("genero"));
                us.setProvincia(rs.getInt("provincia"));
                us.setMunicipio(rs.getInt("municipio"));

            }
        } catch (SQLException ex) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", ex.getMessage());
            us = null;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            us = null;
        } finally {
            this.closeConnection();
        }
        return us;
    }

    public ArrayList<ModelUser> getAllUser(JFrame frame) {

        ArrayList<ModelUser> musers = new ArrayList<>();
        String sql = "SELECT bi, nome FROM usuario ORDER BY nome";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ModelUser us = new ModelUser();
                us.setBi(rs.getString("bi"));
                us.setName(rs.getString("nome"));
                musers.add(us);
            }
        } catch (SQLException ex) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", ex.getMessage());
            musers = null;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            musers = null;
        } finally {
            this.closeConnection();
        }
        return musers;
    }

    public int delete(JFrame frame, int id) {
        int delete;
        String sql = "DELETE FROM usuario "
                + "WHERE id = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
            delete = pst.executeUpdate();
        } catch (SQLException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", e.getMessage());
            delete = -1;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            delete = -1;
        }
        return delete;
    }

    public ModelUser getUser(JFrame frame, int id) {
        ModelUser us = new ModelUser();
        String sql = "SELECT * FROM usuario "
                + "WHERE id = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                us.setBi(rs.getString("bi"));
                us.setFoto(rs.getBytes("foto"));
                us.setId(rs.getInt("id"));
                us.setName(rs.getString("nome"));
                us.setPassword(rs.getString("senha"));
                us.setTipo(rs.getInt("tipo"));
                us.setUser(rs.getString("usuario"));
                us.setDataNascimeto(rs.getString("data_nascimento"));
                us.setGenero(rs.getInt("genero"));
                us.setProvincia(rs.getInt("provincia"));
                us.setMunicipio(rs.getInt("municipio"));
            }
        } catch (SQLException ex) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", ex.getMessage());
            us = null;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            us = null;
        } finally {
            this.closeConnection();
        }
        return us;
    }

    public TableModel getUser(JFrame frame, String nome) {
        TableModel model = null;
        String sql = "SELECT id AS ID,"
                + " nome AS Nome,"
                + "bi AS 'B.I.'"
                + "  FROM usuario "
                + "WHERE nome LIKE ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, nome + "%");
            ResultSet rs = pst.executeQuery();
            model = DbUtils.resultSetToTableModel(rs);

        } catch (SQLException ex) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", ex.getMessage());
            model = null;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            model = null;
        } finally {
            this.closeConnection();
        }
        return model;
    }

    public int save(JFrame frame, ModelUser user, File file) {
        int update;
        String sql = "INSERT INTO usuario "
                + " (nome, usuario, senha, bi, tipo, foto, municipio, provincia, data_nascimento, genero) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getUser());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getBi());
            pst.setInt(5, user.getTipo());
            pst.setBinaryStream(6, Files.newInputStream(file.toPath()));
            pst.setInt(7, user.getMunicipio());
            pst.setInt(8, user.getProvincia());
            pst.setString(9, user.getDataNascimeto());
            pst.setInt(10, user.getGenero());
            update = pst.executeUpdate();
        } catch (SQLException e) {
            update = -1;
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'bi'")) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Já existe um funcionário com este Nº de B.I.");

            } else if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'usuario'")) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Já existe um funcionário com este usuário");

            } else {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("Erro!", e.getMessage());
            }
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            update = -1;
        } catch (IOException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Não foi possível localizar algum arquivo de imagem");
            update = -1;
        } finally {
            this.closeConnection();
        }
        return update;
    }

    public int edit(JFrame frame, ModelUser user) {
        int update;
        String sql = "UPDATE  usuario SET "
                + "nome = ?,"
                + "usuario = ?,"
                + "senha = ?,"
                + "bi = ?,"
                + "tipo = ?,"
                + "foto = ?,"
                + "municipio = ?,"
                + "provincia = ?,"
                + "data_nascimento = ?,"
                + "genero = ?"
                + " WHERE id = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getUser());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getBi());
            pst.setInt(5, user.getTipo());
            pst.setBytes(6, user.getFoto());
            pst.setInt(7, user.getMunicipio());
            pst.setInt(8, user.getProvincia());
            pst.setString(9, user.getDataNascimeto());
            pst.setInt(10, user.getGenero());
            pst.setInt(11, user.getId());
            update = pst.executeUpdate();
        } catch (SQLException e) {
            update = -1;
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'bi'")) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Já existe um funcionário com este Nº de B.I.");

            } else if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'usuario'")) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Já existe um funcionário com este usuário");

            } else {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("Erro!", e.getMessage());
            }
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            update = -1;
        } finally {
            this.closeConnection();
        }
        return update;
    }
}
