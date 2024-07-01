import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Arvore_AVL arvoreAVL = new Arvore_AVL();
        int escolha, valor;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Inserir valor");
            System.out.println("2. Remover valor");
            System.out.println("3. Imprimir árvore");
            System.out.println("4. Pesquisar valor");
            System.out.println("5. Encontrar maior valor");
            System.out.println("6. Encontrar menor valor");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    System.out.print("Digite o valor a ser inserido: ");
                    valor = scanner.nextInt();
                    arvoreAVL.insere(arvoreAVL.raiz, valor);
                    break;
                case 2:
                    System.out.print("Digite o valor a ser removido: ");
                    valor = scanner.nextInt();
                    arvoreAVL.remove(valor);
                    break;
                case 3:
                    arvoreAVL.imprime(arvoreAVL.raiz);
                    break;
                case 4:
                    System.out.print("Digite o valor a ser pesquisado: ");
                    valor = scanner.nextInt();
                    boolean encontrado = arvoreAVL.procura(valor);
                    if (encontrado)
                        System.out.println("Valor encontrado.");
                    else
                        System.out.println("Valor não encontrado.");
                    break;
                case 5:
                    System.out.println("Maior valor: " + arvoreAVL.encontraMaximo(arvoreAVL.raiz).getValor());
                    break;
                case 6:
                    System.out.println("Menor valor: " + arvoreAVL.encontraMinimo(arvoreAVL.raiz).getValor());
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (escolha != 0);

        scanner.close();
    }
}