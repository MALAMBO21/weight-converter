
package my.company.id.smartaccesscontrol;
   import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User extends Smartaccesscontrol {
 

/**
 * Represents a user in the Smart Access Control System.
 */


    // Role constants
    public static final int ROLE_ADMIN   = 1;
    public static final int ROLE_STAFF   = 2;
    public static final int ROLE_VISITOR = 3;

    private String username;
    private String pin;
    private int    role;
    private LocalDateTime createdAt;

    // Login-attempt tracking
    private int     failedAttempts = 0;
    private boolean locked         = false;
    private LocalDateTime lockedAt = null;

    // Formatter shared across all instances
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public User(String username, String pin, int role) {
        this.username  = username;
        this.pin       = pin;
        this.role      = role;
        this.createdAt = LocalDateTime.now();
    }

    // -------------------------------------------------------------------------
    // Getters / Setters
    // -------------------------------------------------------------------------

    public String getUsername()  { return username; }
    public String getPin()       { return pin;      }
    public int    getRole()      { return role;     }
    public boolean isLocked()    { return locked;   }
    public int getFailedAttempts() { return failedAttempts; }

    public String getCreatedAtFormatted() {
        return createdAt.format(FORMATTER);
    }

    public String getRoleName() {
        switch (role) {
            case ROLE_ADMIN:   return "Admin";
            case ROLE_STAFF:   return "Staff";
            case ROLE_VISITOR: return "Visitor";
            default:           return "Unknown";
        }
    }

    // -------------------------------------------------------------------------
    // Login-attempt management
    // -------------------------------------------------------------------------

    /** Increments failed attempts and locks the account after 3 failures. */
    public void registerFailedAttempt() {
        failedAttempts++;
        if (failedAttempts >= 3) {
            locked   = true;
            lockedAt = LocalDateTime.now();
        }
    }

    /** Resets failed attempts and unlocks the account. */
    public void unlock() {
        failedAttempts = 0;
        locked         = false;
        lockedAt       = null;
    }

    /**
     * Returns true if the automatic 5-minute timeout has elapsed since
     * the account was locked.
     */
    public boolean isTimeoutExpired() {
        if (!locked || lockedAt == null) return false;
        return LocalDateTime.now().isAfter(lockedAt.plusMinutes(5));
    }

    /** How many seconds remain in the lockout period (0 if expired). */
    public long secondsUntilUnlock() {
        if (!locked || lockedAt == null) return 0;
        LocalDateTime unlockTime = lockedAt.plusMinutes(5);
        long seconds = java.time.Duration.between(LocalDateTime.now(), unlockTime).getSeconds();
        return Math.max(0, seconds);
    }

    // -------------------------------------------------------------------------
    // Display
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("%-15s | Role: %-7s | Created: %s%s",
                username,
                getRoleName(),
                getCreatedAtFormatted(),
                locked ? " [LOCKED]" : "");
    }
}

