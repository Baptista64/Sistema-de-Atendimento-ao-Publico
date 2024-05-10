/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAOSenha;
import Model.ModelSenha;
import java.util.ArrayList;
import com.view.Principal;

/**
 *
 * @author tobi
 */
public class ControllerSenha {

    private final DAOSenha servico = new DAOSenha();


    public ArrayList<ModelSenha> getAllServicesInWait(Principal frame) {
        return this.servico.selectAllServicesInWait(frame);
    }
}
