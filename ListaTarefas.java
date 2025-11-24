package ListaTarefas;

import java.util.Scanner;

public class ListaTarefas {

    static int CAPACIDADE_ATUAL = 20;
    static String[] descricoes = new String[CAPACIDADE_ATUAL];
    static boolean[] concluidas = new boolean[CAPACIDADE_ATUAL];
    static char[] prioridades = new char[CAPACIDADE_ATUAL];
    static int totalTarefas = 0;
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        personalizarTamanhoInicial();
        
        boolean executando = true;
        while (executando) {
            exibirMenu();

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listarTarefasComFiltro();
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
                    alterarDadosTarefa();
                    break;
                case 6:
                    limparListaTarefas();
                    break;
                case 7:
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

    public static void personalizarTamanhoInicial() {
        System.out.println("\n--- Configuração Inicial ---");
        System.out.print("Deseja definir o tamanho máximo da lista de tarefas? (S/N): ");
        String resposta = scanner.nextLine().toUpperCase();

        if (resposta.equals("S")) {
            System.out.print("Digite o novo tamanho máximo (capacidade): ");
            
            int novoTamanho = scanner.nextInt();
            scanner.nextLine();
            
            if (novoTamanho > 0) {
                CAPACIDADE_ATUAL = novoTamanho;
                descricoes = new String[CAPACIDADE_ATUAL];
                concluidas = new boolean[CAPACIDADE_ATUAL];
                prioridades = new char[CAPACIDADE_ATUAL];
                System.out.println("Capacidade máxima definida para " + CAPACIDADE_ATUAL + " tarefas.");
            } else {
                 System.out.println("Tamanho inválido. Usando o tamanho padrão de " + CAPACIDADE_ATUAL + ".");
            }
        }
    }

    public static void exibirMenu() {
        System.out.println("\n--- Simulador de Lista de Tarefas ---");
        System.out.println("1. Visualizar a lista (Com Filtro)");
        System.out.println("2. Adicionar item a lista de tarefas");
        System.out.println("3. Remover um item da lista");
        System.out.println("4. Marcar uma tarefa como concluída");
        System.out.println("5. Alterar dados da tarefa"); 
        System.out.println("6. Limpar Lista de Tarefas (Remover todas)"); 
        System.out.println("7. Exibir Estatísticas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void adicionarNovaTarefa() {
        if (totalTarefas >= CAPACIDADE_ATUAL) {
            System.out.println("Erro: A lista de tarefas está cheia (limite de " + CAPACIDADE_ATUAL + " itens).");
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
        listarTodasAsTarefas("TODAS");

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
    
    public static void listarTarefasComFiltro() {
        System.out.println("\n--- Opções de Visualização ---");
        System.out.println("1. Exibir Todas as tarefas");
        System.out.println("2. Exibir Apenas Pendentes");
        System.out.println("3. Exibir Apenas Concluídas");
        System.out.print("Escolha uma opção de filtro: ");
        
        int opcaoFiltro = scanner.nextInt();
        scanner.nextLine();

        String filtro = "TODAS";
        switch (opcaoFiltro) {
            case 2:
                filtro = "PENDENTES";
                break;
            case 3:
                filtro = "CONCLUIDAS";
                break;
            case 1:
            default:
                filtro = "TODAS";
                break;
        }
        
        listarTodasAsTarefas(filtro);
    }


    public static void listarTodasAsTarefas(String filtro) {
        System.out.println("\nLista de Tarefas (" + filtro + "):");

        if (totalTarefas == 0) {
            System.out.println("A lista está vazia.");
            return;
        }
        
        System.out.printf(" %-4s | %-1s | %-1s | %s\n", 
                          "Nº", "S", "P", "Descrição");
        System.out.println("------|---|---|--------------------------------");


        for (int i = 0; i < totalTarefas; i++) {
            
            boolean exibir = false;
            if (filtro.equals("TODAS")) {
                exibir = true;
            } else if (filtro.equals("PENDENTES") && !concluidas[i]) {
                exibir = true;
            } else if (filtro.equals("CONCLUIDAS") && concluidas[i]) {
                exibir = true;
            }

            if (exibir) {
                String status = concluidas[i] ? "x" : " ";
                char prioridade = prioridades[i];
                String descricao = descricoes[i];

                System.out.printf(" %02d   | [%s] | %c | %s\n", 
                                  (i + 1), status, prioridade, descricao);
            }
        }
    }

    public static void marcarTarefaComoConcluida() {
        listarTodasAsTarefas("PENDENTES");

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
        
        if (concluidas[indice]) {
            System.out.println("A Tarefa " + numeroTarefa + " já está concluída.");
            return;
        }

        concluidas[indice] = true;
        System.out.println("Tarefa " + numeroTarefa + " marcada como concluída!");
    }
    
    public static void limparListaTarefas() {
        if (totalTarefas == 0) {
            System.out.println("A lista já está vazia.");
            return;
        }
        
        System.out.print("ATENÇÃO: Deseja realmente remover TODAS as " + totalTarefas + " tarefas? (S/N): ");
        String confirmacao = scanner.nextLine().toUpperCase();
        
        if (confirmacao.equals("S")) {
            totalTarefas = 0; 
            descricoes[0] = null; 
            concluidas[0] = false;
            prioridades[0] = ' ';
            
            System.out.println("Toda a lista de tarefas foi limpa com sucesso!");
        } else {
            System.out.println("Operação de limpeza cancelada.");
        }
    }

    public static void alterarDadosTarefa() {
        listarTodasAsTarefas("TODAS");

        if (totalTarefas == 0) {
            System.out.println("Não há tarefas para alterar.");
            return;
        }

        System.out.print("Digite o número da tarefa que deseja alterar: ");
        
        int numeroTarefa = scanner.nextInt();
        scanner.nextLine();

        int indice = numeroTarefa - 1;

        if (indice < 0 || indice >= totalTarefas) {
            System.out.println("Erro: Número da tarefa inválido.");
            return;
        }

        boolean dadosAlterados = false;
        
        System.out.println("\n--- Alterar Tarefa " + numeroTarefa + " ---");
        System.out.println("1. Descrição (Atual: " + descricoes[indice] + ")");
        System.out.println("2. Prioridade (Atual: " + prioridades[indice] + ")");
        System.out.println("3. Status (Concluída/Pendente) (Atual: " + (concluidas[indice] ? "Concluída" : "Pendente") + ")");
        System.out.println("0. Cancelar");
        System.out.print("Escolha o dado para alterar: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1:
                System.out.print("Nova Descrição: ");
                descricoes[indice] = scanner.nextLine();
                dadosAlterados = true;
                break;
            case 2:
                System.out.print("Nova Prioridade (A, M, B): ");
                prioridades[indice] = scanner.nextLine().toUpperCase().charAt(0);
                dadosAlterados = true;
                break;
            case 3:
                System.out.print("Marcar como Concluída (S/N)? (Atual: " + (concluidas[indice] ? "S" : "N") + "): ");
                String status = scanner.nextLine().toUpperCase();
                concluidas[indice] = status.equals("S");
                dadosAlterados = true;
                break;
            case 0:
                System.out.println("Alteração cancelada.");
                break;
            default:
                System.out.println("Opção inválida. Nenhuma alteração realizada.");
        }
        
        if (dadosAlterados) {
            System.out.println("Dados da Tarefa " + numeroTarefa + " alterados com sucesso!");
        }
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
        System.out.printf("Total de Tarefas:       %d (Capacidade Máx: %d)\n", totalTarefas, CAPACIDADE_ATUAL);
        System.out.printf("Tarefas Concluídas:     %d\n", tarefasConcluidas);
        System.out.printf("Tarefas Pendentes:      %d\n", tarefasPendentes);
        System.out.printf("Percentual Concluído:   %.0f%%\n", percentualConcluido);
        System.out.println("==================================");
    }
}
