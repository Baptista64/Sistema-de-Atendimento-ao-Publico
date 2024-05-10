/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.DAO.DAOCaixa;
import com.model.ModelCaixa;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author baptista
 */
public class ControllerCaixa {

    private final DAOCaixa caixa = new DAOCaixa();

    public int abrirFecharCaixa(JFrame fram, int abrir, int id) {
        return this.caixa.abrirFecharCaixa(fram, abrir, id);
    }

    public int save(JFrame frame, ModelCaixa caixa) {
        return this.caixa.save(frame, caixa);
    }

    public ArrayList<ModelCaixa> select(JFrame frame) {
        return this.caixa.select(frame);
    }

    public ModelCaixa select(JFrame frame, String nome) {
        return this.caixa.select(frame, nome);
    }

    public int delete(JFrame frame, int id) {
        return this.caixa.delete(frame, id);
    }

    public int edit(JFrame frame, ModelCaixa caixa) {
        return this.caixa.edit(frame, caixa);
    }
}
