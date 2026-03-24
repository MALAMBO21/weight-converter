
package my.company.id.bankprogram;
import java.util.Scanner;

public class Bankprogram {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
       
        Scanner scanner = new Scanner(System.in);
        
        //BANKING PROGRAM 
        
        // DECLAIR VARIABLES
        
        double balance = 0;
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
            case 1 -> System.out.println(balance);
            case 2 -> balance += deposit();
            case 3 -> balance -= withdraw(balance);
            case 4 -> isRunning = false; 
            default -> System.out.println("INVALID CHOICE");
        }
        
    }
  
       
     System.out.println("**********************");
     System.out.println("Thank you! Have a nice day!");
     System.out.println("**********************");
       
        scanner.close();
    }
    
    static void showBalance(double balance){
        
        //SHOW BALANCE
         System.out.println("**************");
        System.out.printf("R%.2f\n", balance);
    
  }
    static double deposit(){
        
        double amount;
        
         System.out.println("Enter amount to be deposited");
          amount = scanner.nextDouble();
          
          if(amount < 0){
              System.out.println("Amount cannot be negative");
              return 0;
          }
          else{
              return amount;
          }
         
               
    }
    static double withdraw(double balance){
        
        double amount;
        
         System.out.print("Enter amount to be withdrawn: ");
         amount = scanner.nextDouble();
         
         if(amount > balance){
              System.out.println("INSUFFICIENT FUNDS");
              return 0;
         }
         else if( amount < 0){
              System.out.println("Amount can't be negative");
              return 0;
         }
         else{
             return amount;
         }
      
    }
}

