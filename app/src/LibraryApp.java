import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(LibraryApp.class);

    private final Console console;
    private final Library library;

    public LibraryApp(Library library, Console console) {
        this.library = library;
        this.console = console;
    }

    public static void main(String[] args) {
        log.info("start app LibraryApp");
        Library lib = new Library();
        lib.addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));
        lib.addBook(new Book("978-0201633610", "Design Patterns", "Gamma, Helm, Johnson, Vlissides"));
        new LibraryApp(lib, new SystemConsole()).run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = console.readLine().trim();
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
                    console.println("Выход. До свидания!");
                    break;
                default:
                    console.println("Неверный выбор. Попробуйте снова.");
            }
            console.println("");
        }
    }

    private void printMenu() {
        console.println("=== Меню библиотеки ===");
        console.println("1. Добавить книгу");
        console.println("2. Найти книгу по ISBN");
        console.println("3. Найти книги по автору");
        console.println("4. Показать все книги");
        console.println("5. Удалить книгу по ISBN");
        console.println("0. Выход");
        console.print("Выберите действие: ");
    }

    private void addBook() {
        console.print("ISBN: ");
        String isbn = console.readLine().trim();
        console.print("Название: ");
        String title = console.readLine().trim();
        console.print("Автор: ");
        String author = console.readLine().trim();
        library.addBook(new Book(isbn, title, author));
        console.println("Книга добавлена.");
    }

    private void searchByIsbn() {
        console.print("Введите ISBN: ");
        String isbn = console.readLine().trim();
        try {
            Book book = library.findBookByIsbn(isbn);
            console.println("Найдена книга: " + book);
        } catch (Exception e) {
            console.println(e.getMessage());
        }
    }

    private void searchByAuthor() {
        console.print("Введите имя автора: ");
        String author = console.readLine().trim();
        List<Book> books = library.findBooksByAuthor(author);
        if (books.isEmpty()) {
            console.println("Книги автора " + author + " не найдены.");
        } else {
            console.println("Найденные книги:");
            for (Book book : books) {
                console.println(book.toString());
            }
        }
    }

    private void listBooks() {
        List<Book> books = library.listAllBooks();
        if (books.isEmpty()) {
            console.println("Каталог пуст.");
        } else {
            console.println("Список всех книг:");
            for (Book book : books) {
                console.println(book.toString());
            }
        }
    }

    private void removeBook() {
        console.print("Введите ISBN для удаления: ");
        String isbn = console.readLine().trim();
        try {
            library.removeBook(isbn);
            console.println("Книга удалена.");
        } catch (Exception e) {
            console.println(e.getMessage());
        }
    }
}
