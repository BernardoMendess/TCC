package com.tcc.crud.service;

import com.tcc.crud.modelo.Carteira;
import com.tcc.crud.modelo.Transacao;
import com.tcc.crud.modelo.dao.CarteiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CarteiraService {

    @Autowired
    private CarteiraRepository carteiraRepository;

    public Carteira criarCarteira(Long clienteId) {
        Carteira carteira = new Carteira();
        carteira.setClienteId(clienteId);
        carteira.setSaldo(BigDecimal.ZERO);
        return carteiraRepository.save(carteira);
    }

    public Carteira getCarteira(Long id) {
        return carteiraRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Carteira atualizarSaldo(Long carteiraId, BigDecimal valor, String tipo) {
        Carteira carteira = getCarteira(carteiraId);

        BigDecimal saldoAtual = carteira.getSaldo() != null ? carteira.getSaldo() : BigDecimal.ZERO;
        carteira.setSaldo(saldoAtual.add(valor));

        Transacao transacao = new Transacao();
        transacao.setValor(valor);
        transacao.setTipo(tipo);
        transacao.setDataHora(LocalDateTime.now());
        transacao.setCarteira(carteira);

        carteira.getTransacoes().add(transacao);

        return carteiraRepository.save(carteira);
    }
}
