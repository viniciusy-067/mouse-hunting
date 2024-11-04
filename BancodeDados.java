import java.util.ArrayList;
import java.util.Scanner;

class Aluno {
    private String nome;
    private int id;

    public Aluno(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
}

class Professor {
    private String nome;
    private int id;

    public Professor(String nome, int id) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }
}

class BancoDeDados {
    static ArrayList<Aluno> listaAlunos = new ArrayList<>();
    static ArrayList<Professor> listaProfessores = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            exibirMenu();
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "01":
                    verListaCompleta();
                    break;
                case "02":
                    listarAlunos();
                    break;
                case "03":
                    listarProfessores();
                    break;
                case "04":
                    adicionarAluno();
                    break;
                case "05":
                    removerAluno();
                    break;
                case "06":
                    adicionarProfessor();
                    break;
                case "07":
                    removerProfessor();
                    break;
                case "08":
                    System.out.println("Saindo do programa...");
                    return;
                default:
                    System.out.println("A opção selecionada está inválida, por favor tente novamente.");
                    break;
            }
        }
    }

    // Função para exibir o menu de opções
    public static void exibirMenu() {
        System.out.println("--  Bem vindo ao banco de dados de alunos/professores  --");
        System.out.println("------------------------------------------------------");
        System.out.println("Selecione as opções a seguir:");
        System.out.println("(pode selecionar tanto por número de identificação quanto por nome da opção)");
        System.out.println("01 - Ver lista completa");
        System.out.println("02 - Lista Alunos");
        System.out.println("03 - Lista Professores");
        System.out.println("04 - Adicionar aluno");
        System.out.println("05 - Remover aluno");
        System.out.println("06 - Adicionar Professor");
        System.out.println("07 - Remover Professor");
        System.out.println("08 - Sair");
        System.out.println("------------------------------");
        System.out.print("Digite a opção selecionada: ");
    }

    public static void verListaCompleta() {
        listarAlunos();
        listarProfessores();
        pausaParaRetorno();
    }

    public static void listarAlunos() {
        System.out.println("\nLista de alunos:");
        if (listaAlunos.isEmpty()) {
            System.out.println("Nenhum aluno está cadastrado ainda.");
        } else {
            for (Aluno aluno : listaAlunos) {
                System.out.println("Aluno: " + aluno.getNome() + ", ID: " + aluno.getId());
            }
        }
    }

    public static void listarProfessores() {
        System.out.println("\nLista de professores:");
        if (listaProfessores.isEmpty()) {
            System.out.println("Nenhum professor foi cadastrado ainda.");
        } else {
            for (Professor professor : listaProfessores) {
                System.out.println("Professor: " + professor.getNome() + ", ID: " + professor.getId());
            }
        }
    }

    public static void adicionarAluno() {
        System.out.print("Digite o nome do aluno a ser cadastrado: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o ID desse aluno: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        Aluno aluno = new Aluno(nome, id);
        listaAlunos.add(aluno);
        System.out.println("Aluno foi cadastrado com sucesso!");
    }

    public static void removerAluno() {
        System.out.print("Digite o ID do aluno para o mesmo ser removido: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        listaAlunos.removeIf(aluno -> aluno.getId() == id);
        System.out.println("Aluno removido com sucesso!");
    }

    public static void adicionarProfessor() {
        System.out.print("Digite o nome do professor: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o ID do professor: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        Professor professor = new Professor(nome, id);
        listaProfessores.add(professor);
        System.out.println("Professor cadastrado com sucesso!");
    }

    public static void removerProfessor() {
        System.out.print("Digite o ID do professor a ser removido: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        listaProfessores.removeIf(professor -> professor.getId() == id);
        System.out.println("Professor removido com sucesso!");
    }

    // Função para pausa antes de voltar ao menu
    public static void pausaParaRetorno() {
        System.out.println("\nPressione Enter para voltar ao menu...");
        scanner.nextLine();
    }
}
