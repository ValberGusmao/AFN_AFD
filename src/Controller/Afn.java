package Controller;

import Model.Estado;
import Model.Transicao;
import View.ExibirAutomato;

import java.util.ArrayList;

public class Afn {
    protected String alfabeto;
    protected ArrayList<Estado> estados;
    protected Estado estadoInicial;
    protected int tamanhoMaiorNome = 0;
    protected int tamanhoTotalNomes = 0;

    //Os Estados finais são apresentado na função getEstadosFinais() por ser uma propriedades dos estados

    public Afn(String alfabeto, Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
        this.estados = new ArrayList<>();
        estados.add(estadoInicial);
        this.alfabeto = alfabeto;
    }

    //Adiciona um estado ao autômato
    //Se esse estado já tiver uma transição incopatível um erro será retornado.
    //Mas se uma transição for adicionada depois da adição do estado ao automato ela só será pega no momento de imprmir
    public void adicionarEstado(Estado e) throws IllegalArgumentException {
        try {
            alfabetoValido(e);
            estadoExiste(e);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
        if (!estados.contains(e)) {
            estadoValido(e);
            estados.add(e);
            tamanhoMaiorNome = Math.max(e.getIdentificao().length(), tamanhoMaiorNome);
            tamanhoTotalNomes += e.getIdentificao().length();
        }
    }

    public void removerEstado(Estado e) throws IllegalArgumentException{
        if (estadoInicial == e){
            throw new IllegalArgumentException("Foi removido o estado inicial <"+e.getIdentificao()+">");
        }
        estados.remove(e);
    }

    public void mudarEstadoInicial(Estado e) throws IllegalArgumentException{
        try {
            if (!estados.contains(e)){
                adicionarEstado(e);
            }
        }
        catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Esse estado não é válido pra Model.Estado Incial" + "\n" + ex.getMessage());
        }
        estadoInicial = e;
    }

    //Funções de verificação da integridade do automáto----------------------------------


    //Verificando se o estado tem alguma transição com simbolo fora do alfabeto
    public void alfabetoValido(Estado e) throws Exception {
        for (Transicao t : e.getTransicaosList()) {
            if (alfabeto.indexOf(t.getSimbolo()) == -1) {
                throw new Exception("O estado <" + e.getIdentificao() + "> possui uma transição {" +
                        t + "} com simbolo fora do alfabeto");
            }
        }
    }

    //Verifica pra o automato por completo
    public void alfabetoValido() throws Exception {
        for (Estado e : estados) {
            alfabetoValido(e);
        }
    }

    //Verifica se não tem nenhum erro no automato
    //Só é chamada quando o automato é imprimido
    public void automatoValido() throws Exception {
        alfabetoValido();
        estadoExiste();
        for (Estado e : estados) {
            estadoValido(e);
        }
    }

    public void estadoExiste(Estado e) throws Exception{
        for(Transicao t: e.getTransicaosList()){
            if (t.getOrigem() == e){
                if (!estados.contains(t.getDestino())){
                    throw new Exception("O estado <"+e.getIdentificao()+"> possui uma transição para um estado <"+
                            t.getDestino().getIdentificao()+"> que não existe no autômato");
                }
            }
        }
    }

    public void estadoExiste() throws Exception{
        for (Estado e: estados){
            estadoExiste(e);
        }
    }

    //Como não estados trabalhando com transição vazia, qualquer estado é válido no Controller.Afn
    //Apenas a classe afd tem a sua verificação de estado
    public void estadoValido(Estado e) throws IllegalArgumentException {
    }
    //-----------------------------------------------------------------------------------

    public void imprimirTabela(){
        ExibirAutomato exibirAutomato = new ExibirAutomato();
        exibirAutomato.imprimirTabela(this, 2+tamanhoTotalNomes + (estados.size()-1) * 2);
    }

    public Afd transformarEmAfd(){
        AfnParaAfd afnParaAfd = new AfnParaAfd();
        return afnParaAfd.transfomar(this);
    }

    public ArrayList<Estado> getEstadosFinais() {
        ArrayList<Estado> es = null;
        for (Estado e : estados) {
            if (e.getEhFinal()) {
                if (es == null) {
                    es = new ArrayList<>();
                }
                es.add(e);
            }
        }
        return es;
    }

    @Override
    public String toString() {
        try {
            automatoValido();
        } catch (Exception e) {
            return e.getMessage();
        }

        String saida = "";
        for (Estado e : estados) {
            if (e.equals(estadoInicial)) {
                saida = saida.concat("Inicial: ");
            } else {
                saida = saida.concat("         ");
            }
            saida = saida.concat(e.toString() + "\n");
        }
        return saida;
    }
    public String getAlfabeto() {
        return alfabeto;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public int getTamanhoMaiorNome() {
        return tamanhoMaiorNome;
    }

    public int getTamanhoTotalNomes() {
        return tamanhoTotalNomes;
    }
}
