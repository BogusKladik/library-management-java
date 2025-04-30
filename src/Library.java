package src;

import java.util.HashMap;
import java.util.Map;

public class Library {
    private final Map<String, Book> catalog = new HashMap<>();

    public void addBook(Book book) {
        catalog.put(book.getIsbn(), book);
    }

    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        Book book = catalog.get(isbn);
        if (book == null) {
            throw new BookNotFoundException("Книга с ISBN " + isbn + " не найдена.");
        }
        return book;
    }

    public void removeBook(String isbn) throws BookNotFoundException {
        if (catalog.remove(isbn) == null) {
            throw new BookNotFoundException("Невозможно удалить: книга с ISBN " + isbn + " отсутствует.");
        }
    }
}
