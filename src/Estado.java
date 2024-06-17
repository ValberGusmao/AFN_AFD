import java.util.ArrayList;

public class Estado {
    private String identificao;
    private ArrayList<Transicao> transicaos;
    private boolean ehFinal;
    //Serve para quando for desenhar a tabela caiba todas as combinações de estados possíveis
    private static int tamanhoMaiorNome;

    public Estado(String id, boolean ehFinal) {
        this.identificao = id;
        this.ehFinal = ehFinal;
        transicaos = new ArrayList<>();

        tamanhoMaiorNome = Math.max(identificao.length(), tamanhoMaiorNome);
    }

    private void adicionarTransicao(Transicao transicao)
    {
        if (!transicaos.contains(transicao)) {
            transicaos.add((transicao));
        }
    }

    public void removerTodasTransicoes(){
        transicaos.clear();
    }
    public void removerTransicao(Transicao t){
        transicaos.remove(t);
    }
    public void removerTransicao(Estado destino, char simbolo){
        Transicao t = new Transicao(this, destino, simbolo);
        removerTransicao(t);
    }

    public void adicionarTransicao(Estado destino, char simbolo){
        Transicao t = new Transicao(this, destino, simbolo);
        this.adicionarTransicao(t);
        destino.adicionarTransicao(t);
    }

    public String getIdentificao() {
        return identificao;
    }

    public ArrayList<Transicao> getTransicaos() {
        return transicaos;
    }

    public boolean getEhFinal() {
        return ehFinal;
    }

    public static int getTamanhoMaiorNome() {
        return tamanhoMaiorNome;
    }

    @Override
    public String toString() {
        String tr = "[", classicao = "";

        for (Transicao t : transicaos){
            if(t.getOrigem().equals(this)){
                tr = tr.concat(t.toString()+", ");
            }
        }
        if (tr.contains(", ")){
            tr = tr.substring(0, tr.length()-2);
        }
        tr = tr.concat("]");

        classicao = ehFinal ? "Final - " : "";
        return "{" + identificao + " - " + classicao + tr + "}";
    }

    @Override
    public boolean equals(Object obj) {
        Estado e = (Estado) obj;
        return identificao.equals(e.identificao);
    }
}
