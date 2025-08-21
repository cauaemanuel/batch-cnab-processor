package com.transaction_processor.backend.controller;

import com.transaction_processor.backend.dto.TransacaoReport;
import com.transaction_processor.backend.service.TransacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController
{

    private TransacaoService transacaoService;

    public TransacaoController (TransacaoService transacaoService)
    {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    List<TransacaoReport> listAll(){
        return transacaoService.getTotaisTransacoesPorNomeDaLoja();
    }
}
