

package my.company.id.accountcreation;
import java.util.Scanner;


public class AccountCreation {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        
        // Username Validation
        
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
        
                           
        scanner.close();
    }
 
   }
