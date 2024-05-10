/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author baptista
 */
public class ModelConfiguracao {
    
    private String nome, senha;
    private byte[] logo;
    private boolean chamando_cliente;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public boolean isChamando_cliente() {
        return chamando_cliente;
    }

    public void setChamando_cliente(boolean chamando_cliente) {
        this.chamando_cliente = chamando_cliente;
    }
    
    
}
