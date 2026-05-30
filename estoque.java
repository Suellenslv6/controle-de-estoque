import java.util.Scanner;

public class estoque {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Produto[] produtos = new Produto[100];
        int qtd = 0;
        int opcao;

        do {

            System.out.println("\n===== CONTROLE DE ESTOQUE =====");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Filtrar por categoria");
            System.out.println("4 - Ordenar por categoria");
            System.out.println("5 - Remover produto");
            System.out.println("6 - Atualizar preço");
            System.out.println("7 - Listagem com subtotal por categoria");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    qtd = cadastrarProduto(produtos, qtd);
                    break;

                case 2:
                    listarProdutos(produtos, qtd);
                    break;

                case 3:
                    filtrarCategoria(produtos, qtd);
                    break;

                case 4:
                    ordenarCategoria(produtos, qtd);
                    break;

                case 5:
                    qtd = removerProduto(produtos, qtd);
                    break;

                case 6:
                    atualizarPreco(produtos, qtd);
                    break;

                case 7:
                    subtotalPorCategoria(produtos, qtd);
                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

    }

    // ------------------------------------------------

    public static int cadastrarProduto(Produto[] v, int qtd) {

        if (qtd >= v.length) {
            System.out.println("Estoque cheio!");
            return qtd;
        }

        Produto p = new Produto();

        System.out.print("Nome: ");
        p.nome = sc.nextLine();

        System.out.print("Quantidade em estoque: ");
        p.qtdEstoque = sc.nextInt();

        System.out.print("Preço unitário: ");
        p.precoUnitario = sc.nextDouble();
        sc.nextLine();

        System.out.print("Categoria: ");
        p.categoria = sc.nextLine();

        System.out.print("Quantidade mínima: ");
        p.qtdMinima = sc.nextInt();
        sc.nextLine();

        v[qtd] = p;

        System.out.println("Produto cadastrado!");

        return qtd + 1;
    }

    // ------------------------------------------------

    public static void listarProdutos(Produto[] v, int qtd) {

        if (qtd == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (int i = 0; i < qtd; i++) {

            System.out.println("\nProduto " + (i + 1));

            System.out.println("Nome: " + v[i].nome);
            System.out.println("Quantidade: " + v[i].qtdEstoque);
            System.out.println("Preço: R$ " + v[i].precoUnitario);
            System.out.println("Categoria: " + v[i].categoria);
            System.out.println("Qtd mínima: " + v[i].qtdMinima);

            if (v[i].qtdEstoque < v[i].qtdMinima) {
                System.out.println("ATENÇÃO: Estoque abaixo do mínimo!");
            }
        }
    }

    // ------------------------------------------------

    public static void filtrarCategoria(Produto[] v, int qtd) {

        System.out.print("Digite a categoria: ");
        String categoria = sc.nextLine();

        boolean encontrou = false;

        for (int i = 0; i < qtd; i++) {

            if (v[i].categoria.equalsIgnoreCase(categoria)) {

                System.out.println(
                        v[i].nome +
                        " | Estoque: " + v[i].qtdEstoque +
                        " | Preço: R$ " + v[i].precoUnitario);

                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Categoria não encontrada.");
        }
    }

    // ------------------------------------------------

    public static void ordenarCategoria(Produto[] v, int qtd) {

        for (int i = 0; i < qtd - 1; i++) {

            for (int j = i + 1; j < qtd; j++) {

                if (v[i].categoria.compareToIgnoreCase(v[j].categoria) > 0) {

                    Produto aux = v[i];
                    v[i] = v[j];
                    v[j] = aux;
                }
            }
        }

        System.out.println("Produtos ordenados por categoria.");
    }

    // ------------------------------------------------

    public static int removerProduto(Produto[] v, int qtd) {

        System.out.print("Digite o nome do produto: ");
        String nome = sc.nextLine();

        for (int i = 0; i < qtd; i++) {

            if (v[i].nome.equalsIgnoreCase(nome)) {

                for (int j = i; j < qtd - 1; j++) {
                    v[j] = v[j + 1];
                }

                System.out.println("Produto removido.");
                return qtd - 1;
            }
        }

        System.out.println("Produto não encontrado.");

        return qtd;
    }

    // ------------------------------------------------

    public static void atualizarPreco(Produto[] v, int qtd) {

        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();

        for (int i = 0; i < qtd; i++) {

            if (v[i].nome.equalsIgnoreCase(nome)) {

                System.out.print("Novo preço: ");
                v[i].precoUnitario = sc.nextDouble();
                sc.nextLine();

                System.out.println("Preço atualizado.");
                return;
            }
        }

        System.out.println("Produto não encontrado.");
    }

    // ------------------------------------------------

    public static void subtotalPorCategoria(Produto[] v, int qtd) {

        if (qtd == 0) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        ordenarCategoria(v, qtd);

        String categoriaAtual = "";
        double subtotal = 0;
        double totalGeral = 0;

        for (int i = 0; i < qtd; i++) {

            if (!v[i].categoria.equalsIgnoreCase(categoriaAtual)) {

                if (!categoriaAtual.equals("")) {
                    System.out.printf("Subtotal: R$ %.2f\n\n", subtotal);
                }

                categoriaAtual = v[i].categoria;
                subtotal = 0;

                System.out.println("Categoria: " + categoriaAtual);
            }

            double valorProduto =
                    v[i].qtdEstoque * v[i].precoUnitario;

            System.out.printf(
                    "%s - %d x %.2f = R$ %.2f\n",
                    v[i].nome,
                    v[i].qtdEstoque,
                    v[i].precoUnitario,
                    valorProduto);

            subtotal += valorProduto;
            totalGeral += valorProduto;
        }

        System.out.printf("Subtotal: R$ %.2f\n", subtotal);
        System.out.printf("\nTOTAL GERAL: R$ %.2f\n", totalGeral);
    }
}