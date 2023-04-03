package com.scs.sncbettingservice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document
public class Bet {
    private String id;
    private BigDecimal betMoney;
    private String userId;
    private Instant createdDate;

    private BigDecimal betResult;

    public String getId() {
        return id;
    }

    public Bet setId(String id) {
        this.id = id;
        return this;
    }

    public BigDecimal getBetMoney() {
        return betMoney;
    }

    public Bet setBetMoney(BigDecimal betMoney) {
        this.betMoney = betMoney;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Bet setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Bet setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public BigDecimal getBetResult() {
        return betResult;
    }

    public Bet setBetResult(BigDecimal betResult) {
        this.betResult = betResult;
        return this;
    }
}
