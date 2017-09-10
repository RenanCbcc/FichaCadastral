package com.example.dell.fichacadastral;

/**
 * Created by Francisco on 10/09/2017.
 */

public class Solicitacoes_Entregador {
    private String id;
    private String status;
    private String nomeSolicitante;
    private String sobrenomeSolicitante;
    private String valor;
    private String dataPrevistaColeta;
    private String dataPrevistaEntrega;
    private String dataRealColeta;
    private String dataRealEntrega;
    private String reclamacao_id;

    public Solicitacoes_Entregador(){}


    public Solicitacoes_Entregador(String id, String status, String nomeSolicitante, String sobrenomeSolicitante, String valor, String dataPrevistaColeta, String dataPrevistaEntrega, String dataRealColeta, String dataRealEntrega, String reclamacao_id) {
        this.id = id;
        this.status = status;
        this.nomeSolicitante = nomeSolicitante;
        this.sobrenomeSolicitante = sobrenomeSolicitante;
        this.valor = valor;
        this.dataPrevistaColeta = dataPrevistaColeta;
        this.dataPrevistaEntrega = dataPrevistaEntrega;
        this.dataRealColeta = dataRealColeta;
        this.dataRealEntrega = dataRealEntrega;
        this.reclamacao_id = reclamacao_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getSobrenomeSolicitante() {
        return sobrenomeSolicitante;
    }

    public void setSobrenomeSolicitante(String sobrenomeSolicitante) {
        this.sobrenomeSolicitante = sobrenomeSolicitante;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDataPrevistaColeta() {
        return dataPrevistaColeta;
    }

    public void setDataPrevistaColeta(String dataPrevistaColeta) {
        this.dataPrevistaColeta = dataPrevistaColeta;
    }

    public String getDataPrevistaEntrega() {
        return dataPrevistaEntrega;
    }

    public void setDataPrevistaEntrega(String dataPrevistaEntrega) {
        this.dataPrevistaEntrega = dataPrevistaEntrega;
    }

    public String getDataRealColeta() {
        return dataRealColeta;
    }

    public void setDataRealColeta(String dataRealColeta) {
        this.dataRealColeta = dataRealColeta;
    }

    public String getDataRealEntrega() {
        return dataRealEntrega;
    }

    public void setDataRealEntrega(String dataRealEntrega) {
        this.dataRealEntrega = dataRealEntrega;
    }

    public String getReclamacao_id() {
        return reclamacao_id;
    }

    public void setReclamacao_id(String reclamacao_id) {
        this.reclamacao_id = reclamacao_id;
    }

    @Override
    public String toString() {
        return "Id='" + id ;
    }
}
