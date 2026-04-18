/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.company.id.smartaccesscontrol;
  import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Lenovo
 */
public class LoginAttemptLog extends UserManager {
   
/**
 * Records a single login attempt (successful or failed) with a timestamp.
 */


    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String username;
    private boolean success;
    private LocalDateTime timestamp;

    public LoginAttemptLog(String username, boolean success) {
        this.username  = username;
        this.success   = success;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %-15s -> %s",
                timestamp.format(FORMATTER),
                username,
                success ? "SUCCESS" : "FAILED");
    }
}

