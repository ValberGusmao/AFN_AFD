public class Transicao {
    private Estado origem, destino;
    private Character simbolo;

    public Transicao(Estado origem, Estado destino, char simbolo) {
        this.origem = origem;
        this.destino = destino;
        this.simbolo = simbolo;
    }

    public Estado getOrigem() {
        return origem;
    }

    public Estado getDestino() {
        return destino;
    }

    public Character getSimbolo() {
        return simbolo;
    }

    @Override
    public boolean equals(Object obj) {
        Transicao t = (Transicao) obj;
        return origem.equals(t.origem) && destino.equals(t.destino) && simbolo == t.simbolo;
    }

    @Override
    public String toString() {
        return origem.getIdentificao() + simbolo + destino.getIdentificao();
    }
}
