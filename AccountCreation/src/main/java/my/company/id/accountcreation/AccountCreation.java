

package my.company.id.accountcreation;
import java.util.Scanner;


public class AccountCreation {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
       
        // Username Validation
         System.out.println("\n--- Login ---");
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        if(username.matches(".*_.*") && username.length() <= 5){
          System.out.println("Username successfully captured.");  
        }else{
            System.out.println("Username not correctly formatted, please ensure username contains an underscore and no more than five characters in length.");
        }
        // Password Validation
        
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        if(password.length() >= 8 && password.matches("[A-Z]") && password.matches(".*@#!.*") && password.matches(".*[1-9].*")){
            System.out.println("Password captured successfully.");           
        }else{
            System.out.println("Password is not correctly formatted, please ensure password contains atleast eight characters, a capital leter, a number, and a special character.");
        }
        // Phone Number Validation
         System.out.println("Enter your phone number (+27...): ");
         String phoneNumber = scanner.nextLine();
         if(phoneNumber.length() <= 10){
              System.out.println("Cellphone number Successfully added.");
         }else{ 
              System.out.println("Cellphone number incorrectly formatted or does not contain international code.");
         }
            if(){
                 System.out.println("Welcome <user first name>,<user last name> it is great to see you again");
            }else{
                 System.out.println("Username or Passowrd incorrect, please try again.");
            }               
        scanner.close();
    }
 public class Login{
     
//Method 1: Check Username
     public boolean checkUserName(String username){
         if(username == null || username.length() > 25){
             return false;
         }
         return username.contains("_");
     }
     //Method 2 Check Password Complexity
 }
   }
