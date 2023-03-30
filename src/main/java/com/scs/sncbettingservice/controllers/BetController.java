package com.scs.sncbettingservice.controllers;

import com.scs.sncbettingservice.models.Bet;
import com.scs.sncbettingservice.models.dto.BetRs;
import com.scs.sncbettingservice.services.BetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class BetController {
    private final BetService betService;
    @PostMapping("/bet")
    public Mono<ResponseEntity<BetRs>> makeBet(@RequestBody Bet bet) {
        return betService.makeBet(bet);
    }
}
