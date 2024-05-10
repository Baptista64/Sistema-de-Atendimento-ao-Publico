/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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

    public String getSenha(JFrame frame) {

        String senha = "";

        try {
            String sql = "SELECT senha FROM configuracoes";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {

                senha = rst.getString("senha");
            } else {
                senha = "";
            }
        } catch (SQLException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
            java.util.logging.Logger.getLogger(DAOSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
        } finally {
            this.closeConnection();
        }
        return senha;
    }

    public String getCaixa(JFrame frame) {

        String senha = "";

        try {
            String sql = "SELECT caixa FROM configuracoes";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {

                senha = rst.getString("caixa");
            } else {
                senha = "";
            }
        } catch (SQLException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", e.getMessage());
            java.util.logging.Logger.getLogger(DAOSenha.class.getName()).log(java.util.logging.Level.SEVERE, null, e);

        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
        } finally {
            this.closeConnection();
        }
        return senha;
    }
}
