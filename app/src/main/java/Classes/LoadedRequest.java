package Classes;

import java.io.Serializable;

/**
 * Created by Dell on 07/09/2017.
 */

public class LoadedRequest implements Serializable{
    @Override
    public String toString() {
        return "LoadedRequest{" +
                "id=" + id +
                ", dataHoraSolicitacao='" + dataHoraSolicitacao + '\'' +
                ", tipo='" + tipo + '\'' +
                ", quantidade=" + quantidade +
                ", peso=" + peso +
                '}';
    }

    private int id;
    private String dataHoraSolicitacao;

    public String getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public String getTipo() {
        return tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPeso() {
        return peso;
    }

    public double getTamanho() {
        return tamanho;
    }

    public int getLatitude_Coleta() {
        return latitude_Coleta;
    }

    public int getLongitude_Coleta() {
        return longitude_Coleta;
    }

    public String getComplemento_Coleta() {
        return complemento_Coleta;
    }

    public int getNumero_Coleta() {
        return numero_Coleta;
    }

    public String getEnderecoGPS_Coleta() {
        return enderecoGPS_Coleta;
    }

    public int getLatitude_Entrega() {
        return latitude_Entrega;
    }

    public int getLongitude_Entrega() {
        return longitude_Entrega;
    }

    public String getComplemento_Entrega() {
        return complemento_Entrega;
    }

    public int getNumero_Entrega() {
        return numero_Entrega;
    }

    public String getEnderecoGPS_Entrega() {
        return enderecoGPS_Entrega;
    }

    //pacote
    private String tipo;
    private int quantidade;
    private double peso;
    private double tamanho;

    //localColeta
    private int latitude_Coleta;
    private int longitude_Coleta;
    private String complemento_Coleta;
    private int numero_Coleta;
    private String enderecoGPS_Coleta;

    //localEntrega
    private int latitude_Entrega;
    private int longitude_Entrega;
    private String complemento_Entrega;
    private int numero_Entrega;
    private String enderecoGPS_Entrega;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setDataHoraSolicitacao(String dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public void setLatitude_Coleta(int latitude_Coleta) {
        this.latitude_Coleta = latitude_Coleta;
    }

    public void setLongitude_Coleta(int longitude_Coleta) {
        this.longitude_Coleta = longitude_Coleta;
    }

    public void setComplemento_Coleta(String complemento_Coleta) {
        this.complemento_Coleta = complemento_Coleta;
    }

    public void setNumero_Coleta(int numero_Coleta) {
        this.numero_Coleta = numero_Coleta;
    }

    public void setEnderecoGPS_Coleta(String enderecoGPS_Coleta) {
        this.enderecoGPS_Coleta = enderecoGPS_Coleta;
    }

    public void setLatitude_Entrega(int latitude_Entrega) {
        this.latitude_Entrega = latitude_Entrega;
    }

    public void setLongitude_Entrega(int longitude_Entrega) {
        this.longitude_Entrega = longitude_Entrega;
    }

    public void setComplemento_Entrega(String complemento_Entrega) {
        this.complemento_Entrega = complemento_Entrega;
    }

    public void setNumero_Entrega(int numero_Entrega) {
        this.numero_Entrega = numero_Entrega;
    }

    public void setEnderecoGPS_Entrega(String enderecoGPS_Entrega) {
        this.enderecoGPS_Entrega = enderecoGPS_Entrega;
    }

    public LoadedRequest() {
        this.id = 0;
        this.dataHoraSolicitacao = "";
        this.tipo = "";
        this.quantidade = 0;
        this.peso = 0.0;
        this.tamanho = 0.0;
        this.latitude_Coleta = 0;
        this.longitude_Coleta = 0;
        this.complemento_Coleta = "";
        this.numero_Coleta = 0;
        this.enderecoGPS_Coleta = "";
        this.latitude_Entrega = 0;
        this.longitude_Entrega = 0;
        this.complemento_Entrega = "";
        this.numero_Entrega = 0;
        this.enderecoGPS_Entrega = "";

    }

}
