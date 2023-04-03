package com.scs.sncbettingservice.services;

import com.scs.sncbettingservice.repositories.BettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import snc.sncmodels.services.betting.rq.BetRq;

import java.util.Random;

@Slf4j
@Service
public class BetService {
    @Value("${betservice.win.rate}")
    private Double winRate;
    private final BettingRepository bettingRepository;
    private final Mapper mapper;

    public BetService(BettingRepository bettingRepository, Mapper mapper) {
        this.bettingRepository = bettingRepository;
        this.mapper = mapper;
    }

    public BetRq calculateBetResult(BetRq betRq) {
        BetRq result = new BetRq()
                .setUserId(betRq.getUserId())
                .setAmount(new Random().nextDouble() > winRate ? "-" + betRq.getAmount() : betRq.getAmount());
        bettingRepository.save(mapper.mapBetRqToBet(betRq, result)).subscribe();
        return result;
    }

}
