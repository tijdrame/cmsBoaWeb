package com.boa.web.response.cardhistory;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "get-card-history-response" })
public class OperationsResponse {

    @JsonProperty("get-card-history-response")
    private GetCardHistoryResponse getCardHistoryResponse;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("get-card-history-response")
    public GetCardHistoryResponse getGetCardHistoryResponse() {
        return getCardHistoryResponse;
    }

    @JsonProperty("get-card-history-response")
    public void setGetCardHistoryResponse(GetCardHistoryResponse getCardHistoryResponse) {
        this.getCardHistoryResponse = getCardHistoryResponse;
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