/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.DAO.DAOSenha;
import com.model.ModelSenha;
import java.util.ArrayList;
import com.view.Senhas;

/**
 *
 * @author tobi
 */
public class ControllerAtendimento {

    private final DAOSenha servico = new DAOSenha();

    public int save(Senhas frame, ModelSenha servico) {
        return this.servico.save(frame, servico);
    }

    public String select(Senhas frame, String nome, String data, int estado) {
        return this.servico.select(frame, nome, data, estado);
    }

    public ArrayList<ModelSenha> getAllServicesInWait(Senhas frame, String data, boolean caixaPrioridade, int estado) {
        return this.servico.selectAllServicesInWait(frame, data, caixaPrioridade, estado);
    }
}
