/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.model.ModelConfiguracao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import message.MessageDialog;

/**
 *
 * @author baptista
 */
public class DAOConfiguracao extends connection.Connect {

    public ModelConfiguracao getConfiguracao(JFrame frame) {
        ModelConfiguracao config = new ModelConfiguracao();
        try {
            String sql = "SELECT * FROM configuracoes";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                config.setNome(rst.getString("nome"));
                config.setLogo(rst.getBytes("logo"));
            } else {
                config = null;
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "");
            }
        } catch (SQLException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
            config = null;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
            config = null;
        } finally {
            this.closeConnection();
        }
        return config;
    }

    public int chamando_cliente(JFrame frame) {
        int chamando = -1;
        try {
            String sql = "SELECT chamando_cliente FROM configuracoes";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                chamando = rst.getInt("chamando_cliente");
            }
        } catch (SQLException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
            chamando = -1;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
            chamando = -1;
        } finally {
            this.closeConnection();
        }
        return chamando;
    }

    public int saveConfigs(JFrame frame, ModelConfiguracao config) {
        int update;
        String sql = "UPDATE configuracoes "
                + "SET "
                + "nome = ?,"
                + "logo = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, config.getNome());
            pst.setBytes(2, config.getLogo());
            update = pst.executeUpdate();
        } catch (SQLException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
            update = -1;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
            update = -1;
        } finally {
            this.closeConnection();
        }
        return update;
    }

    public int chamarCliente(JFrame frame, String senha, String caixa, int isCalling) {
        int update;
        String sql = "UPDATE configuracoes "
                + "SET "
                + "chamando_cliente = ?, "
                + "senha = ?, "
                + "caixa = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, isCalling);
            pst.setString(2, senha);
            pst.setString(3, caixa);
            update = pst.executeUpdate();
        } catch (SQLException e) {
            update = -1;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
        } catch (NullPointerException e) {
            update = -1;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
        } finally {
            this.closeConnection();
        }
        return update;
    }

}
