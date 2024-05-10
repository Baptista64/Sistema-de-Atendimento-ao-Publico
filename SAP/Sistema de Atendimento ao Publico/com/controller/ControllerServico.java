/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.DAO.DAOServico;
import com.model.ModelServico;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author baptista
 */
public class ControllerServico {

    private final DAOServico servico = new DAOServico();

    public ArrayList<ModelServico> select(JFrame frame, String itemChoosen) {
        return this.servico.select(frame);
    }

    public int save(JFrame frame, ModelServico servico) {
        return this.servico.save(frame, servico);
    }

    public int edit(JFrame frame, ModelServico servico) {
        return this.servico.edit(frame, servico);
    }

    public int delete(JFrame frame, int id) {
        return this.servico.delete(frame, id);
    }

    public ModelServico selects(JFrame frame, String nome) {
        return this.servico.select(frame, nome);
    }
}
