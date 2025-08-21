package com.transaction_processor.backend.service;

import com.transaction_processor.backend.dto.TransacaoReport;
import com.transaction_processor.backend.entity.TipoTransacao;
import com.transaction_processor.backend.entity.Transacao;
import com.transaction_processor.backend.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public List<TransacaoReport> getTotaisTransacoesPorNomeDaLoja() {
        var transacoes = transacaoRepository.findAllByOrderByNomeDaLojaAscIdDesc();

        // Agrupa por nome da loja
        var agrupado = transacoes.stream()
                .collect(Collectors.groupingBy(
                        Transacao::nomeDaLoja,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        return agrupado.entrySet().stream()
                .map(entry -> {
                    var nomeDaLoja = entry.getKey();
                    var tipoTransacao = TipoTransacao.findByTipo(entry.getValue().get(0).tipo());
                    var listaTransacoes = entry.getValue();
                    var total = listaTransacoes.stream()
                            .map(Transacao::valor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new TransacaoReport(nomeDaLoja, total, listaTransacoes);
                })
                .collect(Collectors.toList());
    }
}
