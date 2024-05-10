/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.DAO.DAOSenha;
import com.model.ModelSenha;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author tobi
 */
public class ControllerSenha {

    private final DAOSenha servico = new DAOSenha();

    public int save(JFrame frame, ModelSenha servico) {
        return this.servico.save(frame, servico);
    }

    public String select(JFrame frame, String data, int estado) {
        return this.servico.select(frame, data, estado);
    }

    public ArrayList<ModelSenha> getAllServicesInWait(JFrame frame,String data, boolean caixaPrioridade, int estado) {
        return this.servico.selectAllServicesInWait(frame, data, caixaPrioridade, estado);
    }

    public int setServiceConcluido(JFrame frame, int idUsuario, int idServico, String data) {
        return this.servico.setServiceConcluido(frame, idUsuario, idServico, data);
    }

    public int setCallServiceUser(JFrame frame, int user, int senha) {
        return this.servico.setCallServiceUser(frame, user, senha);
    }

    public int setServicoNaoConcluido(JFrame frame, int idServico) {
        return this.servico.setServicoNaoConcluido(frame, idServico);
    }

    public int callService(JFrame frame, int senha) {
        return this.servico.callService(frame, senha);
    }
}
