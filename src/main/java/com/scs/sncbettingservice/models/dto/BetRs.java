package com.scs.sncbettingservice.models.dto;

import com.scs.sncbettingservice.models.Bet;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BetRs {
    private String id;
    private BigDecimal balance;
    private Bet previousBet;
}
