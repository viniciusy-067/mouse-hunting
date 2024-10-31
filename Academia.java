import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

abstract class Pessoa {
    protected String nome;
    protected int idade;

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public abstract void exibirInformacoes();
}

class Aluno extends Pessoa {
    private Nivel nivel;
    private double peso;
    private double altura;
    private boolean mensalidadePaga;
    private File foto;

    public Aluno(String nome, int idade, Nivel nivel, double peso, double altura, File foto) {
        super(nome, idade);
        this.nivel = nivel;
        this.peso = peso;
        this.altura = altura;
        this.mensalidadePaga = false;
        this.foto = foto;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public boolean isMensalidadePaga() {
        return mensalidadePaga;
    }

    public void pagarMensalidade() {
        this.mensalidadePaga = true;
    }

    public void barrarAluno() {
        this.mensalidadePaga = false;
    }

    public double calcularIMC() {
        return peso / (altura * altura);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Aluno: " + nome + ", Idade: " + idade + ", Nível: " + nivel + ", IMC: " + calcularIMC());
    }

    public File getFoto() {
        return foto;
    }
}

class Instrutor extends Pessoa {
    private String especialidade;

    public Instrutor(String nome, int idade, String especialidade) {
        super(nome, idade);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("Instrutor: " + nome + ", Idade: " + idade + ", Especialidade: " + especialidade);
    }
}

class Treino {
    private int numero;
    private String nome;
    private Instrutor instrutor;

    public Treino(int numero, String nome, Instrutor instrutor) {
        this.numero = numero;
        this.nome = nome;
        this.instrutor = instrutor;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }
}

class Equipamento {
    private String nome;
    private List<Instrutor> melhoresInstrutores;

    public Equipamento(String nome) {
        this.nome = nome;
        this.melhoresInstrutores = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Instrutor> getMelhoresInstrutores() {
        return melhoresInstrutores;
    }

    public void adicionarInstrutor(Instrutor instrutor) {
        if (!melhoresInstrutores.contains(instrutor)) {
            melhoresInstrutores.add(instrutor);
        }
    }
}

enum Nivel {
    INICIANTE, INTERMEDIARIO, AVANCADO;
}

interface Loggable {
    void registrarEvento(String evento);
}

class Log implements Loggable {
    private static final String LOG_FILE = "log.txt";

    @Override
    public void registrarEvento(String evento) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(new Date() + ": " + evento);
        } catch (IOException e) {
            System.err.println("Erro ao registrar evento no log: " + e.getMessage());
        }
    }
}

class FileManager {
    private static Log log = new Log();

    public static void salvar(String evento) {
        log.registrarEvento(evento);
    }
}

class AlunoController {
    private List<Aluno> alunos = new ArrayList<>();

    public void adicionarAluno(String nome, int idade, Nivel nivel, double peso, double altura, File foto) {
        Aluno aluno = new Aluno(nome, idade, nivel, peso, altura, foto);
        alunos.add(aluno);
        FileManager.salvar("Aluno adicionado: " + aluno.getNome());
    }

    public void removerAluno(String nome) {
        alunos.removeIf(aluno -> aluno.getNome().equalsIgnoreCase(nome));
        FileManager.salvar("Aluno removido: " + nome);
    }

    public Aluno pesquisarAluno(String nome) {
        for (Aluno aluno : alunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                return aluno;
            }
        }
        return null;
    }

    public List<Aluno> listarAlunos() {
        return new ArrayList<>(alunos);
    }
}

class InstrutorController {
    private List<Instrutor> instrutores = new ArrayList<>();

    public void adicionarInstrutor(String nome, int idade, String especialidade) {
        Instrutor instrutor = new Instrutor(nome, idade, especialidade);
        instrutores.add(instrutor);
        FileManager.salvar("Instrutor adicionado: " + instrutor.getNome());
    }

    public void removerInstrutor(String nome) {
        instrutores.removeIf(instrutor -> instrutor.getNome().equalsIgnoreCase(nome));
        FileManager.salvar("Instrutor removido: " + nome);
    }

    public Instrutor pesquisarInstrutor(String nome) {
        for (Instrutor instrutor : instrutores) {
            if (instrutor.getNome().equalsIgnoreCase(nome)) {
                return instrutor;
            }
        }
        return null;
    }

    public List<Instrutor> listarInstrutores() {
        return new ArrayList<>(instrutores);
    }
}

class TreinoController {
    private List<Treino> treinos = new ArrayList<>();
    private InstrutorController instrutorController;
    private int contadorTreinos = 1;

    public TreinoController(InstrutorController instrutorController) {
        this.instrutorController = instrutorController;
    }

    public void adicionarTreino(String nomeTreino, String nomeInstrutor) {
        Instrutor instrutor = instrutorController.pesquisarInstrutor(nomeInstrutor);
        if (instrutor != null) {
            Treino treino = new Treino(contadorTreinos++, nomeTreino, instrutor);
            treinos.add(treino);
            FileManager.salvar("Treino adicionado: " + treino.getNome());
        } else {
            System.out.println("Instrutor não encontrado.");
        }
    }

    public void removerTreino(int numero) {
        treinos.removeIf(treino -> treino.getNumero() == numero);
        FileManager.salvar("Treino removido: " + numero);
    }

    public Treino pesquisarTreino(String nome) {
        for (Treino treino : treinos) {
            if (treino.getNome().equalsIgnoreCase(nome)) {
                return treino;
            }
        }
        return null;
    }

    public List<Treino> listarTreinos() {
        return new ArrayList<>(treinos);
    }
}

class EquipamentoController {
    private List<Equipamento> equipamentos = new ArrayList<>();
    private InstrutorController instrutorController;

    public EquipamentoController(InstrutorController instrutorController) {
        this.instrutorController = instrutorController;
    }

    public void adicionarEquipamento(String nome) {
        Equipamento equipamento = new Equipamento(nome);
        equipamentos.add(equipamento);
        FileManager.salvar("Equipamento adicionado: " + nome);
    }

    public void adicionarInstrutorAoEquipamento(String nomeEquipamento, String nomeInstrutor) {
        Equipamento equipamento = pesquisarEquipamento(nomeEquipamento);
        Instrutor instrutor = instrutorController.pesquisarInstrutor(nomeInstrutor);

        if (equipamento != null && instrutor != null) {
            equipamento.adicionarInstrutor(instrutor);
            FileManager.salvar("Instrutor " + instrutor.getNome() + " associado ao equipamento " + equipamento.getNome());
        } else {
            System.out.println("Equipamento ou Instrutor não encontrado.");
        }
    }

    public Equipamento pesquisarEquipamento(String nome) {
        for (Equipamento equipamento : equipamentos) {
            if (equipamento.getNome().equalsIgnoreCase(nome)) {
                return equipamento;
            }
        }
        return null;
    }

    public List<Equipamento> listarEquipamentos() {
        return new ArrayList<>(equipamentos);
    }
}

public class Academia {
    static Scanner scanner = new Scanner(System.in);
    static AlunoController alunoController = new AlunoController();
    static InstrutorController instrutorController = new InstrutorController();
    static TreinoController treinoController = new TreinoController(instrutorController);
    static EquipamentoController equipamentoController = new EquipamentoController(instrutorController);

    public static void main(String[] args) {
        while (true) {
            exibirMenuPrincipal();
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 0:
                    System.out.println("Encerrando o sistema...");
                    return;
                case 1:
                    gerenciarAlunos();
                    break;
                case 2:
                    gerenciarInstrutores();
                    break;
                case 3:
                    gerenciarTreinos();
                    break;
                case 4:
                    gerenciarEquipamentos();
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    static void exibirMenuPrincipal() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Gerenciar Alunos");
        System.out.println("2. Gerenciar Instrutores");
        System.out.println("3. Gerenciar Treinos");
        System.out.println("4. Gerenciar Equipamentos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    static void gerenciarAlunos() {
        System.out.println("\n===== GERENCIAR ALUNOS =====");
        System.out.println("1. Adicionar Aluno");
        System.out.println("2. Remover Aluno");
        System.out.println("3. Pesquisar Aluno");
        System.out.println("4. Listar Todos os Alunos");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 0:
                return;
            case 1:
                adicionarAluno();
                break;
            case 2:
                removerAluno();
                break;
            case 3:
                pesquisarAluno();
                break;
            case 4:
                listarAlunos();
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    static void gerenciarInstrutores() {
        System.out.println("\n===== GERENCIAR INSTRUTORES =====");
        System.out.println("1. Adicionar Instrutor");
        System.out.println("2. Remover Instrutor");
        System.out.println("3. Pesquisar Instrutor");
        System.out.println("4. Listar Todos os Instrutores");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 0:
                return;
            case 1:
                adicionarInstrutor();
                break;
            case 2:
                removerInstrutor();
                break;
            case 3:
                pesquisarInstrutor();
                break;
            case 4:
                listarInstrutores();
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    static void gerenciarTreinos() {
        System.out.println("\n===== GERENCIAR TREINOS =====");
        System.out.println("1. Adicionar Treino");
        System.out.println("2. Remover Treino");
        System.out.println("3. Pesquisar Treino");
        System.out.println("4. Listar Todos os Treinos");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 0:
                return;
            case 1:
                adicionarTreino();
                break;
            case 2:
                removerTreino();
                break;
            case 3:
                pesquisarTreino();
                break;
            case 4:
                listarTreinos();
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    static void gerenciarEquipamentos() {
        System.out.println("\n===== GERENCIAR EQUIPAMENTOS =====");
        System.out.println("1. Adicionar Equipamento");
        System.out.println("2. Adicionar Instrutor ao Equipamento");
        System.out.println("3. Listar Todos os Equipamentos");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 0:
                return;
            case 1:
                adicionarEquipamento();
                break;
            case 2:
                adicionarInstrutorAoEquipamento();
                break;
            case 3:
                listarEquipamentos();
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    }

    static void adicionarAluno() {
        System.out.println("\n===== ADICIONAR ALUNO =====");
        System.out.print("Digite o nome do novo aluno: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a idade do aluno: ");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o nível do aluno (INICIANTE, INTERMEDIARIO, AVANCADO): ");
        String nivelString = scanner.nextLine().toUpperCase();
        Nivel nivel;
        try {
            nivel = Nivel.valueOf(nivelString);
        } catch (IllegalArgumentException e) {
            System.out.println("Nível inválido. O aluno será cadastrado como INICIANTE.");
            nivel = Nivel.INICIANTE;
        }

        System.out.print("Digite o peso do aluno (kg): ");
        double peso = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite a altura do aluno (m): ");
        double altura = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Digite o caminho para a foto do aluno: ");
        String caminhoFoto = scanner.nextLine();
        File foto = new File(caminhoFoto);

        alunoController.adicionarAluno(nome, idade, nivel, peso, altura, foto);
    }

    static void removerAluno() {
        System.out.println("\n===== REMOVER ALUNO =====");
        System.out.print("Digite o nome do aluno que deseja remover: ");
        String nome = scanner.nextLine();

        alunoController.removerAluno(nome);
    }

    static void pesquisarAluno() {
        System.out.println("\n===== PESQUISAR ALUNO =====");
        System.out.print("Digite o nome do aluno que deseja pesquisar: ");
        String nome = scanner.nextLine();

        Aluno aluno = alunoController.pesquisarAluno(nome);
        if (aluno != null) {
            aluno.exibirInformacoes();
            if (aluno.getFoto() != null) {
                System.out.println("Foto do aluno: " + aluno.getFoto().getPath());
            }
        } else {
            System.out.println("Nenhum aluno encontrado com esse nome.");
        }
    }

    static void listarAlunos() {
        System.out.println("\n===== LISTAR TODOS OS ALUNOS =====");
        List<Aluno> alunos = alunoController.listarAlunos();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado ainda.");
        } else {
            for (Aluno aluno : alunos) {
                aluno.exibirInformacoes();
            }
        }
    }

    static void adicionarInstrutor() {
        System.out.println("\n===== ADICIONAR INSTRUTOR =====");
        System.out.print("Digite o nome do novo instrutor: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a idade do instrutor: ");
        int idade = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite a especialidade do instrutor: ");
        String especialidade = scanner.nextLine();

        instrutorController.adicionarInstrutor(nome, idade, especialidade);
    }

    static void removerInstrutor() {
        System.out.println("\n===== REMOVER INSTRUTOR =====");
        System.out.print("Digite o nome do instrutor que deseja remover: ");
        String nome = scanner.nextLine();

        instrutorController.removerInstrutor(nome);
    }

    static void pesquisarInstrutor() {
        System.out.println("\n===== PESQUISAR INSTRUTOR =====");
        System.out.print("Digite o nome do instrutor que deseja pesquisar: ");
        String nome = scanner.nextLine();

        Instrutor instrutor = instrutorController.pesquisarInstrutor(nome);
        if (instrutor != null) {
            instrutor.exibirInformacoes();
        } else {
            System.out.println("Nenhum instrutor encontrado com esse nome.");
        }
    }

    static void listarInstrutores() {
        System.out.println("\n===== LISTAR TODOS OS INSTRUTORES =====");
        List<Instrutor> instrutores = instrutorController.listarInstrutores();
        if (instrutores.isEmpty()) {
            System.out.println("Nenhum instrutor cadastrado ainda.");
        } else {
            for (Instrutor instrutor : instrutores) {
                instrutor.exibirInformacoes();
            }
        }
    }

    static void adicionarTreino() {
        System.out.println("\n===== ADICIONAR TREINO =====");
        System.out.print("Digite o nome do treino: ");
        String nomeTreino = scanner.nextLine();

        System.out.print("Digite o nome do instrutor responsável: ");
        String nomeInstrutor = scanner.nextLine();

        treinoController.adicionarTreino(nomeTreino, nomeInstrutor);
    }

    static void removerTreino() {
        System.out.println("\n===== REMOVER TREINO =====");
        System.out.print("Digite o número do treino que deseja remover: ");
        int numero = scanner.nextInt();
        scanner.nextLine();

        treinoController.removerTreino(numero);
    }

    static void pesquisarTreino() {
        System.out.println("\n===== PESQUISAR TREINO =====");
        System.out.print("Digite o nome do treino que deseja pesquisar: ");
        String nome = scanner.nextLine();

        Treino treino = treinoController.pesquisarTreino(nome);
        if (treino != null) {
            System.out.println("Treino encontrado: " + treino.getNome());
            System.out.println("Instrutor responsável: " + treino.getInstrutor().getNome());
        } else {
            System.out.println("Nenhum treino encontrado com esse nome.");
        }
    }

    static void listarTreinos() {
        System.out.println("\n===== LISTAR TODOS OS TREINOS =====");
        List<Treino> treinos = treinoController.listarTreinos();
        if (treinos.isEmpty()) {
            System.out.println("Nenhum treino cadastrado ainda.");
        } else {
            for (Treino treino : treinos) {
                System.out.println("[" + treino.getNumero() + "] " + treino.getNome());
            }
        }
    }

    static void adicionarEquipamento() {
        System.out.println("\n===== ADICIONAR EQUIPAMENTO =====");
        System.out.print("Digite o nome do novo equipamento: ");
        String nome = scanner.nextLine();

        equipamentoController.adicionarEquipamento(nome);
    }

    static void adicionarInstrutorAoEquipamento() {
        System.out.println("\n===== ADICIONAR INSTRUTOR AO EQUIPAMENTO =====");
        System.out.print("Digite o nome do equipamento: ");
        String nomeEquipamento = scanner.nextLine();

        System.out.print("Digite o nome do instrutor: ");
        String nomeInstrutor = scanner.nextLine();

        equipamentoController.adicionarInstrutorAoEquipamento(nomeEquipamento, nomeInstrutor);
    }

    static void listarEquipamentos() {
        System.out.println("\n===== LISTAR TODOS OS EQUIPAMENTOS =====");
        List<Equipamento> equipamentos = equipamentoController.listarEquipamentos();
        if (equipamentos.isEmpty()) {
            System.out.println("Nenhum equipamento cadastrado ainda.");
        } else {
            for (Equipamento equipamento : equipamentos) {
                System.out.println("Equipamento: " + equipamento.getNome());
                System.out.println("Melhores instrutores:");
                for (Instrutor instrutor : equipamento.getMelhoresInstrutores()) {
                    System.out.println("- " + instrutor.getNome());
                }
            }
        }
    }
}



















//proxima aula 018//