

package my.company.id.brocode;

import java.util.Scanner;

public class BroCode {

    public static void main(String[] args) {
      
       //Weight Converter Program
       
       Scanner scanner = new Scanner(System.in);
       
       double weight;
       double newWeight;
       int choice;
       
       System.out.println("Weight conversion program");
        System.out.println("1: Convert lbs to kgs");
         System.out.println("2: Convert kgs to lbs");
         
         //Prompt user choice
         
          System.out.println("Choose an option:");
          choice = scanner.nextInt();
          
          //Option 1 Convert lbs to kgs
          
          if(choice == 1){
               System.out.println("Enter your weight in lbs: :");
               weight = scanner.nextDouble();
               newWeight = weight * 0.7542;
                System.out.println("Your weight is: " + newWeight);
                
                //Option 2 Convert kgs to lbs
                
                if(choice == 2){
                     System.out.println("Enter your weight n kgs: ");
                     weight = scanner.nextDouble();
                     newWeight = weight / 0.7542;
                     
                }
               
          }
    
 scanner.close();
}
}
        
