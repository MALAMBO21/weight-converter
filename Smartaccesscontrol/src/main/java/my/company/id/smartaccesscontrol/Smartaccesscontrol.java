

package my.company.id.smartaccesscontrol;


import javax.swing.JOptionPane;


public class Smartaccesscontrol {

    private static UserManager userManager = new UserManager();
    private static User        currentUser = null;   // logged-in session

    // =========================================================================
    // Entry point
    // =========================================================================

    public static void main(String[] args) {
        showWelcomeMessage();
        mainMenuLoop();
    }

    // =========================================================================
    // Welcome screen
    // =========================================================================

    private static void showWelcomeMessage() {
        String msg =
            "╔══════════════════════════════════════════╗\n" +
            "║   SMART ACCESS CONTROL SYSTEM            ║\n" +
            "║   Data Centre Security — ICE Task 4      ║\n" +
            "╚══════════════════════════════════════════╝\n\n" +
            "Pre-loaded accounts:\n" +
            "  admin   / PIN: 1234  (Admin)\n" +
            "  alice   / PIN: 5678  (Staff)\n" +
            "  bob     / PIN: 9012  (Staff)\n" +
            "  visitor1/ PIN: 0000  (Visitor)";
        JOptionPane.showMessageDialog(null, msg,
                "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================================================================
    // Part 5 — Main menu loop (shown when NOT logged in)
    // =========================================================================

    private static void mainMenuLoop() {
        boolean running = true;

        while (running) {
            String[] options = {"Login", "Exit"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Please choose an option:",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == JOptionPane.CLOSED_OPTION || choice == 1) {
                running = false;                 // Exit
            } else if (choice == 0) {
                doLogin();                       // Login flow
                if (currentUser != null) {
                    showRoleMenu();              // Post-login menu
                    currentUser = null;          // Log out on return
                }
            }
        }

        JOptionPane.showMessageDialog(null,
                "System shut down. Goodbye!",
                "Goodbye", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================================================================
    // Part 1 — Login with attempt counter and lockout
    // =========================================================================

    private static void doLogin() {
        final int MAX_ATTEMPTS = 3;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            // --- Collect username ---
            String username = JOptionPane.showInputDialog(null,
                    "Enter username  (attempt " + attempt + " of " + MAX_ATTEMPTS + "):",
                    "Login", JOptionPane.PLAIN_MESSAGE);

            if (username == null) return;   // User pressed Cancel
            username = username.trim();

            // --- Collect PIN ---
            String pin = JOptionPane.showInputDialog(null,
                    "Enter PIN:",
                    "Login", JOptionPane.PLAIN_MESSAGE);

            if (pin == null) return;
            pin = pin.trim();

            // --- Attempt authentication ---
            User user = userManager.authenticate(username, pin);

            if (user != null) {
                // SUCCESS
                currentUser = user;
                JOptionPane.showMessageDialog(null,
                        "✔  Login successful!\nWelcome, " + user.getUsername()
                                + "  [" + user.getRoleName() + "]",
                        "Access Granted", JOptionPane.INFORMATION_MESSAGE);
                return;

            } else {
                // FAILURE — check why
                User found = userManager.findByUsername(username);

                if (found != null && found.isLocked()) {
                    /*
                     * ADMIN SAFETY (Extension):
                     * If the admin account would be locked, we unlock it
                     * immediately so the application cannot be permanently
                     * killed. A warning is shown instead.
                     */
                    if (found.getRole() == User.ROLE_ADMIN) {
                        found.unlock();
                        JOptionPane.showMessageDialog(null,
                                "⚠  Admin account protection triggered.\n"
                                + "The admin account cannot be permanently locked.\n"
                                + "Please try again with the correct PIN.",
                                "Admin Protection", JOptionPane.WARNING_MESSAGE);
                        return; // Go back to main menu
                    }

                    long secs = found.secondsUntilUnlock();
                    JOptionPane.showMessageDialog(null,
                            "🔒  Account '" + username + "' is LOCKED.\n"
                            + (secs > 0
                                    ? "Auto-unlock in approximately " + secs + " second(s).\n"
                                    + "Or ask an Admin to unblock you."
                                    : "Timeout has expired — please try logging in again."),
                            "System Locked", JOptionPane.ERROR_MESSAGE);
                    return;

                } else {
                    int remaining = MAX_ATTEMPTS - attempt;
                    if (remaining > 0) {
                        JOptionPane.showMessageDialog(null,
                                "✖  Invalid username or PIN.\n"
                                + remaining + " attempt(s) remaining.",
                                "Login Failed", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }

        // Exhausted all attempts without success
        JOptionPane.showMessageDialog(null,
                "🔒  Maximum login attempts reached.\n"
                + "If your account has been locked, ask an Admin\n"
                + "to unblock you or wait 5 minutes.",
                "System Locked", JOptionPane.ERROR_MESSAGE);
    }

    // =========================================================================
    // Part 2 — Role-based menu via switch
    // =========================================================================

    private static void showRoleMenu() {
        switch (currentUser.getRole()) {
            case User.ROLE_ADMIN:
                adminMenu();
                break;
            case User.ROLE_STAFF:
                staffMenu();
                break;
            case User.ROLE_VISITOR:
                visitorMenu();
                break;
            default:
                JOptionPane.showMessageDialog(null,
                        "Unknown role. Access denied.",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // Admin menu — Parts 3, 4 + extension actions
    // =========================================================================

    private static void adminMenu() {
        boolean running = true;

        while (running) {
            String[] options = {
                "Create New User",      // 0 — Part 3
                "View All Users",       // 1 — Part 4
                "View Login Logs",      // 2 — Part 4 extension
                "Unblock a User",       // 3 — Extension
                "Logout"                // 4
            };

            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Admin Panel — choose an action:",
                    "Admin Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0: createUserDialog();  break;
                case 1: viewUsersDialog();   break;
                case 2: viewLogsDialog();    break;
                case 3: unblockUserDialog(); break;
                case 4:
                case JOptionPane.CLOSED_OPTION:
                    running = false;
                    break;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Part 3 — Create user dialog
    // -------------------------------------------------------------------------

    private static void createUserDialog() {
        // Username
        String newUsername = JOptionPane.showInputDialog(null,
                "Enter new username:", "Create User", JOptionPane.PLAIN_MESSAGE);
        if (newUsername == null || newUsername.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        newUsername = newUsername.trim();

        // Duplicate check (Extension)
        if (userManager.usernameExists(newUsername)) {
            JOptionPane.showMessageDialog(null,
                    "Username '" + newUsername + "' already exists. Please choose another.",
                    "Duplicate Username", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // PIN
        String newPin = JOptionPane.showInputDialog(null,
                "Enter PIN for " + newUsername + ":",
                "Create User", JOptionPane.PLAIN_MESSAGE);
        if (newPin == null || newPin.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "PIN cannot be empty.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        newPin = newPin.trim();

        // Role selection
        String[] roles = {"1 - Admin", "2 - Staff", "3 - Visitor"};
        int roleChoice = JOptionPane.showOptionDialog(
                null,
                "Select a role for " + newUsername + ":",
                "Select Role",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                roles,
                roles[1]);

        if (roleChoice == JOptionPane.CLOSED_OPTION) return;
        int role = roleChoice + 1; // maps 0,1,2 -> 1,2,3

        // Create the user
        boolean created = userManager.createUser(newUsername, newPin, role);
        if (created) {
            JOptionPane.showMessageDialog(null,
                    "✔  User '" + newUsername + "' created successfully!\n"
                    + "Role: " + getRoleName(role),
                    "User Created", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Failed to create user. Please check the inputs.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------------------------------------------------------------------------
    // Part 4 — View all users with timestamps
    // -------------------------------------------------------------------------

    private static void viewUsersDialog() {
        StringBuilder sb = new StringBuilder();
        sb.append("All Registered Users (").append(userManager.getUserCount()).append(" total)\n");
        sb.append("=".repeat(65)).append("\n");

        // Redirect UserManager's print to a string
        // We'll build the same output here for the dialog
        sb.append(String.format("%-3s %-15s %-8s %-10s%n", "#", "Username", "Role", "Created At"));
        sb.append("-".repeat(65)).append("\n");

        // We re-use the console print method for the terminal as well
        userManager.printAllUsers();  // still prints to console

        JOptionPane.showMessageDialog(null,
                "User list has been printed to the console output.\n"
                + "(Run from terminal/IDE to see the formatted table.)\n\n"
                + "Total users: " + userManager.getUserCount(),
                "View Users", JOptionPane.INFORMATION_MESSAGE);
    }

    // -------------------------------------------------------------------------
    // Part 4 Extension — View login attempt logs
    // -------------------------------------------------------------------------

    private static void viewLogsDialog() {
        userManager.printAttemptLogs();  // prints to console
        JOptionPane.showMessageDialog(null,
                "Login attempt log has been printed to the console output.",
                "Login Logs", JOptionPane.INFORMATION_MESSAGE);
    }

    // -------------------------------------------------------------------------
    // Extension — Unblock a user
    // -------------------------------------------------------------------------

    private static void unblockUserDialog() {
        String target = JOptionPane.showInputDialog(null,
                "Enter the username to unblock:",
                "Unblock User", JOptionPane.PLAIN_MESSAGE);
        if (target == null || target.trim().isEmpty()) return;
        target = target.trim();

        User found = userManager.findByUsername(target);
        if (found == null) {
            JOptionPane.showMessageDialog(null,
                    "User '" + target + "' not found.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (found.getRole() == User.ROLE_ADMIN) {
            JOptionPane.showMessageDialog(null,
                    "The Admin account cannot be manually unblocked here.\n"
                    + "(It is automatically protected.)",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean done = userManager.unlockUser(target);
        JOptionPane.showMessageDialog(null,
                done ? "✔  User '" + target + "' has been unblocked."
                     : "Failed to unblock user.",
                done ? "Unblocked" : "Error",
                done ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    // =========================================================================
    // Staff menu
    // =========================================================================

    private static void staffMenu() {
        JOptionPane.showMessageDialog(null,
                "Welcome, Staff member " + currentUser.getUsername() + "!\n\n"
                + "You have access to:\n"
                + "  • View your profile\n"
                + "  • Access staff areas\n\n"
                + "(Click OK to logout)",
                "Staff Dashboard", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================================================================
    // Visitor menu
    // =========================================================================

    private static void visitorMenu() {
        JOptionPane.showMessageDialog(null,
                "Welcome, Visitor " + currentUser.getUsername() + "!\n\n"
                + "You have limited access:\n"
                + "  • View public information only\n\n"
                + "(Click OK to logout)",
                "Visitor Access", JOptionPane.INFORMATION_MESSAGE);
    }

    // =========================================================================
    // Utility
    // =========================================================================

    private static String getRoleName(int role) {
        switch (role) {
            case User.ROLE_ADMIN:   return "Admin";
            case User.ROLE_STAFF:   return "Staff";
            case User.ROLE_VISITOR: return "Visitor";
            default:                return "Unknown";
        }
    }
}
       
    
