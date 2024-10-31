import java.util.Random;
import java.util.Scanner;

public class AdivinheONumero {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int numeroAleatorio = random.nextInt(100) + 1;
        int tentativa = 0;
        int numeroTentativas = 0;

        System.out.println("Bem-vindo ao jogo 'Adivinhe o Número'!");
        System.out.println("Tente adivinhar o número entre 1 e 100.");

        while (tentativa != numeroAleatorio) {
            System.out.print("Digite sua tentativa: ");
            tentativa = scanner.nextInt();
            numeroTentativas++;

            if (tentativa < numeroAleatorio) {
                System.out.println("Muito baixo! Tente um número maior.");
            } else if (tentativa > numeroAleatorio) {
                System.out.println("Muito alto! Tente um número menor.");
            } else {
                System.out.println("Parabéns! Você acertou o número em " + numeroTentativas + " tentativas.");
            }
        }

        scanner.close();
    }
}