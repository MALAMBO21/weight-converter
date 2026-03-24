
package my.company.id.bankprogram;
import java.util.Scanner;

public class Bankprogram {

    public static void main(String[] args) {
       
        Scanner scanner = new Scanner(System.in);
        
        //BANKING PROGRAM 
        
        // DECLAIR VARIABLES
        
        double balance;
        boolean isRunning = true;
        int choice;  
        
        
        // DISPLAY MENU
        while(isRunning){
              System.out.println("*****************");
        System.out.println("BANKING PROGRAM");
        System.out.println("******************");
        System.out.println("1. Show balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        
        // GET AND PROCESS USER CHOICE
        
        System.out.println("Enter your choice(1-4): ");
        choice = scanner.nextInt();
        
        switch(choice){
            case 1 -> System.out.println("SHOW BALANCE");
            case 2 -> System.out.println("DEPOSIT");
            case 3 -> System.out.println("WITHDRAW");
            case 4 -> isRunning = false; 
            default -> System.out.println("INVALID CHOICE");
        }
        
    }
    
        
        //SHOW BALANCE
        
        // DEPOSIT
        
        //WITHDRAW
        
        //EXIT MESSAGE
       
        scanner.close();
    }
    
    static void showBalance(double balance){
        System.out.printf("$%f", balance);
    
  }
}
