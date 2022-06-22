package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundWebhook {


	private RefundWebhookEvent _event = new RefundWebhookEvent();
	private RefundWebhookData _data = new RefundWebhookData();
	
    public RefundWebhook() {
    }
    
    @JsonIgnore
    public RefundWebhookEvent getEvent() {
        return _event;
    }

    @JsonProperty("event")
    public void setEvent(RefundWebhookEvent event) {
        this._event = event;
    }
    
    @JsonIgnore
    public RefundWebhookData getData() {
        return _data;
    }

    @JsonProperty("data")
    public void setData(RefundWebhookData data) {
        this._data = data;
    }
    
}
