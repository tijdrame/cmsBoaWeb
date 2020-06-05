package com.boa.web.response.cardsrequest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "identifier", "stereotype", "label", "hidden", "value" })
public class OutputStringField {

    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("stereotype")
    private String stereotype;

    @JsonProperty("label")
    private String label;
    @JsonProperty("hidden")
    private String hidden;
    @JsonProperty("value")
    private String value;

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("stereotype")
    public String getStereotype() {
        return stereotype;
    }

    @JsonProperty("stereotype")
    public void setStereotype(String stereotype) {
        this.stereotype = stereotype;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("hidden")
    public String getHidden() {
        return hidden;
    }

    @JsonProperty("hidden")
    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

}