import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class LibraryDemo {

    public static void main(String[] args) {
    	int appInit = 1;
        Scanner scanner = new Scanner(System.in);
        LibraryManagement library = new LibraryManagement();

        // Load users from file
        String usersFilePath = "C:\\Users\\melea\\Desktop\\USV Course Work\\USVCS212Java\\step1\\src\\step1o\\EM_LLS\\src\\MK_libraryLending\\UserList.csv";
        library.loadUsersFromFile(usersFilePath);

        UserNameGenerator userGener = new UserNameGenerator();

        System.out.println("\nWelcome to Library System Management");
        System.out.print("Do you have an account? (yes/no): ");
        String userResponse = scanner.next().trim().toLowerCase();

        if (userResponse.equals("no")) {
            System.out.print("Enter new Username: ");
            String userName = scanner.next().trim();

            // Check if the username already exists
            boolean usernameExists = library.getUsers().stream()
                .anyMatch(user -> user.getName().equals(userName));
            if (usernameExists) {
                System.out.println("Username already exists. Please choose a different username.");
                return;
            }

            // Generate  user ID
            String generatedUserId = userGener.generateRandomUserId();

            // Create a new user
            User newUser = new User(userName, generatedUserId);

            // Add user to the library
            library.addUser(newUser);

            // Save new user to file
            library.saveUserToFile(newUser);
            System.out.println("User registered successfully! Welcome, " + userName +
                " (your ID is: " + generatedUserId + ")");

        } else if (userResponse.equals("yes")) {
            System.out.print("Enter Username: ");
            String userName = scanner.next().trim();

            // Check if the username exists
            boolean usernameExists = library.getUsers().stream()
                .anyMatch(user -> user.getName().equals(userName));
            if (usernameExists) {
                System.out.println("Welcome back, " + userName + "!");
            } else {
                System.out.println("Username does not exist. Please register first.");
                return;
            }

        } else {
            System.out.println("Invalid response. Please enter 'yes' or 'no'.");
            return;
        }
//AppLoop
        while(appInit == 1) {
        System.out.println("__________________________");
        System.out.println("__________________________");
        System.out.println("__________________________");
        System.out.println("__________________________");
        System.out.println("__________________________");
        System.out.println("Enter your Choice number: ");
        System.out.println("Library Items: 1");
        System.out.println("User Display:  2");
        System.out.println("Borrow Item:   3");
        System.out.println("Return item:   4");
        System.out.println("Exit app:      5");
        int appChoice = scanner.nextInt();
        	
        //choice 1 to see library items	
       
        if(appChoice == 1){
        // Load library items from file
        System.out.println("\nLoading library items from file...\n");
        String itemsFilePath = "C:\\Users\\melea\\Desktop\\USV Course Work\\USVCS212Java\\step1\\src\\step1o\\EM_LLS\\src\\MK_libraryLending\\BookNames.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    switch (parts[0].trim()) {
                        case "Book":
                            library.addLibraryItem(new Book(
                                parts[1].trim(), // Title
                                parts[2].trim(), // ID
                                parts[3].trim(), // Author
                                parts[4].trim().equals("null") ? null : parts[4].trim(), // Borrower
                                Double.parseDouble(parts[5].trim()), // Price
                                parts[6].trim() // Year
                            ));
                            break;

                        case "Ebook":
                            library.addLibraryItem(new Ebook(
                                parts[1].trim(), // Title
                                parts[2].trim(), // ID
                                parts[3].trim(), // Author
                                parts[4].trim().equals("null") ? null : parts[4].trim(), // Borrower
                                Double.parseDouble(parts[5].trim()), // Price
                                parts[6].trim(), // Year
                                parts[7].trim() // Format
                            ));
                            break;

                        case "Movie":
                            library.addLibraryItem(new Movie(
                                parts[1].trim(), // Title
                                parts[2].trim(), // ID
                                parts[3].trim().equals("null") ? null : parts[3].trim(), // Borrower
                                parts[4].trim(), // Genre
                                parts[5].trim(), // Director
                                Integer.parseInt(parts[6].trim()), // Duration
                                parts[7].trim() // Year
                            ));
                            break;

                        case "Magazine":
                            library.addLibraryItem(new Magazine(
                                parts[1].trim(), // Title
                                parts[2].trim(), // ID
                                parts[3].trim().equals("null") ? null : parts[3].trim(), // Borrower
                                parts[4].trim(), // Publisher
                                Integer.parseInt(parts[5].trim()), // Pages
                                parts[6].trim() // Issue Date
                            ));
                            break;

                        default:
                            System.out.println("Unknown item type: " + parts[0].trim());
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading library items file: " + ex.getMessage());
        }
        
        // Display library items
        System.out.println("\nLibrary Items:");
        library.displayAllItems();
        }
        
        
        //choice 2 for users
        
        if(appChoice == 2) {
        // for testing with user
        User testUser = new User("John Doe", "JD123");
        library.addUser(testUser);
        
     // Display all users
        System.out.println("\nCurrent Users:");
        library.displayAllUsers();
        }
        
        //choice 3 borrowing items
        
        if(appChoice == 3) {
        // Borrow an item
        	
        System.out.println("\nAttempting to borrow a book...");
        library.borrowItem("JD123", "B001");

        // Display updated state of the library
        System.out.println("\nLibrary Collection After Borrowing:");
        library.displayAllItems();
        }
        
        // choice 4 return items
        
        if(appChoice == 4) {
        // Return the borrowed item
        System.out.println("\nAttempting to return the book...");
        library.returnItem("JD123", "B001");
        }
        
        if(appChoice == 5) {
        	System.out.println("Thank you for visit XY Library");
        	System.out.println("Session Terminating");
        	appInit = 0;
        }
        
        
        }
        System.out.println("Session Terminated...");
        scanner.close();
    }
}
