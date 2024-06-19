package Controller;

import Model.Estado;
import Model.Transicao;
import View.ExibirAutomato;

import java.util.ArrayList;

public class Afd extends Afn {

    public Afd(String alfabeto, Estado estadoInicial) {
        super(alfabeto, estadoInicial);
    }

    //Um estado é válido no afd se ele não tiver mais de uma transição, partindo dele, com o mesmo simbolo.
    @Override
    public void estadoValido(Estado e) throws IllegalArgumentException {
        super.estadoValido(e);
        ArrayList<Character> simbolosUsados = new ArrayList<>();
        for (Transicao t : e.getTransicaosList()) {
            if (t.getOrigem().equals(e)) {
                if (simbolosUsados.contains(t.getSimbolo())) {
                    throw new IllegalArgumentException("Para o estado <" + e.getIdentificao() + "> a transição {" + t.toString() + "} é inválida para esse automato");
                }
                simbolosUsados.add(t.getSimbolo());
            }
        }
    }

    @Override
    public void imprimirTabela() {
        //Como o afd só tem uma transição por um simbolo é preciso apenas reservar espaço para o nome do maior estado
        ExibirAutomato exibirAutomato = new ExibirAutomato();
        exibirAutomato.imprimirTabela(this, 2 + tamanhoMaiorNome);
    }
}