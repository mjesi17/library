import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Book {
    String name;
    int id;
    boolean available;

    public Book(String name, int id, boolean available) {
        this.name = name;
        this.id = id;
        this.available = available;
    }
}

class Student {
    String name;
    String password;
    int id;
    int borrowedBookId;

    public Student(String name, String password, int id) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.borrowedBookId = 0;
    }
}

public class Library {
    static Book[] books = new Book[100];
    static Student[] students = new Student[100];
    static int bookCount = 1;
    static int bookId = 101;
    static int studentCount = 1;
    static int studentId = 1001;
    
    static Scanner scanner = new Scanner(System.in);

    public static void viewBooks() {
        System.out.println();
        for (int i = 1; i < bookCount; i++) {
            System.out.printf("Book name: %s\tBook Id: %-10d\t", books[i].name, books[i].id);
            System.out.println(books[i].available ? "Available" : "Not Available");
        }
        System.out.println();
    }

    public static void admin() {
        String username, password;
        System.out.print("Enter username: ");
        username = scanner.next();
        System.out.print("Enter password: ");
        password = scanner.next();

        if (username.equals("Library") && password.equals("Mylibrary")) {
            while (true) {
                System.out.println("\n----------> ADMIN <------------");
                System.out.println("1. Add Book\n2. View Book\n3. Report Book\n4. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("How many books to add: ");
                    int m = scanner.nextInt();
                    for (int i = 1; i <= m; i++) {
                        System.out.print("Enter Book name: ");
                        String bookName = scanner.next();
                        books[bookCount] = new Book(bookName, bookId++, true);
                        System.out.println("Your Book id is: " + books[bookCount++].id);
                    }
                } else if (choice == 2) {
                    viewBooks();
                } else if (choice == 3) {
                    reportBooks();
                } else if (choice == 4) {
                    break;
                } else {
                    System.out.println("Invalid choice...");
                }
            }
        } else {
            System.out.println("Username or password is wrong...");
        }
    }

    public static void student() {
        while (true) {
            System.out.println("\n-------------> STUDENT LOGIN <--------------");
            System.out.println("1. Sign In\n2. Sign Up\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter user id: ");
                int id = scanner.nextInt();
                System.out.print("Enter password: ");
                String password = scanner.next();

                int studentIndex = id - 1000;
                if (students[studentIndex] != null && students[studentIndex].id == id && students[studentIndex].password.equals(password)) {
                    while (true) {
                        System.out.println("1. View Book\n2. Lend Book\n3. Return Book\n4. Log out");
                        System.out.print("Enter your choice: ");
                        int stuChoice = scanner.nextInt();

                        if (stuChoice == 1) {
                            viewBooks();
                        } else if (stuChoice == 2) {
                            System.out.print("Enter Book name: ");
                            String bookName = scanner.next();
                            System.out.print("Enter Book id: ");
                            int bookId = scanner.nextInt();

                            int bookIndex = bookId - 100;
                            if (students[studentIndex].borrowedBookId == 0 && books[bookIndex].available) {
                                students[studentIndex].borrowedBookId = books[bookIndex].id;
                                books[bookIndex].available = false;
                                System.out.println(bookName + " Lend Successfully...");
                            } else if (students[studentIndex].borrowedBookId != 0) {
                                System.out.println("You already borrowed a book...");
                            } else {
                                System.out.println("Book Not Available...");
                            }
                        } else if (stuChoice == 3) {
                            int borrowedBookId = students[studentIndex].borrowedBookId;
                            if (borrowedBookId != 0 && !books[borrowedBookId - 100].available) {
                                books[borrowedBookId - 100].available = true;
                                students[studentIndex].borrowedBookId = 0;
                                System.out.println("Book returned successfully...");
                            } else {
                                System.out.println("You have no book to return...");
                            }
                        } else if (stuChoice == 4) {
                            break;
                        } else {
                            System.out.println("Invalid choice...");
                        }
                    }
                } else {
                    System.out.println("User id or password is wrong...");
                }
            } else if (choice == 2) {
                System.out.print("Enter Member name: ");
                String name = scanner.next();
                System.out.print("Enter Password: ");
                String pass = scanner.next();
                students[studentCount] = new Student(name, pass, studentId++);
                System.out.println("Your Membership id is: " + students[studentCount++].id);
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice...");
            }
        }
    }

    public static void reportBooks() {
        try (PrintWriter out = new PrintWriter(new FileWriter("out.txt"))) {
            for (int i = 1; i < bookCount; i++) {
                out.printf("Book name: %s\tBook Id: %-10d\t", books[i].name, books[i].id);
                out.println(books[i].available ? "Available" : "Not Available");
            }
            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Admin\n2. Student\n3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    admin();
                    break;
                case 2:
                    student();
                    break;
                case 3:
                    System.out.println("Thank you for using the library system.");
                    return;
                default:
                    System.out.println("Invalid choice...");
            }
        }
    }
}