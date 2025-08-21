package com.transaction_processor.backend.dto;

import com.transaction_processor.backend.entity.Transacao;

import java.math.BigDecimal;
import java.util.List;

public record TransacaoReport(
        String nomeDaLoja,
        BigDecimal total,
        List<Transacao> transacoes
) {
}
