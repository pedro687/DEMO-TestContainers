package com.example.demo.model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "nota_fiscal")
@Entity
public class NotaFiscal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private LocalDate dataEmissao;

    @Column(nullable = false)
    private Long client; // Imagine que aqui seja uma chave estrangeira para a tabela de clientes

    private BigDecimal valorTotal;

    public NotaFiscal() {
    }

    private NotaFiscal(Long id, String numero, LocalDate dataEmissao, Long client, BigDecimal valorTotal) {
        this.id = id;
        this.numero = numero;
        this.dataEmissao = dataEmissao;
        this.client = client;
        this.valorTotal = valorTotal;
    }

    public static NotaFiscal of(String number, LocalDate emissionDate, Long client, BigDecimal valorTotal) {
        return new NotaFiscal(null, number, emissionDate, client, valorTotal);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String number) {
        this.numero = number;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

}