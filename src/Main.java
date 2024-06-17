public class Main {
    public static void main(String[] args) {
        
        //Criar um estado
        Estado es0 = new Estado("Q0",true);
        Estado es1 = new Estado("Q1", false);
        Estado es2 = new Estado("Q2", false);
        Estado es3 = new Estado("Q3", false);

        //Criar um automato com alfabeto e estado inicial
        Afn afn = new Afn("abv", es0);
        
        //Adicionar um novo estado
        afn.adicionarEstado(es1);
        afn.adicionarEstado(es2);
        afn.adicionarEstado(es3);

        //Adicionar transição
        es0.adicionarTransicao(es0, 'a');
        es0.adicionarTransicao(es1, 'a');
        es0.adicionarTransicao(es2, 'a');
        es0.adicionarTransicao(es3, 'a');
        es1.adicionarTransicao(es0, 'v');
        es1.adicionarTransicao(es1, 'v');

        //Uma transição que vai retornar erro pelo simbolo fora do alfabeto
                //es1.adicionarTransicao(es1, 'c');

        //Uma das funções de removação
        es1.removerTodasTransicoes();

        //Imprimir o automato
        System.out.println(afn);

        //Imprimir em forma de tabela
        afn.imprimirTabela();

        //Estado Inicial
        System.out.println("Estado Inicial: " + afn.getEstadoInicial().getIdentificao());

        //Imprimir estados finais
        System.out.println("Estados Finais: " + afn.getEstadosFinais());
    }
}
