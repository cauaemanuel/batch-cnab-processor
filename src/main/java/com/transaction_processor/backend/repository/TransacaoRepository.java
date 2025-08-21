package com.transaction_processor.backend.repository;

import com.transaction_processor.backend.entity.Transacao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {

    List<Transacao> findAllByOrderByNomeDaLojaAscIdDesc();
}
