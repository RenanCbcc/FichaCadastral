package com.example.dell.fichacadastral;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Dell on 27/07/2017.
 */

public class Costumer implements Serializable {
    private String nome;
    private Bitmap foto;
    private String contato;
    private String cpf;
    private String cnpj;
    private String email;
    private String senha;
    private LoadedAddress loadedAddress;
    private String placa_Veiculo;
    private String marca_Veiculo;
    private String model_Veiculo;
    private String titular_banco;
    private String banco;
    private String agencia;
    private String conta;

    public Costumer(){}
    public Costumer(String nome, Bitmap foto, String contato, String cpf, String cnpj, String email, String senha,
                    LoadedAddress loadedAddress, String placa_Veiculo, String marca_Veiculo,
                    String model_Veiculo, String titular_banco, String banco, String agencia,
                    String conta) {
        this.nome = nome;
        this.foto = foto;
        this.contato = contato;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
        this.loadedAddress = loadedAddress;
        this.placa_Veiculo = placa_Veiculo;
        this.marca_Veiculo = marca_Veiculo;
        this.model_Veiculo = model_Veiculo;
        this.titular_banco = titular_banco;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public String getCNPJ() {
        return cnpj;
    }

    public void setCNPJ(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LoadedAddress getLoadedAddress() {
        return loadedAddress;
    }

    public void setLoadedAddress(LoadedAddress loadedAddress) {
        this.loadedAddress = loadedAddress;
    }

    public String getPlaca_Veiculo() {
        return placa_Veiculo;
    }

    public void setPlaca_Veiculo(String placa_Veiculo) {
        this.placa_Veiculo = placa_Veiculo;
    }

    public String getMarca_Veiculo() {
        return marca_Veiculo;
    }

    public void setMarca_Veiculo(String marca_Veiculo) {
        this.marca_Veiculo = marca_Veiculo;
    }

    public String getModel_Veiculo() {
        return model_Veiculo;
    }

    public void setModel_Veiculo(String model_Veiculo) {
        this.model_Veiculo = model_Veiculo;
    }

    public String getTitular_banco() {
        return titular_banco;
    }

    public void setTitular_banco(String titular_banco) {
        this.titular_banco = titular_banco;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "nome='" + nome + '\'' +
                ", contato='" + contato + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
