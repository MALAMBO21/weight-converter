

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
           
         
                      
        scanner.close();
    }
 public class Login{
     
//Method 1: Check Username
     public boolean checkUserName(String username){
         if(username == null || username.length() > 5){
             return false;
         }
         return username.contains("_");
     }
     //Method 2 Check Password Complexity
     public boolean checkPasswordComplexity(String password){
         if(password == null || password.length() < 8){
             return false;
         }
         return true;
     }
     //Method 3: Check Cell Phone number
     public boolean checkCellPhoneNumber(String cellPhoneNumber){
         if(cellPhoneNumber == null){
             return false;
         }
         String regex = "\\+27\\d{9}$";
         return cellPhoneNumber.matches(regex);
     }
     //Method 4: Register User
     public String registerUser(String username, String password, String cellPhoneNumber){
         if(!checkUserName(username)){
             return "Username is incorrectly formatted, ysername must contain an underscore(_) and no more than 25 characters.";
         }
         if(!checkPasswordComplexity(password)){
             return "Password does not meet the complexity requirements, password must contain atleast 8 characters long, contain a captuatl letter, a number, and a special character.";
         }
         if(!checkCellPhoneNumber(cellPhoneNumber)){
             return "Cellphone is incorrectly formatted, it must in international format(e.g., +27XXXXXXXXX).";     
         }
         //if all checks pass
         return "The two above conditions have been met, and the user has been registered successfully.";
     }
     //Method 5: Login User;
     public boolean loginUser(String Username, String password){
         String storedUsername = "main_21";
         String storedPassword = "Password123!";
         
         return Username.equals(storedUsername) && password.equals(storedPassword);
     }
     //Method 6: Return Login Status
     public String returnLoginStatus(boolean loginSuccessful){
         if(loginSuccessful){
             return "A successful login";
         }else{
             return "A failed login";
         }
     }
 }
   }
