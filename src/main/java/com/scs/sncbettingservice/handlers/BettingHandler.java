package com.scs.sncbettingservice.handlers;

import com.snc.snckafkastarter.JsonConverter;
import com.snc.snckafkastarter.converters.KafkaMessageCreator;
import com.snc.snckafkastarter.event.annotation.Event;
import com.snc.snckafkastarter.event.annotation.EventHandler;
import com.snc.snckafkastarter.kafka.MessageService;
import com.snc.snckafkastarter.models.KafkaMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import snc.sncmodels.services.betting.rq.BetRq;

@EventHandler
@AllArgsConstructor
@Slf4j
public class BettingHandler {
    private final MessageService messageService;
    private final KafkaMessageCreator kafkaMessageCreator;

    @Event("make_manual_bet")
    public void makeBet(KafkaMessage message) {
        try {
            BetRq betRq = JsonConverter.objectFromJson(message.getMessageBody(), BetRq.class);
           log.info("Got request " + betRq.getAmount());
        } catch (Exception exception) {
            messageService.sendMessage(kafkaMessageCreator.getErrorMessage(exception, message.getHeaders()));
        }
    }
    @Event("request_balance_change")
    public void changeBalance(KafkaMessage message) {

    }
}
