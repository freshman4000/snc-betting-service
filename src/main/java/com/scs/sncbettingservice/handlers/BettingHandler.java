package com.scs.sncbettingservice.handlers;

import com.scs.sncbettingservice.services.BetService;
import com.snc.snckafkastarter.JsonConverter;
import com.snc.snckafkastarter.converters.KafkaMessageCreator;
import com.snc.snckafkastarter.event.annotation.Event;
import com.snc.snckafkastarter.event.annotation.EventHandler;
import com.snc.snckafkastarter.kafka.MessageService;
import com.snc.snckafkastarter.models.Headers;
import com.snc.snckafkastarter.models.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import snc.sncmodels.constants.Events;
import snc.sncmodels.services.betting.rq.BetRq;

import java.util.Map;

@EventHandler
@AllArgsConstructor
@Slf4j
public class BettingHandler {
    private final MessageService messageService;
    private final KafkaMessageCreator kafkaMessageCreator;
    private final BetService betService;

    @Event(Events.MAKE_MANUAL_BET)
    public void makeBet(KafkaMessage message) {
        Map<String, String> headers = message.getHeaders();
        try {
            BetRq betRq = JsonConverter.objectFromJson(message.getMessageBody(), BetRq.class);
           log.info("Got request to make bet " + betRq.getAmount());
           log.info("Calculating bet result...");
           BetRq result = betService.calculateBetResult(betRq);
           log.info("Got result of calculation: " + result.getAmount());
           headers.put(Headers.EVENT_NAME, Events.APPLY_BET_RESULT);
           messageService.sendMessage(kafkaMessageCreator.getMessage(result, headers), "snc-user-service");
        } catch (Exception exception) {
            messageService.sendMessage(kafkaMessageCreator.getErrorMessage(exception, message.getHeaders()));
        }
    }
}
