
package my.company.id.icetask2;

import java.util.Scanner;

public class IceTask2 {

    public static void main(String[] args) {
        
        //Temperature converter
        
        
        Scanner scanner = new Scanner(System.in);
        
        String name;
        double temp;
        double newTemp;
        String unit;
        
        
        System.out.println("Enter your name: ");
        name = scanner.nextLine();
        System.out.println("Hello " + name + "!");
        
        System.out.print("Enter the temperature:");
        temp = scanner.nextDouble();
        
        System.out.print("Convert to Celcius or Fahrenheit? (C or F): ");
        unit = scanner.next().toUpperCase();
        
        //(condition) ? true : false
       
        newTemp = (unit.equals("C")) ? (temp - 32) * 5 / 9 : (temp * 5 / 9) + 32;
        
        System.out.printf("The temperature is %.2f", newTemp);
        
                
                
                                
        scanner.close();
    }
}
