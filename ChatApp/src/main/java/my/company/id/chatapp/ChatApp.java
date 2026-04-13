
package my.company.id.chatapp;
 import java.util.Scanner;

public class ChatApp {

 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========== WELCOME TO QUICKCHAT ==========");

        // Step 1: Registration — must complete successfully before anything else
        Registration reg = Registration.registerWithRetry(scanner);

        // Step 2: Login — must succeed before the message system is accessible
        Login login = new Login(reg.getUsername(), reg.getPassword());
        boolean loggedIn = login.loginWithRetry(scanner);

        // Step 3: Only open the message system if login was successful
        if (loggedIn) {
            System.out.println("\nAccess granted. Welcome to the Message System, " + reg.getUsername() + "!");
            runMessageSystem(scanner);
        } else {
            System.out.println("\nAccess denied. Please contact support to unlock your account.");
            System.out.println("The message system is unavailable until you log in successfully.");
        }

        scanner.close();
    }

    // ==============================
    // PART 2: Message System
    // (Only reached after successful registration AND login)
    // ==============================
    private static void runMessageSystem(Scanner scanner) {
        MessageManager manager = new MessageManager();
        boolean running = true;

        while (running) {
            System.out.println("\n------- WELCOME TO QUICKCHAT ----------");
            System.out.println("\n--- MESSAGE MENU ---");
            System.out.println("1. Send / Store / Disregard a message");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Display longest sent message");
            System.out.println("4. Search by Message ID");
            System.out.println("5. Search by Recipient");
            System.out.println("6. Delete message by hash");
            System.out.println("7. Display report");
            System.out.println("8. Total messages sent");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    System.out.print("Enter recipient number: ");
                    String recipient = scanner.nextLine();
                    System.out.print("Enter message (max 250 chars): ");
                    String text = scanner.nextLine();
                    int msgNum = Message.returnTotalMessages() + 1;
                    Message msg = new Message(msgNum, recipient, text);

                    System.out.println("1. Send  2. Store  3. Disregard");
                    System.out.print("Choice: ");
                    int action = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println(manager.processMessage(msg, action));
                    break;
                case 2:
                   System.out.println("Coming soon");
                   break;
                case 3:
                    System.out.println(manager.displayLongestMessage());
                    break;
                case 4:
                    System.out.print("Enter Message ID: ");
                    System.out.println(manager.searchByMessageId(scanner.nextLine()));
                    break;
                case 5:
                    System.out.print("Enter recipient number: ");
                    System.out.println(manager.searchByRecipient(scanner.nextLine()));
                    break;
                case 6:
                    System.out.print("Enter message hash: ");
                    System.out.println(manager.deleteMessage(scanner.nextLine()));
                    break;
                case 7:
                    System.out.println(manager.displayReport());
                    break;
                case 8:
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
            
     
  


