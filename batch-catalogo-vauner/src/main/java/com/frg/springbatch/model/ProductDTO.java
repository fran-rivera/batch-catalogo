
package com.frg.springbatch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resp",
    "detail"
})
public class ProductDTO {

    @JsonProperty("resp")
    private String resp;
    @JsonProperty("detail")
    private List<ProductDetail> productDetail = null;

    @JsonProperty("resp")
    public String getResp() {
        return resp;
    }

    @JsonProperty("resp")
    public void setResp(String resp) {
        this.resp = resp;
    }

    @JsonProperty("detail")
    public List<ProductDetail> getProductDetail() {
        return productDetail;
    }

    @JsonProperty("detail")
    public void setProductDetail(List<ProductDetail> productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "resp='" + resp + '\'' +
                ", productDetail=" + productDetail +
                '}';
    }
}
