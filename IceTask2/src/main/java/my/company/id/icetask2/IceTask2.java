
package my.company.id.icetask2;
import java.util.Scanner;

public class IceTask2 {

    public static void main(String[] args) {
        
        //Temperature conerter
        
        
        Scanner scanner = new Scanner(System.in);
        
        double temp;
        double newTemp;
        String unit;
        
        
        System.out.print("Enter the temperature:");
        temp = scanner.nextDouble();
        
        System.out.print("Convert to Celcius or Degrees?; ");
        unit = scanner.nextLine();
        
        
        
        
        
        scanner.close();
    }
}
