import java.io.Serializable;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import static java.lang.System.out;

/**
*
**/

public class Menu implements Serializable
{
  private List<String> options;
  private int op; //option
  private int chosenMenu;
  
  public Menu(String[] options)
  {
      this.options = Arrays.asList(options); //prints prettier
      this.op = 0;
  }
  
  //runs home menu
  public void rHome()
  {
    this.chosenMenu = 1;
    do
    {
        showMenu();
        this.op = readOption();
    }while(this.op == -1);
  }
  
  //run cliente menu
  public void rClientMenu() 
  {
     this.chosenMenu = 2;
     do 
     {
        showMenu();
        this.op = readOption();
     } while (this.op == -1);
  }
  
  public void rOwnerMenu() 
  {
     this.chosenMenu = 3;
     do 
     {
        showMenu();
        this.op = readOption();
     } while (this.op == -1);
  }

  public void rSignUpMenu() 
  {
     this.chosenMenu = 4;
     do 
     {
        showMenu();
        this.op = readOption();
     } while (this.op == -1);
  }
  
  public void rRefuelMenu()
  {
      this.chosenMenu = 5;
      do 
      {
         showMenu();
         this.op = readOption();
      } while (this.op == -1);
  }
  
  public void rSignUpVehicleMenu() 
  {
      this.chosenMenu = 6;
      do 
      {
         showMenu();
         this.op = readOption();
      } while (this.op == -1);
   }
  
  public void rSpecificVehicleMenu() 
  {
      this.chosenMenu = 7;
      do 
      {
         showMenu();
         this.op = readOption();
      } while (this.op == -1);
  }
  
  
  
  
  
  
  
  //Apresentar Menu
  private void showMenu()
  {
      switch(this.chosenMenu)
      {
          case 1: System.out.println("********* Bem-vindo à UMCarrojá! *********\n");
          for(int i = 0; i<this.options.size(); i++)
          {
              out.print((i+1));
              out.print(" - ");
              out.println(this.options.get(i));
          }
          out.println("\nClique 0 para sair");
          out.print("******************************************");
          break;
          
          case 2: System.out.println("\nCliente");
          for (int i=0; i<this.options.size(); i++) {
              System.out.print((i+1));
              System.out.print(" - ");
              System.out.println(this.options.get(i));
          }
          System.out.println("\nClique 0 para sair");
          break;
          
          case 3: System.out.println("\nProprietário");
          for (int i=0; i<this.options.size(); i++) {
              System.out.print((i+1));
              System.out.print(" - ");
              System.out.println(this.options.get(i));
          }
          out.println("\nClique 0 para sair");
          break;
          
          case 4:out.println("\n Efetuar Registo");
          for (int i=0; i<this.options.size(); i++) {
              System.out.print((i+1));
              System.out.print(" - ");
              System.out.println(this.options.get(i));
          }
          out.println( "\nClique 0 para sair");
          break;
          
          
      }
  }
  
  //ler opção valida
  private int readOption()
  {
     int op;
     Scanner input = new Scanner(System.in);
     System.out.print("\nOpção: ");
     
     try {op = input.nextInt();}
     catch (InputMismatchException e) {op = -1;} //Não foi escrito um int
     if (op<0 || op>this.options.size()) 
     {
            System.out.println("\nOpção Inválida!");
            op = -1;
     }

     return op;
  }
  
  
  //obter a ultima opção lida
  public int getOption()
  {
      return this.op;
  }
}
