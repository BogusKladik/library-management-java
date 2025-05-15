import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : catalog.values()) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> listAllBooks() {
        return new ArrayList<>(catalog.values());
    }

    public void removeBook(String isbn) throws BookNotFoundException {
        if (catalog.remove(isbn) == null) {
            throw new BookNotFoundException("Невозможно удалить: книга с ISBN " + isbn + " отсутствует.");
        }
    }
}
