/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.model.ModelServico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import message.MessageDialog;

/**
 *
 * @author baptista
 */
public class DAOServico extends connection.Connect {

    public int save(JFrame frame, ModelServico servico) {

        int save;

        try {
            String sql = "INSERT INTO servico (nome, descricao) VALUES (?,?)";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, servico.getNome());
            pst.setString(2, servico.getDescricao());

            save = pst.executeUpdate();

        } catch (SQLException ex) {

            save = -1;
            if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("for key 'nome'")) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Já existe um serviço com este nome");
            } else {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", ex.getMessage());
            }
        } catch (NullPointerException ex) {

            save = -1;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
        } finally {
            this.closeConnection();
        }

        return save;

    }

    public ModelServico select(JFrame frame, String nome) {
        ModelServico serv;

        String sql = "SELECT * FROM servico WHERE nome = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, nome);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                serv = new ModelServico();
                serv.setId(rst.getInt("id"));
                serv.setNome(rst.getString("nome"));
                serv.setDescricao(rst.getString(""));
            } else {
                serv = null;
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("Aviso!", "Nenhum serviço com este nome foi encontrado");
            }
        } catch (SQLException ex) {

            serv = null;

            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", ex.getMessage());

        } catch (NullPointerException ex) {

            serv = null;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
        } finally {
            this.closeConnection();
        }
        return serv;
    }

    public ArrayList<ModelServico> select(JFrame frame) {

        ArrayList<ModelServico> serv = new ArrayList<>();
        String sql = "SELECT * FROM servico";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                ModelServico se = new ModelServico();
                se.setId(rst.getInt("id"));
                se.setNome(rst.getString("nome"));
                se.setDescricao(rst.getString("descricao"));
                serv.add(se);
            }
        } catch (SQLException ex) {

            serv = null;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", ex.getMessage());

        } catch (NullPointerException ex) {

            serv = null;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
        } finally {
            this.closeConnection();
        }
        return serv;
    }

    public int delete(JFrame frame, int id) {

        int update;

        String sql = "DELETE FROM servico "
                + "WHERE id = ?";

        try {

            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
            update = pst.executeUpdate();

        } catch (SQLException ex) {

            update = -1;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", ex.getMessage());

        } catch (NullPointerException ex) {

            update = -1;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
        } finally {
            this.closeConnection();
        }
        return update;
    }

    public int edit(JFrame frame, ModelServico servico) {

        int update;

        try {
            String sql = "UPDATE servico "
                    + "SET nome = ?,"
                    + "descricao = ?"
                    + "WHERE id = ?";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, servico.getNome());
            pst.setString(2, servico.getDescricao());
            pst.setInt(3, servico.getId());

            update = pst.executeUpdate();

        } catch (SQLException ex) {

            update = -1;
            if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("for key 'nome'")) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Já existe um serviço com este nome");
            } else {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", ex.getMessage());
            }

        } catch (NullPointerException ex) {

            update = -1;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("ERRO!", "Erro de ligação");
        } finally {
            this.closeConnection();
        }

        return update;
    }
}
