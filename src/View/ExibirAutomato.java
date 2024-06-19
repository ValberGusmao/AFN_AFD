package View;

import Controller.Afn;
import Model.Estado;
import Model.Transicao;

import java.util.ArrayList;

public class ExibirAutomato {
    public void imprimirTabela(Afn afn, int tamanhoCelula){
        try {
            afn.automatoValido();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        int tamanhoNome = afn.getTamanhoMaiorNome();
        String saida = preencherTabela(" ", tamanhoNome+2);
        String alfabeto = afn.getAlfabeto();
        String aux = "";
        ArrayList<Estado> estados = afn.getEstados();

        saida = saida.concat("|");
        for (int i = 0; i < alfabeto.length(); i++){
            saida = saida.concat(preencherTabela(Character.toString(alfabeto.charAt(i)), tamanhoCelula) + "|");
        }
        saida = saida.concat("\n");

        for (Estado e : estados) {
            //Primeira coluna a partir da segunda linha
            saida = saida.concat(preencherTabela(e.getIdentificao(), tamanhoNome+2)+"|");
            //-----------------------------------------

            for (int i = 0; i < alfabeto.length(); i++){
                for (Transicao t : e.getTransicaosList()){
                    if(t.getOrigem().equals(e)){
                        if(t.getSimbolo() == alfabeto.charAt(i)){
                            aux = aux.concat(t.getDestino().getIdentificao() + ", ");
                        }
                    }
                }
                if (aux.contains(", ")){
                    aux = aux.substring(0, aux.length()-2);
                }
                aux = preencherTabela(aux, tamanhoCelula);

                saida = saida.concat(aux + "|");
                aux = "";
            }
            saida = saida.concat("\n");
        }
        System.out.println(saida);
    }

    private String preencherTabela(String ent, int tamanho){
        boolean dir = true;
        String aux = ent;

        while (aux.length() < tamanho){
            if (dir)
                aux = aux.concat(" ");
            else{
                aux = " ".concat(aux);
            }
            dir = !dir;
        }
        return aux;
    }
}
