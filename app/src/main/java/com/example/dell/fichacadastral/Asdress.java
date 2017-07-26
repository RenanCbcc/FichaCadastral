package com.example.dell.fichacadastral;

/**
 * Created by Dell on 23/07/2017.
 * class POJO
 * @see{@link https://pt.wikipedia.org/wiki/Plain_Old_Java_Objects}
 */

public class Asdress {
    public class Address {
        private String bairro;
        private String cep;
        private String logradouro;
        private String localidade;
        private String uf;
        private String complemento;

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getLocalidade() {
            return localidade;
        }

        public void setLocalidade(String localidade) {
            this.localidade = localidade;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public String getComplemento() {
            return complemento;
        }

        public void setComplemento(String complemento) {
            this.complemento = complemento;
        }
    }
}

