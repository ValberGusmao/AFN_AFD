import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String alfabeto = lerAlfabeto();
        ArrayList<Estado> estados = new ArrayList<>();
        Estado estadoInicial = lerEstadoInicial(estados);

        Afn afn = new Afn(alfabeto, estadoInicial);

        int opcao;
        do {
            opcao = menu();
            switch (opcao) {
                case 1:
                    Estado novoEstado = lerEstado();
                    try {
                        afn.adicionarEstado(novoEstado);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    adicionarTransicao(afn.getEstados());
                    break;
                case 3:
                    System.out.println(afn);
                    break;
                case 4:
                    afn.imprimirTabela();
                    break;
                case 5:
                    System.out.println("Estado Inicial: " + afn.getEstadoInicial().getIdentificao());
                    break;
                case 6:
                    System.out.println("Estados Finais: " + afn.getEstadosFinais());
                    break;
                case 0:
                    System.out.println("Voce saiu");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static int menu() {
        System.out.println("\nMenu:");
        System.out.println("1. Adicionar estado");
        System.out.println("2. Adicionar transicao");
        System.out.println("3. Mostrar automato");
        System.out.println("4. Imprimir tabela do automato");
        System.out.println("5. Exibir estado inicial");
        System.out.println("6. Exibir estados finais");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opcao: ");
        return scanner.nextInt();
    }

    private static String lerAlfabeto() {
        System.out.print("Digite o alfabeto do automato: ");
        return scanner.next();
    }

    private static Estado lerEstadoInicial(ArrayList<Estado> estados) {
        System.out.println("Digite o estado inicial:");
        Estado estadoInicial = lerEstado();
        estados.add(estadoInicial);
        return estadoInicial;
    }

    private static Estado lerEstado() {
        boolean repetir;
        boolean ehFinal = false;
        String id;

        System.out.print("Digite o identificador do estado: ");
        id = scanner.next();
        do{
            repetir = false;
            System.out.print("O estado eh final? (true/false): ");
            try{
                ehFinal = scanner.nextBoolean();
            }
            catch (Exception ex){
                System.out.println("Identificador Inválido!! Digite Novamente");
                scanner.next();
                repetir = true;
            }
        }while (repetir);
        return new Estado(id, ehFinal);
    }

    private static void adicionarTransicao(ArrayList<Estado> estados) {
        System.out.print("Digite o identificador do estado de origem: ");
        String idOrigem = scanner.next();
        Estado origem = buscarEstado(estados, idOrigem);
        if (origem == null) {
            System.out.println("Estado de origem nao encontrado.");
            return;
        }

        System.out.print("Digite o identificador do estado de destino: ");
        String idDestino = scanner.next();
        Estado destino = buscarEstado(estados, idDestino);
        if (destino == null) {
            System.out.println("Estado de destino nao encontrado.");
            return;
        }

        System.out.print("Digite o simbolo da transicao: ");
        char simbolo = scanner.next().charAt(0);

        origem.adicionarTransicao(destino, simbolo);
    }

    private static Estado buscarEstado(ArrayList<Estado> estados, String id) {
        for (Estado e : estados) {
            if (e.getIdentificao().equals(id)) {
                return e;
            }
        }
        return null;
    }
}
