import java.util.ArrayList;
import java.util.Hashtable;

public class Estado {
    protected String identificao;
    protected Hashtable<Character, ArrayList<Transicao>> transicaos;
    protected boolean ehFinal;
    //Serve para quando for desenhar a tabela caiba todas as combinações de estados possíveis

    public Estado(String id, boolean ehFinal) {
        this.identificao = id;
        this.ehFinal = ehFinal;
        transicaos = new Hashtable<>();

    }

    protected void adicionarTransicao(Transicao t)
    {
        if (t != null) {
            ArrayList<Transicao> tra = transicaos.get(t.getSimbolo());
            if (tra == null){
                tra = new ArrayList<>();
                transicaos.put(t.getSimbolo(), tra);
            }
            else if (tra.contains(t)){
                return;
            }
            tra.add(t);
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
    }

    public String getIdentificao() {
        return identificao;
    }

    public Hashtable<Character, ArrayList<Transicao>> getTransicaos() {
        return transicaos;
    }

    public ArrayList<Transicao> getTransicaosList(){
        ArrayList<Transicao> tr = new ArrayList<>();
        for (Character c: transicaos.keySet()){
            tr.addAll(transicaos.get(c));
        }
        return tr;
    }


    public boolean getEhFinal() {
        return ehFinal;
    }

    @Override
    public String toString() {
        String tr = "[", classicao = "";

        for (Character c : transicaos.keySet()){
            for(Transicao t: transicaos.get(c)){
                if(t.getOrigem().equals(this)){
                    tr = tr.concat(t.toString()+", ");
                }
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
