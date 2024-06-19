package Model;

import java.util.ArrayList;

public class EstadoComposto extends Estado{
    ArrayList<Estado> estados;

    public EstadoComposto(ArrayList<Estado> estados) {
        super("", false);
        boolean efinal = false;
        StringBuilder stringBuilder = new StringBuilder();

        this.estados = new ArrayList<>();

        this.estados.addAll(estados);

        ArrayList<Estado> simples = quebrarEstado(estados);
        for (Estado e: estados) {
            if(!simples.contains(e)){
                if (e instanceof EstadoComposto) {
                    for (Estado ee :((EstadoComposto) e).estados){
                        if (!simples.contains(ee)){
                            simples.add(ee);
                        }
                    }
                } else {
                    simples.add(e);
                }
            }
        }

        for (Estado e: simples){
            stringBuilder.append(e.getIdentificao());
            if (e.getEhFinal()){
                efinal = true;
            }
            for(Transicao t: e.getTransicaosList()){
                adicionarTransicao(t);
            }
        }
        identificao = stringBuilder.toString();
        ehFinal = efinal;
    }

    //Serve para quebrar estados compostos em estados individuais
    public ArrayList<Estado> quebrarEstado(ArrayList<Estado> estados){
        ArrayList<Estado> simples = new ArrayList<>();
        for (Estado e: estados) {
            if(!simples.contains(e)){
                if (e instanceof EstadoComposto) {
                    for (Estado ee :((EstadoComposto) e).estados){
                        if (!simples.contains(ee)){
                            simples.add(ee);
                        }
                    }
                } else {
                    simples.add(e);
                }
            }
        }
        return simples;
    }

    @Override
    public boolean equals(Object obj) {
        EstadoComposto ec;
        try{
            ec = (EstadoComposto) obj;
        }catch (Exception ex){
            return super.equals(obj);
        }
        if(ec.identificao.length() != identificao.length()){
            return false;
        }
        for (Estado e : quebrarEstado(ec.estados)){
            ArrayList<Estado> aux = quebrarEstado(estados);
            if (!aux.contains(e)){
                return false;
            }
        }
        return true;
    }
}
