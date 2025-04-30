package src;

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
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));
        library.addBook(new Book("978-0201633610", "Design Patterns", "Gamma, Helm, Johnson, Vlissides"));
        try {
            System.out.println("Ищем книгу по ISBN 978-0134685991:");
            Book found = library.findBookByIsbn("978-0134685991");
            System.out.println(found);

            System.out.println("\nУдаляем книгу 978-0201633610 …");
            library.removeBook("978-0201633610");

            System.out.println("\nПытаемся найти удалённую книгу:");
            library.findBookByIsbn("978-0201633610");
        } catch (BookNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}