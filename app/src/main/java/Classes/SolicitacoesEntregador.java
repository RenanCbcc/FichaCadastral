package Classes;

/**
 * Created by Rodrigo Henrique on 14/09/2017.
 */

public class SolicitacoesEntregador {
    private String id = "";
    private String status = "";
    private String nomeSolicitante = "";

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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataPrevistaColeta() {
        return dataPrevistaColeta;
    }

    public void setDataPrevistaColeta(String dataPrevistaColeta) {
        this.dataPrevistaColeta = dataPrevistaColeta;
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

    public double getValorTaxaServico() {
        return valorTaxaServico;
    }

    public void setValorTaxaServico(double valorTaxaServico) {
        this.valorTaxaServico = valorTaxaServico;
    }

    public String getTokenConfirmacao() {
        return tokenConfirmacao;
    }

    public void setTokenConfirmacao(String tokenConfirmacao) {
        this.tokenConfirmacao = tokenConfirmacao;
    }

    private String sobrenomeSolicitante = "";
    private double valor = 0.0;
    private String dataPrevistaColeta = "";

    public String getDataPrevistaEntrega() {
        return dataPrevistaEntrega;
    }

    public void setDataPrevistaEntrega(String dataPrevistaEntrega) {
        this.dataPrevistaEntrega = dataPrevistaEntrega;
    }

    private String dataPrevistaEntrega = "";
    private String dataRealColeta = "";
    private String dataRealEntrega = "";
    private String reclamacao_id = "";
    private double valorTaxaServico = 0.0;
    private String tokenConfirmacao = "";

    public SolicitacoesEntregador(){}


    public SolicitacoesEntregador(String id, String status, String nomeSolicitante, String sobrenomeSolicitante, double valor, String dataPrevistaColeta, String dataPrevistaEntrega, String dataRealColeta, String dataRealEntrega, String reclamacao_id, double valorTaxaServico, String tokenConfirmacao) {
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
        this.valorTaxaServico = valorTaxaServico;
        this.tokenConfirmacao = tokenConfirmacao;
    }

    @Override
    public String toString() {
        return "Status= " + status + "\n" +
                "ID= " + id + "\n" +
                "Nome do Solicitante= " + nomeSolicitante + "\n" +
                "Sobrenome do Solicitante= " + sobrenomeSolicitante + "\n" +
                "Valor= " + valor + "\n" +
                "Data Prevista da Coleta= " + dataPrevistaColeta + "\n" +
                "Data Prevista da Entrega= " + dataPrevistaEntrega + "\n" +
                "Data Real da Coleta= " + dataRealColeta + "\n" +
                "Data Real da Entrega= " + dataRealEntrega + "\n" +
                "ID da Reclamacao (Caso houver)= " + reclamacao_id + "\n" +
                "Valor da Taxa de Servico= " + valorTaxaServico;
    }

}
