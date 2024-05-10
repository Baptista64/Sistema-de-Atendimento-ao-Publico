/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAOConfiguracao;
import javax.swing.JFrame;

/**
 *
 * @author baptista
 */
public class ControllerConfiguracoes {

    private final DAOConfiguracao config = new DAOConfiguracao();

    public int chamando_cliente(JFrame frame) {
        return this.config.chamando_cliente(frame);
    }

    public String getSenha(JFrame frame) {
        return this.config.getSenha(frame);
    }
    public String getCaixa(JFrame frame) {
        return this.config.getCaixa(frame);
    }
}
