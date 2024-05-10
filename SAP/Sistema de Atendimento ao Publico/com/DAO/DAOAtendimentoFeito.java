/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.model.ModelAtendimentoFeito;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JFrame;
import message.MessageDialog;

/**
 *
 * @author tobi
 */
public class DAOAtendimentoFeito extends connection.Connect {

    public int save(JFrame frame, ModelAtendimentoFeito atendimento) {
        int update;
        String sql = "INSERT INTO usuario_completa_atendimento"
                + " (id_usuario, id_senha, data)"
                + " VALUES(?, ?, ?)";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, atendimento.getIdUsuario());
            pst.setInt(2, atendimento.getIdServico());
            pst.setString(3, atendimento.getData());
            update = pst.executeUpdate();
        } catch (SQLException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", e.getMessage());
            update = -1;
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
