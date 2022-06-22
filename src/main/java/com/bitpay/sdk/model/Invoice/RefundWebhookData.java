package com.bitpay.sdk.model.Invoice;

import java.util.Hashtable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

class RefundWebhookData{
	
	public RefundWebhookData() {
    }
	
	private String _id;
	private String _url;
	private String _posData;
	private String _status;
	private Double _price;
	private String _currency;
	private String _invoiceTime;
	private String _expirationTime;
	private String _currentTime;
	private String _exceptionStatus;
	private BuyerFields _buyerFields = new BuyerFields();
	private Double _amountPaid;
	private String _orderId;
	private String _transactionCurrency;
	private Hashtable<String, String> _paymentSubtotals;
    private Hashtable<String, String> _paymentTotals;
    private Hashtable<String, Hashtable<String, String>> _exchangeRates;
	
	@JsonProperty("id")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getId() {
        return _id;
    }

    @JsonProperty("id")
    public void setId(String _id) {
        this._id = _id;
    }
    
    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getUrl() {
        return _url;
    }

    @JsonProperty("url")
    public void setUrl(String _url) {
        this._url = _url;
    }
    
    
    @JsonProperty("posData")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getPosData() {
        return _posData;
    }

    @JsonProperty("posData")
    public void setPosData(String _posData) {
        this._posData = _posData;
    }
    
    
    @JsonProperty("status")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getStatus() {
        return _status;
    }

    @JsonProperty("status")
    public void setStatus(String _status) {
        this._status = _status;
    }
    
    @JsonProperty("price")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getprice() {
        return _price;
    }

    @JsonProperty("price")
    public void setprice(Double _price) {
        this._price = _price;
    }
    
    @JsonProperty("currency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCurrency() {
        return _currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String _currency) {
        this._currency = _currency;
    }
    
    @JsonProperty("invoiceTime")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getInvoiceTime() {
        return _invoiceTime;
    }

    @JsonProperty("invoiceTime")
    public void setInvoiceTime(String _invoiceTime) {
        this._invoiceTime = _invoiceTime;
    }
    
    @JsonProperty("expirationTime")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getExpirationTime() {
        return _expirationTime;
    }

    @JsonProperty("expirationTime")
    public void setExpirationTime(String _expirationTime) {
        this._expirationTime = _expirationTime;
    }
    
    @JsonProperty("currentTime")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getCurrentTime() {
        return _currentTime;
    }

    @JsonProperty("currentTime")
    public void setCurrentTime(String _currentTime) {
        this._currentTime = _currentTime;
    }
    
    @JsonProperty("exceptionStatus")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getExceptionStatus() {
        return _exceptionStatus;
    }

    @JsonProperty("exceptionStatus")
    public void setExceptionStatus(String _exceptionStatus) {
        this._exceptionStatus = _exceptionStatus;
    }
    
    @JsonProperty("buyerFields")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public BuyerFields getbuyerFields() {
        return _buyerFields;
    }

    @JsonProperty("buyerFields")
    public void setbuyerFields(BuyerFields _buyerFields) {
        this._buyerFields = _buyerFields;
    }
    
    @JsonProperty("orderId")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getOrderId() {
        return _orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String _orderId) {
        this._orderId = _orderId;
    }
    
    @JsonProperty("transactionCurrency")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public String getTransactionCurrency() {
        return _transactionCurrency;
    }

    @JsonProperty("transactionCurrency")
    public void setTransactionCurrency(String _transactionCurrency) {
        this._transactionCurrency = _transactionCurrency;
    }
    
    @JsonProperty("amountPaid")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public Double getAmountPaid() {
        return _amountPaid;
    }

    @JsonProperty("amountPaid")
    public void setAmountPaid(Double _amountPaid) {
        this._amountPaid = _amountPaid;
    }
    
    @JsonIgnore
    public Hashtable<String, Hashtable<String, String>> getExchangeRates() {
        return _exchangeRates;
    }

    @JsonProperty("exchangeRates")
    public void setExchangeRates(Hashtable<String, Hashtable<String, String>> _exchangeRates) {
        this._exchangeRates = _exchangeRates;
    }
    
    @JsonIgnore
    public Hashtable<String, String> getPaymentSubTotals() {
        return _paymentSubtotals;
    }

    @JsonProperty("paymentSubtotals")
    public void setPaymentSubTotals(Hashtable<String, String> _paymentSubtotals) {
        this._paymentSubtotals = _paymentSubtotals;
    }

    @JsonIgnore
    public Hashtable<String, String> getPaymentTotals() {
        return _paymentTotals;
    }

    @JsonProperty("paymentTotals")
    public void setPaymentTotals(Hashtable<String, String> _paymentTotals) {
        this._paymentTotals = _paymentTotals;
    }
	
}
