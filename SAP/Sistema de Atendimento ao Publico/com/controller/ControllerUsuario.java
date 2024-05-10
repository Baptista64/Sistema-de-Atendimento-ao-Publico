package com.controller;

import com.DAO.DAOUsuario;
import com.model.ModelUser;
import java.io.File;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author baptista
 */
public class ControllerUsuario {

    private final DAOUsuario user = new DAOUsuario();

    public ArrayList<ModelUser> getAllUser(JFrame frame){
        return this.user.getAllUser(frame);
    }
    
   public int delete(JFrame frame, int id){
       return this.user.delete(frame, id);
   }
    
    public ModelUser getUser(JFrame frame, String user, String password) {
        return this.user.getUser(frame, user, password);
    }

    public ModelUser getUser(JFrame frame, int id) {
        return this.user.getUser(frame, id);
    }

    public TableModel getUser(JFrame frame, String nome) {
        return this.user.getUser(frame, nome);
    }

    public int save(JFrame frame, ModelUser user, File file) {
        return this.user.save(frame, user, file);
    }

    public int edit(JFrame frame, ModelUser user) {
        return this.user.edit(frame, user);
    }
}
