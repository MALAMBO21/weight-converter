package my.company.id.workspace;
import java.util.Random;
import java.util.Scanner;

public class Workspace {
    
    public static void main(String[] args) {
         
        //DICE ROLLING GAME
        
        Random random = new Random();
         Scanner scanner = new Scanner(System.in);
         
         int numOfDice;
         int total = 0;
         
         System.out.println("Enter # of dice to roll: ");
         numOfDice = scanner.nextInt();
         
         if(numOfDice > 0){
              
            for(int i = 0; i < numOfDice; i++){
                 int roll = random.nextInt(1, 7);
                 printDie(roll);
                 System.out.println("You rolled: " + roll);
                 total += roll;
             }
             System.out.println("Total: " + total);
         }
         else{
             System.out.println("# of dice must be greater than 0");
         }
        scanner.close();
    }
    static void printDie(int roll){
        
        String die1 = """
                      -------
                     |       |
                     |   o   |
                     |       |
                      -------
                """;
        String die2 = """
                      -------
                     |o     |
                     |      |
                     |    o |
                      -------
                """;
         String die3 = """
                      -------
                     |o      |
                     |   o   |
                     |     o |
                      -------
                """;
          String die4 = """
                      -------
                     |o     o|
                     |       |
                     |o     o|
                      -------
                """;
           String die5 = """
                      -------
                     |o     o|
                     |   o   |
                     |o     o|
                      -------
                """;
            String die6 = """
                      -------
                     |o     o|
                     |o     o|
                     |o     o|
                      -------
                """;
    
        switch(roll){
        case 1 -> System.out.print(die1);
        case 2 -> System.out.print(die2);
        case 3 -> System.out.print(die3);
        case 4 -> System.out.print(die4);
        case 5 -> System.out.print(die5);
        case 6 -> System.out.print(die6);
        }
    }
}
             
         
    

            
  
   
          
        
       

      
             
             
             
            
        