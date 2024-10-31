import java.util.Scanner;



public class hello{
    public static void main(String args[]){
        Scanner leitor = new Scanner(System.in);
        
        System.out.println("Digite uma palavra para teste");
        String nome = leitor.nextLine();

        System.out.println("a palvra digitada foi a :");
        System.out.println(nome);


        leitor.close();

    }
}