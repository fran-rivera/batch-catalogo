
package com.frg.springbatch.login;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "descricao",
    "CODIGO",
    "GRUPO"
})
public class CategoryDetail {

    @JsonProperty("descricao")
    private String descricao;
    @JsonProperty("CODIGO")
    private String codigo;
    @JsonProperty("GRUPO")
    private String grupo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("descricao")
    public String getDescricao() {
        return descricao;
    }

    @JsonProperty("descricao")
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @JsonProperty("CODIGO")
    public String getCODIGO() {
        return codigo;
    }

    @JsonProperty("CODIGO")
    public void setCODIGO(String cODIGO) {
        this.codigo = cODIGO;
    }

    @JsonProperty("GRUPO")
    public String getGRUPO() {
        return grupo;
    }

    @JsonProperty("GRUPO")
    public void setGRUPO(String gRUPO) {
        this.grupo = gRUPO;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
