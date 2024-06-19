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
                ArrayList<Transicao> tr = estado.getTransicaos().get(afd.getAlfabeto().charAt(i));
                if (tr != null) {
                    if (tr.get(0).getDestino() instanceof EstadoComposto) {
                        EstadoComposto eC = (EstadoComposto) tr.get(0).getDestino();
                        afd.adicionarEstado(eC);
                    } else {
                        if(estado != tr.get(0).getDestino()){
                            afd.adicionarEstado(tr.get(0).getDestino());
                        }
                    }
                }
            }
        }
        afd.imprimirTabela();
        return afd;
    }

    public void substituirTransicoesCompostas(Estado estado) {
        Hashtable<Character, ArrayList<Transicao>> transicaos = estado.transicaos;
        ArrayList<Estado> es = new ArrayList<>();
        for (Character c : transicaos.keySet()) {
            for (Transicao t : transicaos.get(c)) {
                es.add(t.getDestino());
            }
            if (es.size() > 1) {
                EstadoComposto eC = new EstadoComposto(es);
                transicaos.get(c).clear();
                estado.adicionarTransicao(eC, c);
            } else {
                transicaos.get(c).clear();
                estado.adicionarTransicao(es.get(0), c);
            }
            es.clear();
        }

    }
}
