/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.DAO.DAOServico;
import com.model.ModelServico;
import com.view.Senhas;
import java.util.ArrayList;

/**
 *
 * @author baptista
 */
public class ControllerServico {

    private final DAOServico servico = new DAOServico();

    public ArrayList<ModelServico> selectAllServices(Senhas frame) {
        return this.servico.selectAllService(frame);
    }
}
