package com.example.demo.service;

import com.example.demo.controller.NotaFiscalDto;
import com.example.demo.repository.NotaFiscalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotaFiscalService {
    private final NotaFiscalRepository notaFiscalRepository;

    public NotaFiscalService(NotaFiscalRepository notaFiscalRepository) {
        this.notaFiscalRepository = notaFiscalRepository;
    }

    @Transactional
    public NotaFiscalDto emitir(final NotaFiscalDto notaFiscalDto) {
        var notaFiscal = notaFiscalDto.toEntity();
        notaFiscalRepository.save(notaFiscal);

        if (notaFiscal.getId() == null) {
            throw new RuntimeException("Erro ao emitir nota fiscal");
        }


        return notaFiscalDto;

    }
}
