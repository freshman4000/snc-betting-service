package com.scs.sncbettingservice.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class Bet {
    private String id;
    private BigDecimal betMoney;
    private String userId;
}
