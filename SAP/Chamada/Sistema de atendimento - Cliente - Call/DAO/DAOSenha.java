/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.ModelSenha;
import com.utils.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.view.Principal;
import message.MessageDialog;

/**
 *
 * @author tobi
 */
public class DAOSenha extends connection.Connect {

    public ArrayList<ModelSenha> selectAllServicesInWait(Principal frame) {
        String data = Utils.getFormatedDate();
        ArrayList<ModelSenha> servicos = new ArrayList<>();
        String sql = "SELECT * FROM senha "
                + "WHERE estado = ? "
                + "AND data LIKE ?"
                + " ORDER BY id;";
        try {
            String d[] = data.split("-");
            String d2[] = d[2].split(" ");
            data = d[0] + "-" + d[1] + "-" + d2[0] + "%";
            PreparedStatement pst = this.getConnection().prepareStatement(sql);
            pst.setInt(1, 0);
            pst.setString(2, data);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                ModelSenha serv = new ModelSenha();
                serv.setData(rst.getString("data"));
                serv.setEstado(rst.getInt("estado"));
                serv.setId(rst.getInt("id"));
                serv.setNome(rst.getString("nome"));
                serv.setUsuario(rst.getInt("usuario"));
                serv.setPrioridade(rst.getInt("prioridade"));
                servicos.add(serv);
            }
        } catch (SQLException e) {
            servicos = null;
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", e.getMessage());
        } catch (NullPointerException e) {
            message.MessageDialog message = new MessageDialog(frame, MessageDialog.Type.WARNING);
            message.showMessage("Erro!", "Erro de ligação");
            servicos = null;
        } finally {
            this.closeConnection();
        }
        return servicos;
    }
}
