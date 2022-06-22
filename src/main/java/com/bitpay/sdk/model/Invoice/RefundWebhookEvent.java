package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundWebhookEvent {

	private Double _code;
	private String _name;

    public RefundWebhookEvent() {
    }

    @JsonProperty("code")
    public Double getCode() {
        return _code;
    }

    @JsonProperty("code")
    public void setCode(Double code) {
        this._code = _code;
    }
    
    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getName() {
        return _name;
    }

    @JsonProperty("name")
    public void setName(String _name) {
        this._name = _name;
    }
}
