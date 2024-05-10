/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.DAO.DAOAtendimentoFeito;
import com.model.ModelAtendimentoFeito;
import javax.swing.JFrame;

/**
 *
 * @author tobi
 */
public class ControllerAtendimentoFeito {

    private final DAOAtendimentoFeito atendimento = new DAOAtendimentoFeito();

    public int save(JFrame frame, ModelAtendimentoFeito atendimentoFeito) {
        return this.atendimento.save(frame, atendimentoFeito);
    }
}
