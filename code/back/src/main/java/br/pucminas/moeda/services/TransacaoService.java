package br.pucminas.moeda.services;

import br.pucminas.moeda.models.Transacao;
import br.pucminas.moeda.models.Usuario;
import br.pucminas.moeda.repositories.TransacaoRepository;
import br.pucminas.moeda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void criarTransacao(Transacao transacao) {

        Usuario de = transacao.getDe(), para = transacao.getPara();

        if (de.equals(para)) {
            throw new IllegalArgumentException("De e para não podem ser iguais");
        }

        if (transacao.getDe().getSaldo() < transacao.getQuantidade()) {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }

        de.setSaldo(de.getSaldo() - transacao.getQuantidade());
        para.setSaldo(para.getSaldo() + transacao.getQuantidade());

        usuarioRepository.save(de);
        usuarioRepository.save(para);
        transacaoRepository.save(transacao);

    }

}
