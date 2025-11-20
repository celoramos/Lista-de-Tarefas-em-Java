package ListaTarefas;
import java.util.Scanner;

public class ListaTarefas {
    static final int CAPACIDADE_MAXIMA = 20;
    static String[] descricoes = new String[CAPACIDADE_MAXIMA];
    static boolean[] concluidas = new boolean[CAPACIDADE_MAXIMA];
    static char[] prioridades = new char[CAPACIDADE_MAXIMA];
    static int totalTarefas = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean executando = true;
        while (executando) {
            exibirMenu();

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listarTodasAsTarefas();
                    break;
                case 2:
                    adicionarNovaTarefa();
                    break;
                case 3:
                    removerTarefaExistente();
                    break;
                case 4:
                    marcarTarefaComoConcluida();
                    break;
                case 5:
                    exibirEstatisticas();
                    break;
                case 0:
                    executando = false;
                    System.out.println("Saindo do programa. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\n--- Simulador de Lista de Tarefas ---");
        System.out.println("1. Visualizar a lista");
        System.out.println("2. Adicionar item a lista de tarefas");
        System.out.println("3. Remover um item da lista");
        System.out.println("4. Marcar uma tarefa como concluida");
        System.out.println("5. Exibir Estatísticas"); // Nova opção
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void adicionarNovaTarefa() {
        if (totalTarefas >= CAPACIDADE_MAXIMA) {
            System.out.println("Erro: A lista de tarefas está cheia (limite de " + CAPACIDADE_MAXIMA + " itens).");
            return;
        }

        System.out.print("Digite a descrição da nova tarefa: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite a prioridade (A - Alta, M - Média, B - Baixa): ");
        char prioridade = scanner.nextLine().toUpperCase().charAt(0);

        descricoes[totalTarefas] = descricao;
        prioridades[totalTarefas] = prioridade;
        concluidas[totalTarefas] = false;
        totalTarefas++;
        System.out.println("Tarefa adicionada com sucesso!");
    }

    public static void removerTarefaExistente() {
        listarTodasAsTarefas();

        if (totalTarefas == 0) {
            System.out.println("Não há tarefas para remover.");
            return;
        }

        System.out.print("Digite o número da tarefa que deseja remover: ");
        int numeroTarefa = scanner.nextInt();
        scanner.nextLine();

        int indice = numeroTarefa - 1;

        if (indice < 0 || indice >= totalTarefas) {
            System.out.println("Erro: Número da tarefa inválido.");
            return;
        }

        for (int i = indice; i < totalTarefas - 1; i++) {
            descricoes[i] = descricoes[i + 1];
            concluidas[i] = concluidas[i + 1];
            prioridades[i] = prioridades[i + 1];
        }

        totalTarefas--;
        descricoes[totalTarefas] = null;
        concluidas[totalTarefas] = false;
        prioridades[totalTarefas] = ' ';
        System.out.println("Tarefa " + numeroTarefa + " removida com sucesso.");
    }

    public static void listarTodasAsTarefas() {
        System.out.println("\n" + "Lista de Tarefas:" + ":");

        if (totalTarefas == 0) {
            System.out.println("A lista está vazia.");
            return;
        }

        for (int i = 0; i < totalTarefas; i++) {
            String status = concluidas[i] ? "x" : " ";
            char prioridade = prioridades[i];
            String descricao = descricoes[i];
            System.out.printf("%02d. [%s] [%c] %s\n", (i + 1), status, prioridade, descricao);
        }
    }

    public static void marcarTarefaComoConcluida() {
        listarTodasAsTarefas();

        if (totalTarefas == 0) {
            System.out.println("Não há tarefas para marcar.");
            return;
        }

        System.out.print("Digite o número da tarefa que deseja marcar como concluída: ");
        int numeroTarefa = scanner.nextInt();
        scanner.nextLine();
        int indice = numeroTarefa - 1;

        if (indice < 0 || indice >= totalTarefas) {
            System.out.println("Erro: Número da tarefa inválido.");
            return;
        }

        concluidas[indice] = true;
        System.out.println("Tarefa " + numeroTarefa + " marcada como concluída!");
    }

    public static void exibirEstatisticas() {
        int tarefasConcluidas = 0;

        for (int i = 0; i < totalTarefas; i++) {
            if (concluidas[i]) {
                tarefasConcluidas++;
            }
        }

        int tarefasPendentes = totalTarefas - tarefasConcluidas;
        double percentualConcluido = 0.0;

        if (totalTarefas > 0) {
            percentualConcluido = ((double) tarefasConcluidas / totalTarefas) * 100;
        }

        System.out.println("\n==================================");
        System.out.println("           ESTATÍSTICAS           ");
        System.out.println("==================================");
        System.out.printf("Total de Tarefas:       %d\n", totalTarefas);
        System.out.printf("Tarefas Concluídas:     %d\n", tarefasConcluidas);
        System.out.printf("Tarefas Pendentes:      %d\n", tarefasPendentes);
        System.out.printf("Percentual Concluído:   %.0f%%\n", percentualConcluido);
        System.out.println("==================================");
    }
}s
