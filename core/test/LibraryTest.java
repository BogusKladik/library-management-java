import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LibraryAdditionalTest {

    private Library lib;

    @BeforeEach
    void init() {
        lib = new Library();
    }

    @Test
    void listAllBooks_emptyLibrary() {
        List<Book> list = lib.listAllBooks();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    void listAllBooks_returnsCopyNotInternal() {
        Book b = new Book("1", "T", "A");
        lib.addBook(b);
        List<Book> l1 = lib.listAllBooks();
        l1.clear();
        List<Book> l2 = lib.listAllBooks();
        assertEquals(1, l2.size());
    }

    @Test
    void findBooksByAuthor_noMatches() {
        lib.addBook(new Book("1", "T", "A"));
        List<Book> found = lib.findBooksByAuthor("Unknown");
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    void findBooksByAuthor_multipleMatches() {
        Book b1 = new Book("1", "T1", "Auth");
        Book b2 = new Book("2", "T2", "Auth");
        Book b3 = new Book("3", "T3", "Other");
        lib.addBook(b1);
        lib.addBook(b2);
        lib.addBook(b3);
        List<Book> found = lib.findBooksByAuthor("auth");
        assertEquals(2, found.size());
        assertTrue(found.containsAll(List.of(b1, b2)));
    }

    @Test
    void addBook_overwritesExisting() throws Exception {
        Book b1 = new Book("X", "Old", "A");
        Book b2 = new Book("X", "New", "B");
        lib.addBook(b1);
        lib.addBook(b2);
        Book result = lib.findBookByIsbn("X");
        assertEquals("New", result.getTitle());
        assertEquals("B", result.getAuthor());
    }

    @Test
    void removeBook_onEmpty_throws() {
        assertThrows(BookNotFoundException.class, () -> lib.removeBook("no"));
    }

    @Test
    void removeBook_removesAndThenNotFound() throws Exception {
        Book b = new Book("Y", "T", "A");
        lib.addBook(b);
        lib.removeBook("Y");
        assertThrows(BookNotFoundException.class, () -> lib.findBookByIsbn("Y"));
    }

    @Test
    void findBookByIsbn_messageIncludesIsbn() {
        BookNotFoundException ex = assertThrows(
                BookNotFoundException.class,
                () -> lib.findBookByIsbn("abc"));
        assertTrue(ex.getMessage().contains("abc"));
    }
}
