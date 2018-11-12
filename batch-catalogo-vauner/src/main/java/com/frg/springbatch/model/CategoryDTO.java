
package com.frg.springbatch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resp",
    "detailCategory"
})


/**
 * Response getCategories.
 *
 * @author Fran Rivera
 */
public class CategoryDTO {

    @JsonProperty("resp")
    private String resp;
    @JsonProperty("detail")
    private List<CategoryDetail> detailCategory = null;


    @JsonProperty("resp")
    public String getResp() {
        return resp;
    }

    @JsonProperty("resp")
    public void setResp(String resp) {
        this.resp = resp;
    }

    @JsonProperty("detailCategory")
    public List<CategoryDetail> getDetailCategory() {
        return detailCategory;
    }

    @JsonProperty("detailCategory")
    public void setDetailCategory(List<CategoryDetail> detailCategory) {
        this.detailCategory = detailCategory;
    }


}
