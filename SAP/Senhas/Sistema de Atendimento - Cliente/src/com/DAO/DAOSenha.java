/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.model.ModelSenha;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.view.Senhas;
import message.MessageDialog;

/**
 *
 * @author tobi
 */
public class DAOSenha extends connection.Connect {

    public int save(Senhas frame, ModelSenha servico) {
        int update;
        String sql = "INSERT INTO senha "
                + " (nome, estado, prioridade, data)"
                + "VALUES(?, ?, ?, ?) ";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setString(1, servico.getNome());
            pst.setInt(2, servico.getEstado());
            pst.setInt(3, servico.getPrioridade());
            pst.setString(4, servico.getData());
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

    public String select(Senhas frame, String name, String data, int estado) {
        String senha;
        String name1 = name + "%";
        String d[] = data.split("-");
        String d2[] = d[2].split(" ");
        data = d[0] + "-" + d[1] + "-" + d2[0] + "%";
        try {
            String sql = "SELECT MAX(id) as max FROM senha "
                    + "WHERE estado = ?"
                    + " AND data LIKE ?"
                    + " AND nome LIKE ?";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, estado);
            pst.setString(2, data);
            pst.setString(3, name1);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                int id = rst.getInt("max");
                sql = "SELECT nome FROM senha WHERE id = ?";
                pst = this.getConnection().prepareStatement(sql);
                pst.setInt(1, id);
                rst = pst.executeQuery();
                if (rst.next()) {
                    senha = rst.getString("nome");
                    String[] n = senha.split("-");
                    int numero = Integer.parseInt(n[1]);
                    numero++;
                    senha = n[0] + "-" + numero;
                } else {
                    senha = name + "-" + "1";
                }
            } else {
                senha = name + "-" + "1";
            }

        } catch (SQLException e) {
            senha = null;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", e.getMessage());
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            senha = null;
        } finally {
            this.closeConnection();
        }
        return senha;
    }

    public ArrayList<ModelSenha> selectAllServicesInWait(Senhas frame, String data, boolean caixaPrioridade, int estado) {
        ArrayList<ModelSenha> servicos = new ArrayList<>();
        String sql = "SELECT * FROM senha "
                + "WHERE estado = ? "
                + "AND data LIKE ?"
                + " ORDER BY prioridade";
        if (caixaPrioridade) {
            sql += " ASC";
        } else {
            sql += " DESC";
        }
        try {
            String d[] = data.split("-");
            String d2[] = d[2].split(" ");
            data = d[0] + "-" + d[1] + "-" + d2[0] + "%";
            System.out.println(data);
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, estado);
            pst.setString(2, data);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                ModelSenha serv = new ModelSenha();
                serv.setData(rst.getString("data"));
                serv.setEstado(rst.getInt("estado"));
                serv.setId(rst.getInt("id"));
                serv.setNome(rst.getString("nome"));
                serv.setPrioridade(rst.getInt("prioridade"));
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
