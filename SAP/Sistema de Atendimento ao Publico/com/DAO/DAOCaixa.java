/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.model.ModelCaixa;
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
public class DAOCaixa extends connection.Connect {

    public int save(JFrame frame, ModelCaixa caixa) {
        int update;
        String sql = "INSERT INTO caixa (nome, prioridade) values (?, ?)";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, caixa.getNome());
            pst.setInt(2, caixa.getPrioridade());
            update = pst.executeUpdate();
        } catch (SQLException ex) {
            update = -1;
            if (ex.getMessage().contains("Duplicate entry") && ex.getMessage().contains("for key 'nome'")) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Já existe um serviço com este nome");
            } else {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("Erro!", ex.getMessage());
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

    public ArrayList<ModelCaixa> select(JFrame frame) {
        ArrayList<ModelCaixa> caixas = new ArrayList<>();
        String sql = "SELECT * FROM caixa ORDER BY nome";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                ModelCaixa caixa = new ModelCaixa();
                caixa.setId(rst.getInt("id"));
                caixa.setNome(rst.getString("nome"));
                caixa.setAberto(rst.getInt("aberto"));
                caixa.setPrioridade(rst.getInt("prioridade"));
                caixas.add(caixa);
            }
        } catch (SQLException ex) {
            caixas = null;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", ex.getMessage());
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            caixas = null;
        } finally {
            this.closeConnection();
        }
        return caixas;
    }

    public ModelCaixa select(JFrame frame, String nome) {
        ModelCaixa caixa;
        String sql = "SELECT * FROM caixa WHERE nome = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, nome);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                caixa = new ModelCaixa();
                caixa.setId(rst.getInt("id"));
                caixa.setNome(rst.getString("nome"));
                caixa.setPrioridade(rst.getInt("prioridade"));
            } else {
                caixa = null;
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("Aviso!", "Nenhum caixa com este nome foi encontrado");
            }
        } catch (SQLException ex) {
            caixa = null;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", ex.getMessage());
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            caixa = null;
        } finally {
            this.closeConnection();
        }
        return caixa;
    }

    public int delete(JFrame frame, int id) {
        int update;
        String sql = "DELETE FROM caixa"
                + " WHERE id = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
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

    public int edit(JFrame frame, ModelCaixa caixa) {
        int update;
        String sql = "UPDATE caixa "
                + "SET "
                + "nome = ?,"
                + "prioridade = ?"
                + " WHERE id = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, caixa.getNome());
            pst.setInt(2, caixa.getPrioridade());
            pst.setInt(3, caixa.getId());
            update = pst.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("for key 'nome'")) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("ERRO!", "Já existe um serviço com este nome");
            } else {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("Erro!", e.getMessage());
            }
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

    public int abrirFecharCaixa(JFrame frame, int abrir, int id) {

        int update;
        String sql = "UPDATE caixa "
                + "SET aberto = ?"
                + " WHERE id = ?";

        try {

            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, abrir);
            pst.setInt(2, id);

            update = pst.executeUpdate();

            if (update == 0) {
                if (abrir == 1) {
                    message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                    message.showMessage("Erro!", "Algo correu mal ao abrir o caixa");
                } else {
                    message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                    message.showMessage("Erro!", "Algo correu mal ao fechar o caixa");
                }
            }
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
