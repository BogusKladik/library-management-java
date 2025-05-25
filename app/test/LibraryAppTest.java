import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.*;

class LibraryAppTest {
    @Mock
    Library mockLibrary;
    @Mock
    Console mockConsole;
    private LibraryApp app;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchByIsbn_printsFound() throws Exception {
        when(mockConsole.readLine()).thenReturn("2", "978-1", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        Book b = new Book("978-1", "T", "A");
        when(mockLibrary.findBookByIsbn("978-1")).thenReturn(b);
        app.run();
        verify(mockConsole).println("Найдена книга: " + b);
    }

    @Test
    void searchByIsbn_notFound() throws Exception {
        when(mockConsole.readLine()).thenReturn("2", "978-1", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        when(mockLibrary.findBookByIsbn("978-1"))
                .thenThrow(new BookNotFoundException("not found"));
        app.run();
        verify(mockConsole).println("not found");
    }

    @Test
    void addBook_callsLibraryAndPrintsConfirmation() {
        when(mockConsole.readLine())
                .thenReturn("1", "isbn-123", "My Title", "My Author", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        app.run();
        verify(mockLibrary).addBook(argThat(book -> book.getIsbn().equals("isbn-123")
                && book.getTitle().equals("My Title")
                && book.getAuthor().equals("My Author")));
        verify(mockConsole).println("Книга добавлена.");
    }

    @Test
    void searchByAuthor_emptyList_printsNotFoundMessage() {
        when(mockConsole.readLine()).thenReturn("3", "SomeAuthor", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        when(mockLibrary.findBooksByAuthor("SomeAuthor"))
                .thenReturn(List.of());
        app.run();
        verify(mockConsole).println("Книги автора SomeAuthor не найдены.");
    }

    @Test
    void searchByAuthor_nonEmpty_printsAllBooks() {
        when(mockConsole.readLine()).thenReturn("3", "Auth", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        Book b1 = new Book("1", "T1", "Auth");
        Book b2 = new Book("2", "T2", "Auth");
        when(mockLibrary.findBooksByAuthor("Auth"))
                .thenReturn(List.of(b1, b2));
        app.run();
        verify(mockConsole).println("Найденные книги:");
        verify(mockConsole).println(b1.toString());
        verify(mockConsole).println(b2.toString());
    }

    @Test
    void listBooks_emptyCatalog_printsEmptyMessage() {
        when(mockConsole.readLine()).thenReturn("4", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        when(mockLibrary.listAllBooks()).thenReturn(List.of());
        app.run();
        verify(mockConsole).println("Каталог пуст.");
    }

    @Test
    void listBooks_nonEmptyCatalog_printsAll() {
        when(mockConsole.readLine()).thenReturn("4", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        Book b = new Book("X", "TitleX", "AuthorX");
        when(mockLibrary.listAllBooks()).thenReturn(List.of(b));
        app.run();
        verify(mockConsole).println("Список всех книг:");
        verify(mockConsole).println(b.toString());
    }

    @Test
    void removeBook_success_printsDeleted() throws Exception {
        when(mockConsole.readLine()).thenReturn("5", "999-9", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        doNothing().when(mockLibrary).removeBook("999-9");
        app.run();
        verify(mockConsole).println("Книга удалена.");
    }

    @Test
    void removeBook_notFound_printsErrorMessage() throws Exception {
        when(mockConsole.readLine()).thenReturn("5", "999-9", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        doThrow(new BookNotFoundException("Not here"))
                .when(mockLibrary).removeBook("999-9");
        app.run();
        verify(mockConsole).println("Not here");
    }

    @Test
    void invalidChoice_printsErrorPrompt() {
        when(mockConsole.readLine()).thenReturn("42", "0");
        app = new LibraryApp(mockLibrary, mockConsole);
        app.run();
        verify(mockConsole).println("Неверный выбор. Попробуйте снова.");
    }
}
