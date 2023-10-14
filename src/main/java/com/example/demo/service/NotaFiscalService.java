package com.example.demo.service;

import com.example.demo.controller.NotaFiscalDto;
import com.example.demo.producer.KafkaProducer;
import com.example.demo.repository.NotaFiscalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotaFiscalService {
    private final NotaFiscalRepository notaFiscalRepository;
    private final KafkaProducer kafkaProducer;

    public NotaFiscalService(NotaFiscalRepository notaFiscalRepository, KafkaProducer kafkaProducer) {
        this.notaFiscalRepository = notaFiscalRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Transactional
    public NotaFiscalDto emitir(final NotaFiscalDto notaFiscalDto) {
        var notaFiscal = notaFiscalDto.toEntity();
        notaFiscalRepository.save(notaFiscal);

        if (notaFiscal.getId() == null) {
            throw new RuntimeException("Erro ao emitir nota fiscal");
        }

        kafkaProducer.send(notaFiscal);
        return notaFiscalDto;

    }
}
