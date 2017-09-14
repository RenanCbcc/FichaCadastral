package Classes;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Dell on 27/07/2017.
 */

public class Deliveryman implements Serializable {
    private String nome;
    private Bitmap foto;
    private String contato;
    private String email;
    private String senha;
    private String telefone;
    private LoadedAddress loadedAddress;
    private String placa_Veiculo;
    private String marca_Veiculo;
    private String model_Veiculo;
    private int Id;
    private String titular_banco;
    private String banco;
    private String agencia;
    private String conta;
    private LatLng local;
    private String feed;
    private boolean isAvailable;
    private int nivel;
    private int experiencia;
    private float media;

    public String getModoConfimacao() {
        return modoConfimacao;
    }

    public void setModoConfimacao(String modoConfimacao) {
        this.modoConfimacao = modoConfimacao;
    }

    private String modoConfimacao;




    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;

    }


    public void setLocal(LatLng local) {
        this.local = local;
    }

    public Deliveryman(String nome, Bitmap foto, String contato, String email, String senha,
                       String telefone, LoadedAddress loadedAddress, String placa_Veiculo,
                       String marca_Veiculo, String model_Veiculo, String titular_banco,
                       String banco, String agencia, String conta, LatLng local, String feed,
                       boolean isAvailable, int nivel, int experiencia, float media) {

        this.nome = nome;
        this.foto = foto;
        this.contato = contato;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.loadedAddress = loadedAddress;
        this.placa_Veiculo = placa_Veiculo;
        this.marca_Veiculo = marca_Veiculo;
        this.model_Veiculo = model_Veiculo;
        this.titular_banco = titular_banco;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.local = local;
        this.feed = feed;
        this.isAvailable = isAvailable;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.media = media;
    }

    public LatLng getLocal() {

        return local;
    }


    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getFeed() {
        return feed;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public float getMedia() {
        return media;
    }




    public Deliveryman(){

        isAvailable = false;
        nivel = 0;
        experiencia = 0;
        media = (float)0;

    }
    public Deliveryman(String nome, Bitmap foto, String contato, String email, String senha,
                       LoadedAddress loadedAddress, String placa_Veiculo, String marca_Veiculo,
                       String model_Veiculo, String titular_banco, String banco, String agencia,
                       String conta) {
        this.nome = nome;
        this.foto = foto;
        this.contato = contato;
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

    public String getEmail() {
        return email;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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
        return "Deliveryman{" +
                "nome='" + nome + '\'' +
                ", contato='" + contato + '\'' +
                ", email='" + email + '\'' +
                ", banco='" + banco + '\'' +
                '}';
    }
}
