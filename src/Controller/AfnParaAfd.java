package Controller;

import Model.Estado;
import Model.EstadoComposto;
import Model.Transicao;

import java.util.ArrayList;
import java.util.Hashtable;

public class AfnParaAfd {
    public Afd transfomar(Afn afn) {
        Afd afd = new Afd(afn.alfabeto, afn.estadoInicial);

        //Para cada estado, verificarei suas transições salvando os estados que nelas aparecerem
        for (int k = 0; k < afd.getEstados().size(); k++) {
            Estado estado = afd.getEstados().get(k);
            substituirTransicoesCompostas(estado);

            //Pegando as transições por cada simbolo pertencente ao automato
            for (int i = 0; i < afd.getAlfabeto().length(); i++) {
                //Todas as transições que levam ao atual simbolo do for do estado
                ArrayList<Transicao> tr = estado.getTransicaos().get(afd.getAlfabeto().charAt(i));

                if (tr != null) {
                    //Se a o destino da transição atual for um estado composto,
                    if (tr.get(0).getDestino() instanceof EstadoComposto eC) {
                        afd.adicionarEstado(eC);
                    } else {
                        if(estado != tr.get(0).getDestino()){
                            afd.adicionarEstado(tr.get(0).getDestino());
                        }
                    }
                }
            }
        }
        return afd;
    }

    //Essa função transforma estados simples em transições compostas quando for preciso
    public void substituirTransicoesCompostas(Estado estado) {
        Hashtable<Character, ArrayList<Transicao>> transicaos = estado.getTransicaos();
        ArrayList<Estado> estadosPorTransicao = new ArrayList<>();

        //Passa por cada simbolo do alfabeto que o estado realiza transição
        for (Character c : transicaos.keySet()) {
            //Guarda o destino dessas transições
            for (Transicao t : transicaos.get(c)) {
                estadosPorTransicao.add(t.getDestino());
            }
            //Se for maior que um, significa que terá que ser refeito como um estado composto
            if (estadosPorTransicao.size() > 1) {
                EstadoComposto eC = new EstadoComposto(estadosPorTransicao);
                transicaos.get(c).clear();
                estado.adicionarTransicao(eC, c);
            } else {
                transicaos.get(c).clear();
                //Por ser um novo estado pro afd tem que criar uma nova transição
                estado.adicionarTransicao(estadosPorTransicao.get(0), c);
            }
            estadosPorTransicao.clear();
        }
    }
}
