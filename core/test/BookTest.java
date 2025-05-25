import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void toString_containsAllFields() {
        Book b = new Book("ISBN-123", "Title", "Author");
        String s = b.toString();
        assertTrue(s.contains("ISBN=ISBN-123"));
        assertTrue(s.contains("title=\"Title\""));
        assertTrue(s.contains("author=\"Author\""));
    }

    @Test
    void equals_and_hashCode_sameIsbn() {
        Book b1 = new Book("X", "T1", "A1");
        Book b2 = new Book("X", "T2", "A2");
        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    void equals_falseForDifferentIsbn() {
        Book b1 = new Book("X1", "T", "A");
        Book b2 = new Book("X2", "T", "A");
        assertNotEquals(b1, b2);
    }

    @Test
    void getters_returnWhatWasPassed_includingNulls() {
        Book b = new Book(null, null, null);
        assertNull(b.getIsbn());
        assertNull(b.getTitle());
        assertNull(b.getAuthor());
    }
}
