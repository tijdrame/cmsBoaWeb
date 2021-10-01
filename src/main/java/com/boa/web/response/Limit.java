package com.boa.web.response;

public class Limit {
    private Integer limitId;
    private Double amount;
    private Integer usedAmount;
    private String expiryDate;
    private String description;
    private Integer isActive;
    private Integer isChangeable;
    private String limitName;
    private String currency;
    private Integer isMoneyLimit;
    private String period;

    public Limit() {
    }

    public Limit(Integer limitId, Double amount, Integer usedAmount, String expiryDate, String description, Integer isActive, Integer isChangeable, String limitName, String currency, Integer isMoneyLimit, String period) {
        this.limitId = limitId;
        this.amount = amount;
        this.usedAmount = usedAmount;
        this.expiryDate = expiryDate;
        this.description = description;
        this.isActive = isActive;
        this.isChangeable = isChangeable;
        this.limitName = limitName;
        this.currency = currency;
        this.isMoneyLimit = isMoneyLimit;
        this.period = period;
    }

    public Integer getLimitId() {
        return this.limitId;
    }

    public void setLimitId(Integer limitId) {
        this.limitId = limitId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getUsedAmount() {
        return this.usedAmount;
    }

    public void setUsedAmount(Integer usedAmount) {
        this.usedAmount = usedAmount;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsChangeable() {
        return this.isChangeable;
    }

    public void setIsChangeable(Integer isChangeable) {
        this.isChangeable = isChangeable;
    }

    public String getLimitName() {
        return this.limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getIsMoneyLimit() {
        return this.isMoneyLimit;
    }

    public void setIsMoneyLimit(Integer isMoneyLimit) {
        this.isMoneyLimit = isMoneyLimit;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Limit limitId(Integer limitId) {
        setLimitId(limitId);
        return this;
    }

    public Limit amount(Double amount) {
        setAmount(amount);
        return this;
    }

    public Limit usedAmount(Integer usedAmount) {
        setUsedAmount(usedAmount);
        return this;
    }

    public Limit expiryDate(String expiryDate) {
        setExpiryDate(expiryDate);
        return this;
    }

    public Limit description(String description) {
        setDescription(description);
        return this;
    }

    public Limit isActive(Integer isActive) {
        setIsActive(isActive);
        return this;
    }

    public Limit isChangeable(Integer isChangeable) {
        setIsChangeable(isChangeable);
        return this;
    }

    public Limit limitName(String limitName) {
        setLimitName(limitName);
        return this;
    }

    public Limit currency(String currency) {
        setCurrency(currency);
        return this;
    }

    public Limit isMoneyLimit(Integer isMoneyLimit) {
        setIsMoneyLimit(isMoneyLimit);
        return this;
    }

    public Limit period(String period) {
        setPeriod(period);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " limitId='" + getLimitId() + "'" +
            ", amount='" + getAmount() + "'" +
            ", usedAmount='" + getUsedAmount() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isChangeable='" + getIsChangeable() + "'" +
            ", limitName='" + getLimitName() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", isMoneyLimit='" + getIsMoneyLimit() + "'" +
            ", period='" + getPeriod() + "'" +
            "}";
    }

}
