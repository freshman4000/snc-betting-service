package com.scs.sncbettingservice.services;

import com.scs.sncbettingservice.models.Bet;
import org.springframework.stereotype.Component;
import snc.sncmodels.services.betting.rq.BetRq;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

@Component
public class Mapper {

    public Bet mapBetRqToBet(BetRq initial, BetRq result) {
        return new Bet()
                .setCreatedDate(Instant.now(Clock.systemUTC()))
                .setBetMoney(new BigDecimal(initial.getAmount()))
                .setBetResult(new BigDecimal(result.getAmount()))
                .setUserId(initial.getUserId());
    }

}
