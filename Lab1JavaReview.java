import java.util.Random;
import java.util.Scanner;

public class Lab1JavaReview {
    static Scanner scnr = new Scanner(System.in);
    static Random rnd = new Random();

    static String[] books = new String[5];
    static int[] bookNumber = new int[5];
    static boolean[] isAvailable = new boolean[5];
    static int bookCount = 0; 

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n");
            System.out.println("================================");
            System.out.println("     WHAT DO YOU WANT TO DO?    ");
            System.out.println("                                ");
            System.out.println("1. ADD BOOK");
            System.out.println("2. UPDATE BOOK STATUS");
            System.out.println("3. SHOW ALL BOOKS");
            System.out.println("4. GENERATE REPORT");
            System.out.println("5. EXIT");
            System.out.println("================================");
            System.out.print("PLEASE ENTER THE NUMBER OF YOUR CHOSEN ACTION: ");
            String chosen = scnr.nextLine();
            try {
                int chosen_action = Integer.parseInt(chosen);
                switch (chosen_action) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        updateBookStatus();
                        break;
                    case 3:
                        showBooks();
                        break;
                    case 4:
                        generateReport();
                        break;
                    case 5:
                        System.out.println("Exiting program. Goodbye!");
                        return; 
                    default:
                        System.out.println("Invalid Input. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number again.");
            }
        }
    }

    public static void addBook() {
        if (bookCount == books.length) {
            System.out.println("Bookshelf is full. Cannot add more books.");
            return;
        }

        int maxAdd = books.length - bookCount;
        int adding = 0;

        while (true) {
            System.out.print("How many books would you like to add? (Maximum " + maxAdd + "): ");
            String additional = scnr.nextLine();
            try {
                adding = Integer.parseInt(additional);
                if (adding <= 0 || adding > maxAdd) {
                    System.out.println("Invalid number. Please enter a number between 1 and " + maxAdd + ".");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number again.");
            }
        }

        System.out.println("Please enter the titles of the books you want to add:");
        for (int i = 0; i < adding; i++) {
            System.out.print((i + 1) + ". ");
            String title = scnr.nextLine();

            int index = -1;
            for (int j = 0; j < books.length; j++) {
                if (books[j] == null) {
                    index = j;
                    break;
                }
            }

            if (index == -1) {
                System.out.println("Unexpected error: no empty slot found.");
                return;
            }

            books[index] = title;

            int newNum;
            boolean exists;
            do {
                newNum = rnd.nextInt(9999) + 1;
                exists = false;
                for (int k = 0; k < books.length; k++) {
                    if (bookNumber[k] == newNum) {
                        exists = true;
                        break;
                    }
                }
            } while (exists);

            bookNumber[index] = newNum;
            isAvailable[index] = true;
            bookCount++;
        }

        System.out.println("\nBooks Stored:");
        showBooks();
        System.out.println("You have successfully added books. Returning to the main menu.\n");
    }

    public static void updateBookStatus() {
        if (bookCount == 0) {
            System.out.println("No books available to update.");
            return;
        }

        int bookNumToUpdate = -1;
        int index = -1;

        while (true) {
            System.out.println("Enter the book number to update its status:");
            String input = scnr.nextLine();
            try {
                bookNumToUpdate = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid book number.");
                continue;
            }

            index = -1;
            for (int i = 0; i < books.length; i++) {
                if (books[i] != null && bookNumber[i] == bookNumToUpdate) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                System.out.println("Error: The entered book number does not exist. Please try again.");
            } else {
                break;
            }
        }

        System.out.println("Current status of \"" + books[index] + "\": " + (isAvailable[index] ? "Available" : "Borrowed"));
        System.out.println("Enter new status (1 for Available, 2 for Borrowed): ");
        String statusInput = scnr.nextLine();

        if (statusInput.equals("1")) {
            isAvailable[index] = true;
            System.out.println("Status updated to Available.");
        } else if (statusInput.equals("2")) {
            isAvailable[index] = false;
            System.out.println("Status updated to Borrowed.");
        } else {
            System.out.println("Invalid status input. No changes made.");
        }
    }

    public static void showBooks() {
        if (bookCount == 0) {
            System.out.println("No books to show.");
            return;
        }
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                System.out.println((i + 1) + ". " + books[i] + " # " + bookNumber[i] + " - " + (isAvailable[i] ? "Available" : "Borrowed"));
            }
        }
    }

    public static void generateReport() {
        if (bookCount == 0) {
            System.out.println("No books to report.");
            return;
        }
        int availableCount = 0;
        int borrowedCount = 0;
        for (int i = 0; i < books.length; i++) {
            if (books[i] != null) {
                if (isAvailable[i]) {
                    availableCount++;
                } else {
                    borrowedCount++;
                }
            }
        }
        System.out.println("BOOK REPORT");
        System.out.println("Total books: " + bookCount);
        System.out.println("Available books: " + availableCount);
        System.out.println("Borrowed books: " + borrowedCount);
    }
}
