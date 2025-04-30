package src;

import java.util.List;
import java.util.Scanner;

/**
 * Задача:
 * Реализовать консольное приложение для управления библиотекой.
 * Приложение должно позволять:
 * 1) Добавлять книгу (ISBN, название, автор);
 * 2) Искать книгу по ISBN;
 * 3) Удалять книгу по ISBN;
 * 4) Показать сообщение об ошибке, если книга не найдена.
 */
public class LibraryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        library.addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));
        library.addBook(new Book("978-0201633610", "Design Patterns", "Gamma, Helm, Johnson, Vlissides"));

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    searchByIsbn();
                    break;
                case "3":
                    searchByAuthor();
                    break;
                case "4":
                    listBooks();
                    break;
                case "5":
                    removeBook();
                    break;
                case "0":
                    running = false;
                    System.out.println("Выход. До свидания!");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("=== Меню библиотеки ===");
        System.out.println("1. Добавить книгу");
        System.out.println("2. Найти книгу по ISBN");
        System.out.println("3. Найти книги по автору");
        System.out.println("4. Показать все книги");
        System.out.println("5. Удалить книгу по ISBN");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void addBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Название: ");
        String title = scanner.nextLine().trim();
        System.out.print("Автор: ");
        String author = scanner.nextLine().trim();
        library.addBook(new Book(isbn, title, author));
        System.out.println("Книга добавлена.");
    }

    private static void searchByIsbn() {
        System.out.print("Введите ISBN: ");
        String isbn = scanner.nextLine().trim();
        try {
            Book book = library.findBookByIsbn(isbn);
            System.out.println("Найдена книга: " + book);
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchByAuthor() {
        System.out.print("Введите имя автора: ");
        String author = scanner.nextLine().trim();
        List<Book> books = library.findBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("Книги автора " + author + " не найдены.");
        } else {
            System.out.println("Найденные книги:");
            books.forEach(System.out::println);
        }
    }

    private static void listBooks() {
        List<Book> books = library.listAllBooks();
        if (books.isEmpty()) {
            System.out.println("Каталог пуст.");
        } else {
            System.out.println("Список всех книг:");
            books.forEach(System.out::println);
        }
    }

    private static void removeBook() {
        System.out.print("Введите ISBN для удаления: ");
        String isbn = scanner.nextLine().trim();
        try {
            library.removeBook(isbn);
            System.out.println("Книга удалена.");
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
