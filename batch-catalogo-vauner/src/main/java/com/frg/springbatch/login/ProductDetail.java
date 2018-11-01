
package com.frg.springbatch.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "cod_artigo",
    "descricao",
    "valor",
    "Linha",
    "Stock",
    "image"
})
public class ProductDetail {

    @JsonProperty("cod_artigo")
    private String codArtigo;
    @JsonProperty("descricao")
    private String descricao;
    @JsonProperty("valor")
    private String valor;
    @JsonProperty("Linha")
    private String linha;
    @JsonProperty("Stock")
    private String stock;
    @JsonProperty("image")
    private String image;

    @JsonProperty("cod_artigo")
    public String getCodArtigo() {
        return codArtigo;
    }

    @JsonProperty("cod_artigo")
    public void setCodArtigo(String codArtigo) {
        this.codArtigo = codArtigo;
    }

    @JsonProperty("descricao")
    public String getDescricao() {
        return descricao;
    }

    @JsonProperty("descricao")
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @JsonProperty("valor")
    public String getValor() {
        return valor;
    }

    @JsonProperty("valor")
    public void setValor(String valor) {
        this.valor = valor;
    }

    @JsonProperty("Linha")
    public String getLinha() {
        return linha;
    }

    @JsonProperty("Linha")
    public void setLinha(String linha) {
        this.linha = linha;
    }

    @JsonProperty("Stock")
    public String getStock() {
        return stock;
    }

    @JsonProperty("Stock")
    public void setStock(String stock) {
        this.stock = stock;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "codArtigo='" + codArtigo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", valor='" + valor + '\'' +
                ", linha='" + linha + '\'' +
                ", stock='" + stock + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
