package com.example.demo.controller;

import com.example.demo.service.NotaFiscalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nota-fiscal")
public class NotaFiscalController {

    private final NotaFiscalService notaFiscalService;

    public NotaFiscalController(NotaFiscalService notaFiscalService) {
        this.notaFiscalService = notaFiscalService;
    }


    @PostMapping("/emitir")
    public ResponseEntity<?> emitirNotaFiscal(@RequestBody final NotaFiscalDto notaFiscalDto) {
        try {
            return ResponseEntity.ok(notaFiscalService.emitir(notaFiscalDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
