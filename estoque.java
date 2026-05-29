import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class estoque {
    String nome, descricao;
    String categoria;
    int qtdEmEstoque;
    int qtdMinima;
    Double precoUnit;
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
       public Produto(String nome, String descricao, int qtdEstoque, double precoUnitario, String categoria, int qtdMinima) {
        this.nome = nome;
        this.descricao = descricao;
        this.qtdEstoque = qtdEstoque;
        this.precoUnitario = precoUnitario;
        this.categoria = categoria;
        this.qtdMinima = qtdMinima;
       }
    }

    // 1. CADASTRAR PRODUTOS
    private static void cadastrarProduto() {
        System.out.println("\n--- CADASTRAR PRODUTO ---");

        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Descricao: ");
        String descricao = scanner.nextLine().trim();

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine().trim();

        System.out.print("Quantidade em estoque: ");
        int qtd = lerInt();

        System.out.print("Quantidade minima: ");
        int qtdMin = lerInt();

        System.out.print("Preco unitario (R$): ");
        double preco = lerDouble();

        Produto p = new Produto(nome, descricao, qtd, preco, categoria, qtdMin);
        produtos.add(p);

        System.out.println("\n[OK] Produto '" + nome + "' cadastrado com sucesso!");
    }

    
    // 2. LISTAR PRODUTOS
    private static void listarProdutos() {
        System.out.println("\n--- LISTAGEM DE PRODUTOS ---");
        if (produtos.isEmpty()) {
            System.out.println("[INFO] Nenhum produto cadastrado.");
            return;
        }
        imprimirCabecalho();
        for (Produto p : produtos) {
            System.out.println(p);
        }
        System.out.println("Total de produtos: " + produtos.size());
    }

  
    // 3. FILTRAR POR CATEGORIA
    private static void filtrarPorCategoria() {
        System.out.println("\n--- FILTRAR POR CATEGORIA ---");
        System.out.print("Digite a categoria: ");
        String cat = scanner.nextLine().trim();

        List<Produto> filtrados = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getCategoria().equalsIgnoreCase(cat)) {
                filtrados.add(p);
            }
        }

        if (filtrados.isEmpty()) {
            System.out.println("[INFO] Nenhum produto encontrado na categoria '" + cat + "'.");
        } else {
            System.out.println("Produtos da categoria '" + cat + "':");
            imprimirCabecalho();
            for (Produto p : filtrados) {
                System.out.println(p);
            }
        }
    }

   
    // 4. ORDENAR PRODUTOS
    private static void ordenarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("\n[INFO] Nenhum produto cadastrado.");
            return;
        }
        System.out.println("\n--- ORDENAR PRODUTOS ---");
        System.out.println("Ordenar por:");
        System.out.println("  1 - Nome (A-Z)");
        System.out.println("  2 - Preco (menor ao maior)");
        System.out.println("  3 - Quantidade em estoque (menor ao maior)");
        System.out.println("  4 - Categoria (A-Z)");
        System.out.print("Escolha: ");

        int op = lerInt();
        switch (op) {
            case 1 -> produtos.sort(Comparator.comparing(p -> p.getNome().toLowerCase()));
            case 2 -> produtos.sort(Comparator.comparingDouble(Produto::getPrecoUnitario));
            case 3 -> produtos.sort(Comparator.comparingInt(Produto::getQtdEstoque));
            case 4 -> produtos.sort(Comparator.comparing(p -> p.getCategoria().toLowerCase()));
            default -> { System.out.println("[ERRO] Opcao invalida."); return; }
        }

        System.out.println("\n[OK] Produtos ordenados com sucesso!");
        imprimirCabecalho();
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

   
    // 5. REMOVER PRODUTO
    private static void removerProduto() {
        System.out.println("\n--- REMOVER PRODUTO ---");
        if (produtos.isEmpty()) {
            System.out.println("[INFO] Nenhum produto cadastrado.");
            return;
        }

        System.out.print("Digite o nome do produto a remover: ");
        String nome = scanner.nextLine().trim();

        Iterator<Produto> it = produtos.iterator();
        boolean removido = false;
        while (it.hasNext()) {
            Produto p = it.next();
            if (p.getNome().equalsIgnoreCase(nome)) {
                it.remove();
                removido = true;
                break;
            }
        }

        if (removido) {
            System.out.println("[OK] Produto '" + nome + "' removido com sucesso!");
        } else {
            System.out.println("[ERRO] Produto '" + nome + "' nao encontrado.");
        }
    }

    // 6. ATUALIZAR PRECO
    private static void atualizarPreco() {
        System.out.println("\n--- ATUALIZAR PRECO ---");
        if (produtos.isEmpty()) {
            System.out.println("[INFO] Nenhum produto cadastrado.");
            return;
        }

        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine().trim();

        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                System.out.printf("Preco atual de '%s': R$ %.2f%n", p.getNome(), p.getPrecoUnitario());
                System.out.print("Novo preco (R$): ");
                double novoPreco = lerDouble();
                p.setPrecoUnitario(novoPreco);
                System.out.println("[OK] Preco atualizado para R$ " + df.format(novoPreco));
                return;
            }
        }
        System.out.println("[ERRO] Produto '" + nome + "' nao encontrado.");
    }

    
    // 7. LISTAGEM COM SUBTOTAL POR CATEGORIA
    private static void listagemComSubtotal() {
        System.out.println("\n--- LISTAGEM COM SUBTOTAL POR CATEGORIA ---");
        if (produtos.isEmpty()) {
            System.out.println("[INFO] Nenhum produto cadastrado.");
            return;
        }

        // Ordenar por categoria
        List<Produto> ordenados = new ArrayList<>(produtos);
        ordenados.sort(Comparator.comparing(p -> p.getCategoria().toLowerCase()));

        double totalGeral = 0;
        String categoriaAtual = null;
        double subtotal = 0;

        for (int i = 0; i < ordenados.size(); i++) {
            Produto p = ordenados.get(i);

            // Nova categoria encontrada
            if (!p.getCategoria().equalsIgnoreCase(categoriaAtual)) {
                // Imprime subtotal da categoria anterior
                if (categoriaAtual != null) {
                    System.out.printf("  %-47s Subtotal: R$ %10.2f%n", "", subtotal);
                    System.out.println();
                }
                categoriaAtual = p.getCategoria();
                subtotal = 0;
                System.out.println("Categoria: " + categoriaAtual.toUpperCase());
                System.out.println("  " + "-".repeat(90));
                imprimirCabecalho("  ");
            }

            System.out.println("  " + p);
            subtotal += p.getValorTotalEstoque();
            totalGeral += p.getValorTotalEstoque();
        }

        // Subtotal da ultima categoria
        if (categoriaAtual != null) {
            System.out.printf("  %-47s Subtotal: R$ %10.2f%n", "", subtotal);
        }

        System.out.println();
        System.out.println("=".repeat(94));
        System.out.printf("  TOTAL GERAL EM ESTOQUE:  R$ %,.2f%n", totalGeral);
        System.out.println("=".repeat(94));
    }

    // ─────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────
    private static void imprimirCabecalho() {
        imprimirCabecalho("");
    }

    private static void imprimirCabecalho(String prefix) {
        System.out.println(prefix + String.format("%-20s | %-15s | %-9s | %-10s | %-15s | %-14s",
                "NOME", "CATEGORIA", "QTD ESTQ", "QTD MIN", "PRECO UNIT", "TOTAL ESTQ"));
        System.out.println(prefix + "-".repeat(90));
    }

    private static int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("[ERRO] Digite um numero inteiro valido: ");
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("[ERRO] Digite um valor valido (ex: 10.50): ");
            }
        }
    }
}