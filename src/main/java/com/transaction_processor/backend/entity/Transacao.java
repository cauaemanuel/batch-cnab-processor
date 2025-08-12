package com.transaction_processor.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "transacao")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Integer tipo;
    private Date data;
    private BigDecimal valor;
    private Long cpf;
    private String cartao;
    private Time hora;
    private String donoDaLoja;
    private String nomeDaLoj;

    public Long getId() {
        return id;
    }

}