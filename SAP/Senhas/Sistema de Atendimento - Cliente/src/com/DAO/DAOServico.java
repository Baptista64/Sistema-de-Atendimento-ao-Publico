/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.model.ModelServico;
import com.view.Senhas;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import message.MessageDialog;

/**
 *
 * @author baptista
 */
public class DAOServico extends connection.Connect{
    public ArrayList<ModelServico> selectAllService(Senhas frame) {
        ArrayList<ModelServico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servico";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                ModelServico serv = new ModelServico();
                serv.setId(rst.getInt("id"));
                serv.setDescricao(rst.getString("descricao"));
                serv.setNome(rst.getString("nome"));
                servicos.add(serv);
            }
        } catch (SQLException e) {
            servicos = null;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", e.getMessage());
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            servicos = null;
        } finally {
            this.closeConnection();
        }
        return servicos;
    }
}
