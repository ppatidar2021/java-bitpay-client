package com.bitpay.sdk.model.Invoice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MinerFeesItem {

    private BigDecimal _satoshisPerByte;
    private BigDecimal _totalFee;
    private double _fiatAmount;

    public MinerFeesItem() {
    }

    @JsonIgnore
    public BigDecimal getSatoshisPerByte() {
        return _satoshisPerByte;
    }

    @JsonProperty("satoshisPerByte")
    public void setSatoshisPerByte(BigDecimal satoshisPerByte) {
        this._satoshisPerByte = satoshisPerByte;
    }

    @JsonIgnore
    public BigDecimal getTotalFee() {
        return _totalFee;
    }

    @JsonProperty("totalFee")
    public void setTotalFee(BigDecimal totalFee) {
        this._totalFee = totalFee;
    }

    @JsonIgnore
    public double getFiatAmount() {
        return _fiatAmount;
    }

    @JsonProperty("fiatAmount")
    public void setFiatAmount(double fiatAmount) { this._fiatAmount = fiatAmount; }
}
