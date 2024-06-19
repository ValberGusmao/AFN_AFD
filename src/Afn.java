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
            tamanhoMaiorNome = Math.max(e.identificao.length(), tamanhoMaiorNome);
            tamanhoTotalNomes += e.identificao.length();
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
            throw new IllegalArgumentException("Esse estado não é válido pra Estado Incial" + "\n" + ex.getMessage());
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

    //Como não estados trabalhando com transição vazia, qualquer estado é válido no Afn
    //Apenas a classe afd tem a sua verificação de estado
    public void estadoValido(Estado e) throws IllegalArgumentException {
    }
    //-----------------------------------------------------------------------------------



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

    //TODO Provavelmente vou mover essas funções para outro lugar no final--------
    //Se der algum problema depois corrijo
    protected void imprimirTabela(int tamanhoCelula){
        try {
            automatoValido();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        int tamanhoNome = tamanhoMaiorNome;
        String saida = preencherTabela(" ", tamanhoNome+2);
        String aux = "";

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

    public void imprimirTabela(){
        int tamanhoNome = tamanhoTotalNomes;
        imprimirTabela(tamanhoNome + 2 + (estados.size()-1) * (2+tamanhoNome));
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
    //---------------------------------------------------------------------------------------

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
}
