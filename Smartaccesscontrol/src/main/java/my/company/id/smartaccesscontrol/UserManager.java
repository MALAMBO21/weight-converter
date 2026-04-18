
package my.company.id.smartaccesscontrol;
 import java.util.ArrayList;

public class UserManager extends Smartaccesscontrol {
  

/**
 * Manages all users and login-attempt logs for the Smart Access Control System.
 * All storage uses ArrayList as required by the task specification.
 */
    // -------------------------------------------------------------------------
    // Storage — ArrayList for every collection (task requirement)
    // -------------------------------------------------------------------------
    private ArrayList<User>            users       = new ArrayList<>();
    private ArrayList<LoginAttemptLog> attemptLogs = new ArrayList<>();

    // -------------------------------------------------------------------------
    // Constructor — pre-populate seed users
    // -------------------------------------------------------------------------

    public UserManager() {
        // Pre-populated accounts so the system can be tested immediately
        users.add(new User("admin",   "1234", User.ROLE_ADMIN));
        users.add(new User("alice",   "5678", User.ROLE_STAFF));
        users.add(new User("bob",     "9012", User.ROLE_STAFF));
        users.add(new User("visitor1","0000", User.ROLE_VISITOR));
    }

    // -------------------------------------------------------------------------
    // Lookup helpers
    // -------------------------------------------------------------------------

    /** Returns the User with the given username, or null if not found. */
    public User findByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    /** Returns true when the username is already taken (case-insensitive). */
    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    // -------------------------------------------------------------------------
    // Authentication
    // -------------------------------------------------------------------------

    /**
     * Attempts to authenticate the supplied credentials.
     *
     * @return the authenticated User on success, null on failure
     */
    public User authenticate(String username, String pin) {
        User user = findByUsername(username);

        if (user == null) {
            // Unknown username — log and return null
            logAttempt(username, false);
            return null;
        }

        // Check for auto-timeout unlock
        if (user.isLocked() && user.isTimeoutExpired()) {
            user.unlock();
            System.out.println("  [INFO] Account '" + username
                    + "' has been automatically unlocked after the timeout period.");
        }

        if (user.isLocked()) {
            logAttempt(username, false);
            return null; // Still locked
        }

        if (user.getPin().equals(pin)) {
            user.unlock(); // Reset any previous failed attempts
            logAttempt(username, true);
            return user;
        } else {
            user.registerFailedAttempt();
            logAttempt(username, false);
            return null;
        }
    }

    // -------------------------------------------------------------------------
    // User creation (Admin only)
    // -------------------------------------------------------------------------

    /**
     * Creates a new user and adds it to the ArrayList.
     *
     * @return true on success, false if the username already exists
     */
    public boolean createUser(String username, String pin, int role) {
        if (usernameExists(username)) return false;

        // Basic input validation
        if (username == null || username.trim().isEmpty()) return false;
        if (pin == null || pin.trim().isEmpty())           return false;
        if (role < 1 || role > 3)                          return false;

        users.add(new User(username.trim(), pin.trim(), role));
        return true;
    }

    // -------------------------------------------------------------------------
    // Admin management actions
    // -------------------------------------------------------------------------

    /**
     * Unlocks a user account (Admin privilege).
     *
     * @return true if the user was found and unlocked
     */
    public boolean unlockUser(String username) {
        User user = findByUsername(username);
        if (user == null) return false;
        user.unlock();
        return true;
    }

    // -------------------------------------------------------------------------
    // Logging
    // -------------------------------------------------------------------------

    private void logAttempt(String username, boolean success) {
        attemptLogs.add(new LoginAttemptLog(username, success));
    }

    // -------------------------------------------------------------------------
    // Display helpers
    // -------------------------------------------------------------------------

    /** Prints all users in a formatted table. */
    public void printAllUsers() {
        if (users.isEmpty()) {
            System.out.println("  No users found.");
            return;
        }
        System.out.println("  " + "-".repeat(70));
        System.out.printf("  %-3s %-15s %-8s %s%n", "#", "Username", "Role", "Created At");
        System.out.println("  " + "-".repeat(70));
        for (int i = 0; i < users.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + users.get(i));
        }
        System.out.println("  " + "-".repeat(70));
    }

    /** Prints the full login-attempt log. */
    public void printAttemptLogs() {
        if (attemptLogs.isEmpty()) {
            System.out.println("  No login attempts recorded yet.");
            return;
        }
        System.out.println("  " + "-".repeat(55));
        System.out.println("  Login Attempt History:");
        System.out.println("  " + "-".repeat(55));
        for (LoginAttemptLog log : attemptLogs) {
            System.out.println("  " + log);
        }
        System.out.println("  " + "-".repeat(55));
    }

    /** Returns the number of registered users. */
    public int getUserCount() { return users.size(); }
}

