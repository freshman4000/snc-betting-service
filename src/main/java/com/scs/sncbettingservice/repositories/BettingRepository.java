package com.scs.sncbettingservice.repositories;

import com.scs.sncbettingservice.models.Bet;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface BettingRepository extends ReactiveSortingRepository<Bet, String> {
}
