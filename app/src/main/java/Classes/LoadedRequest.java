package Classes;

import java.io.Serializable;

/**
 * Created by Dell on 07/09/2017.
 */

public class LoadedRequest implements Serializable {

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

    public String getLatitude_Coleta() {
        return latitude_Coleta;
    }

    public String getLongitude_Coleta() {
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

    public String getLatitude_Entrega() {
        return latitude_Entrega;
    }

    public String getLongitude_Entrega() {
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
    private String latitude_Coleta;
    private String longitude_Coleta;
    private String complemento_Coleta;
    private int numero_Coleta;
    private String enderecoGPS_Coleta;

    //localEntrega
    private String latitude_Entrega;
    private String longitude_Entrega;
    private String complemento_Entrega;
    private int     numero_Entrega;
    private String enderecoGPS_Entrega;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoadedRequest(){};

    public LoadedRequest(int id, String dataHoraSolicitacao, String tipo, int quantidade,
                         double peso, double tamanho, String latitude_Coleta, String longitude_Coleta,
                         String complemento_Coleta, int numero_Coleta, String enderecoGPS_Coleta,
                         String latitude_Entrega, String longitude_Entrega, String complemento_Entrega,
                         int numero_Entrega, String enderecoGPS_Entrega) {
        this.id = id;
        this.dataHoraSolicitacao = dataHoraSolicitacao;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.peso = peso;
        this.tamanho = tamanho;
        this.latitude_Coleta = latitude_Coleta;
        this.longitude_Coleta = longitude_Coleta;
        this.complemento_Coleta = complemento_Coleta;
        this.numero_Coleta = numero_Coleta;
        this.enderecoGPS_Coleta = enderecoGPS_Coleta;
        this.latitude_Entrega = latitude_Entrega;
        this.longitude_Entrega = longitude_Entrega;
        this.complemento_Entrega = complemento_Entrega;
        this.numero_Entrega = numero_Entrega;
        this.enderecoGPS_Entrega = enderecoGPS_Entrega;
    }

}
