/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.controller.ControllerAtendimentoFeito;
import com.model.ModelAtendimentoFeito;
import com.model.ModelSenha;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import message.MessageDialog;

/**
 *
 * @author tobi
 */
public class DAOSenha extends connection.Connect {

    public int save(JFrame frame, ModelSenha servico) {
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

    public String select(JFrame frame, String data, int estado) {
        String senha;
        String d[] = data.split("-");
        String d2[] = d[2].split(" ");
        data = d[0] + "-" + d[1] + "-" + d2[0] + "%";
        try {
            String sql = "SELECT MAX(id) as max FROM atendimento "
                    + "WHERE estado = ?"
                    + " AND data LIKE ?";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, estado);
            pst.setString(2, data);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                int id = rst.getInt("max");
                sql = "SELECT nome FROM senha WHERE id = ?";
                pst = this.getConnection().prepareStatement(sql);
                pst.setInt(1, id);
                rst = pst.executeQuery();
                if (rst.next()) {
                    senha = rst.getString("nome");
                } else {
                    senha = "non";
                }
            } else {
                senha = "non";
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

    public ArrayList<ModelSenha> selectAllServicesInWait(JFrame frame, String data, boolean caixaPrioridade, int estado) {
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
                serv.setUsuario(rst.getInt("usuario"));
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

    public int setServiceConcluido(JFrame frame, int idUsuario, int idServico, String data) {
        int update;
        ResultSet rst;
        ModelAtendimentoFeito atendimentoFeito = new ModelAtendimentoFeito();
        atendimentoFeito.setData(data);
        atendimentoFeito.setIdServico(idServico);
        atendimentoFeito.setIdUsuario(idUsuario);
        try {
            String sql = "UPDATE senha SET "
                    + "estado = ?"
                    + " WHERE id = ?";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, 1);
            pst.setInt(2, idServico);
            update = pst.executeUpdate();
            if (update > 0) {
                rst = pst.getResultSet();
                ControllerAtendimentoFeito controllerAtendimentoFeito = new ControllerAtendimentoFeito();
                update = controllerAtendimentoFeito.save(frame, atendimentoFeito);
                if (update < 1) {
                    rst.cancelRowUpdates();
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

    public int setServicoNaoConcluido(JFrame frame, int idServico) {
        int update;
        try {
            String sql = "UPDATE senha SET "
                    + "estado = ?,"
                    + " usuario = ?"
                    + " WHERE id = ?";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, 2);
            pst.setInt(2, 0);
            pst.setInt(3, idServico);
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

    public int setCallServiceUser(JFrame frame, int user, int senha) {
        int update;
        String sql = "UPDATE senha "
                + "SET usuario = ?"
                + " WHERE id = ?";
        try {
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, user);
            pst.setInt(2, senha);
            update = pst.executeUpdate();
            if (update == 0) {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("Erro!", "Algo correu mal ao tentar chamar a senha");
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

    public int callService(JFrame frame, int senha) {

        int usu;

        String sql = "SELECT usuario FROM senha"
                + " WHERE id = ?";

        try {

            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, senha);
            ResultSet rst = pst.executeQuery();
            if (rst.next()) {
                usu = rst.getInt(1);
            } else {
                message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
                message.showMessage("Erro!", "Algo correu mal ao chamar a senha");
                usu = -2;
            }
        } catch (SQLException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", e.getMessage());
            usu = -1;
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            usu = -1;
        } finally {
            this.closeConnection();
        }
        return usu;
    }
}
