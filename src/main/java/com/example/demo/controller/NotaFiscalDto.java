package com.example.demo.controller;


import com.example.demo.model.NotaFiscal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NotaFiscalDto(
        String number,
        LocalDate emissionDate,
        Long client,
        BigDecimal valorTotal) {

    public static NotaFiscalDto of(String number, LocalDate emissionDate, Long client, BigDecimal valorTotal) {
        return new NotaFiscalDto(number, emissionDate, client, valorTotal);
    }

    public NotaFiscal toEntity() {
        return NotaFiscal.of(number, emissionDate, client, valorTotal);
    }
}
