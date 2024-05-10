/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.DAO.DAOConfiguracao;
import com.model.ModelConfiguracao;
import javax.swing.JFrame;

/**
 *
 * @author baptista
 */
public class ControllerConfiguracoes {

    private final DAOConfiguracao config = new DAOConfiguracao();

    public int saveConfiguracao(JFrame frame, ModelConfiguracao config) {
        return this.config.saveConfigs(frame, config);
    }

    public ModelConfiguracao getConfig(JFrame frame) {
        return this.config.getConfiguracao(frame);
    }

    public int chamando_cliente(JFrame frame) {
        return this.config.chamando_cliente(frame);
    }

    public int chamarCliente(JFrame frame, String senha, String caixa, int isCalling) {
        return this.config.chamarCliente(frame, senha, caixa, isCalling);
    }
}
