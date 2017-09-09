package Classes;

import java.io.Serializable;

/**
 * Created by Dell on 07/09/2017.
 */

public class LoadedRequest implements Serializable {

    private String id;
    private String dataHoraSolicitacao;

    //pacote
    private String tipo;
    private String quantidade;
    private String peso;
    private String tamanho;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoadedRequest(){};

    public LoadedRequest(String id, String dataHoraSolicitacao, String tipo, String quantidade,
                         String peso, String tamanho, String latitude_Coleta, String longitude_Coleta,
                         String complemento_Coleta, String numero_Coleta, String enderecoGPS_Coleta,
                         String latitude_Entrega, String longitude_Entrega, String complemento_Entrega,
                         String numero_Entrega, String enderecoGPS_Entrega) {
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

    //localColeta
    private String latitude_Coleta;
    private String longitude_Coleta;
    private String complemento_Coleta;
    private String numero_Coleta;
    private String enderecoGPS_Coleta;

    //localEntrega
    private String latitude_Entrega;
    private String longitude_Entrega;
    private String complemento_Entrega;
    private String numero_Entrega;
    private String enderecoGPS_Entrega;
}
