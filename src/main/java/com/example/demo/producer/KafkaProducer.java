package com.example.demo.producer;

import com.example.demo.model.NotaFiscal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


@Component
public class KafkaProducer {
    private final KafkaTemplate<Long, NotaFiscal> kafkaTemplate;

    private static final Logger LOG = LoggerFactory
            .getLogger(KafkaProducer.class);

    public KafkaProducer(KafkaTemplate<Long, NotaFiscal> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(final NotaFiscal notaFiscal) {
        CompletableFuture<SendResult<Long, NotaFiscal>> result = kafkaTemplate
                .send("nota-fiscal-topic", notaFiscal.getId(), notaFiscal);

        result.whenComplete((success, error) -> {
            if (error != null) {
                LOG.error("Error sending message: {}", error.getMessage());
            } else {
                LOG.info(
                        "Sent(key={}, partition={}): {}",
                        success.getProducerRecord().partition(),
                        success.getProducerRecord().key(),
                        success.getProducerRecord().value()
                );
            }
        });
    }

}
