package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyerFields {

	private String _buyerName;
	private String _buyerAddress1;
	private String _buyerAddress2;
	private String _buyerCity;
	private String _buyerState;
	private String _buyerZip;
	private String _buyerCountry;
	private String _buyerPhone;
	private String _buyerNotify;
	private String _buyerEmail;


    public BuyerFields() {
    }

    @JsonProperty("buyerName")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerName() {
        return _buyerName;
    }

    @JsonProperty("buyerName")
    public void setBuyerName(String _buyerName) {
        this._buyerName = _buyerName;
    }
    
    @JsonProperty("buyerAddress1")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerAddress1() {
        return _buyerAddress1;
    }

    @JsonProperty("buyerAddress1")
    public void setBuyerAddress1(String _buyerAddress1) {
        this._buyerAddress1 = _buyerAddress1;
    }
    
    @JsonProperty("buyerAddress2")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerAddress2() {
        return _buyerAddress2;
    }

    @JsonProperty("buyerAddress2")
    public void setBuyerAddress2(String _name) {
        this._buyerAddress2 = _buyerAddress2;
    }
    
    @JsonProperty("buyerCity")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerCity() {
        return _buyerCity;
    }

    @JsonProperty("buyerCity")
    public void setBuyerCity(String _buyerCity) {
        this._buyerCity = _buyerCity;
    }
    
    @JsonProperty("buyerState")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerState() {
        return _buyerState;
    }

    @JsonProperty("buyerState")
    public void setBuyerState(String _buyerState) {
        this._buyerState = _buyerState;
    }
    
    @JsonProperty("buyerZip")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerZip() {
        return _buyerZip;
    }

    @JsonProperty("buyerZip")
    public void setBuyerZip(String _buyerZip) {
        this._buyerZip = _buyerZip;
    }
    
    @JsonProperty("buyerCountry")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerCountry() {
        return _buyerCountry;
    }

    @JsonProperty("buyerCountry")
    public void setBuyerCountry(String _buyerCountry) {
        this._buyerCountry = _buyerCountry;
    }
    
    @JsonProperty("buyerPhone")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerPhone() {
        return _buyerPhone;
    }

    @JsonProperty("buyerPhone")
    public void setBuyerPhone(String _buyerPhone) {
        this._buyerPhone = _buyerPhone;
    }
    
    @JsonProperty("buyerNotify")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerNotify() {
        return _buyerNotify;
    }

    @JsonProperty("buyerNotify")
    public void setBuyerNotify(String _buyerNotify) {
        this._buyerNotify = _buyerNotify;
    }
    
    @JsonProperty("buyerEmail")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getBuyerEmail() {
        return _buyerEmail;
    }

    @JsonProperty("buyerEmail")
    public void setBuyerEmail(String buyerEmail) {
        this._buyerEmail = _buyerEmail;
    }
    
}