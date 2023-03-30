package com.scs.sncbettingservice.services;

import com.scs.sncbettingservice.exceptions.InsufficientBalanceException;
import com.scs.sncbettingservice.models.Bet;
import com.scs.sncbettingservice.models.dto.BalanceDto;
import com.scs.sncbettingservice.models.dto.BetRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class BetService {
    @Value("${betservice.win.rate}")
    private Double winRate;
    private WebClient webClient;

    public BetService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ResponseEntity<BetRs>> makeBet(Bet bet) {
        return getBalance(bet.getUserId())
                .map(balance -> {
                    if (balance.getMoney().compareTo(bet.getBetMoney()) < 0) throw new InsufficientBalanceException();
                    if (Math.random() > winRate) balance.setMoney(balance.getMoney().subtract(bet.getBetMoney()));
                    else balance.setMoney(balance.getMoney().add(bet.getBetMoney()));
                    return balance;
                })
                .zipWhen(balance -> updateBalance(bet.getUserId(), balance))
                .map(tuple2 -> {
                    BalanceDto a = tuple2.getT1();
                    BalanceDto b = tuple2.getT2();
                    return new ResponseEntity<>(new BetRs()
                            .setId(bet.getId())
                            .setBalance(b.getMoney())
                            .setPreviousBet(bet), HttpStatus.OK);
                })
                .doOnError(error -> log.error(error.getMessage()));
    }

    public Mono<BalanceDto> getBalance(String userId) {
        return webClient
                .get()
                .uri("http://localhost:8088/userapp/api/v1/users/balance")
                .header("X-USER-ID", userId)
                .retrieve()
                .bodyToMono(BalanceDto.class);
    }

    public Mono<BalanceDto> updateBalance(String userId, BalanceDto balanceDto) {
        return webClient
                .put()
                .uri("http://localhost:8088/userapp/api/v1/users/balance")
                .header("X-USER-ID", userId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(balanceDto), BalanceDto.class)
                .retrieve()
                .bodyToMono(BalanceDto.class);
    }
}
