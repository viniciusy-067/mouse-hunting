import java.util.Scanner;

public class Calculadora{
    public static void main (String args[]){
    Scanner leitor = new Scanner(System.in);

    



     System.out.println("=======Bem vindo a calculadora======");
     System.out.println("Vamos para os cálculos:");



      System.out.println("Digite o primeiro número");
       int num1=leitor.nextInt();


        System.out.println("Digite o próximo numero");
        int num2=leitor.nextInt();


         System.out.println("Escolha o operador para fazer a sua conta : ");
          System.out.println("Soma,Subtração,Multiplição ou Divisão");
           System.out.println(" (+) -  (-)  - (*)  - (%)  ");
            String caso = leitor.next();
             int resultado =0;



        switch (caso){
            case "soma":
            resultado = num1 + num2;
            System.out.println("A soma é  " + resultado );
            break;

             case "subtracao":
             resultado = num1 -num2;
             System.out.println("A subtração é" + resultado );
             break;

              case "multiplicao":
              resultado = num1 * num2;
              System.out.println("A multiplição é " + resultado );
              break;
            
               case "divisao":
               resultado = num1 % num2;
               System.out.println("A divisão é " + resultado);
               break;

                default:
                System.out.println("Digite algo válido por favor");
        }
    }
}