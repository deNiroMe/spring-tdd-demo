package edu.tdd.springthymeleaftdd.services;

import edu.tdd.springthymeleaftdd.entities.Book;
import edu.tdd.springthymeleaftdd.exceptions.BadResourceException;
import edu.tdd.springthymeleaftdd.exceptions.ResourceAlreadyExistsException;
import edu.tdd.springthymeleaftdd.exceptions.ResourceNotFoundException;
import edu.tdd.springthymeleaftdd.repositories.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AssertionErrors;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class BookServiceTests {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Test find book with id successfully")
    public void testFindBookById() throws Exception{

        Book mockBook = new Book(1L,"book title","book summary","test");

        doReturn(Optional.of(mockBook)).when(bookRepository).findById(anyLong());

        Book returnedBook = bookService.findById(1L);

        Assertions.assertNotNull(returnedBook);

        Assertions.assertSame(1L,returnedBook.getId());
        Assertions.assertSame("book title",returnedBook.getTitle());
        Assertions.assertSame("book summary",returnedBook.getSummary());
        Assertions.assertSame("test",returnedBook.getGenre());
    }

    @Test()
    @DisplayName("Test find book with id failure")
    public void testBookNotFoundById() throws Exception{

        doReturn(Optional.empty()).when(bookRepository).findById(anyLong());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookService.findById(1L) );
    }

    @Test
    @DisplayName("Test Find All books")
    public void testFindAllBooks(){

        Book mockBook1 = new Book(1L,"book title 1","book summary 1","test");
        Book mockBook2 = new Book(2L,"book title 2","book summary 1","test");

        Page<Book> page = new PageImpl<Book>(Arrays.asList(mockBook1,mockBook2));

        doReturn(page).when(bookRepository).findAll(Mockito.any(Pageable.class));

        Iterable<Book> books = bookService.findAll(1,10);

        Assertions.assertEquals(2,((Collection<?>) books).size());
    }

    @Test
    @DisplayName("Test Save book successfully")
    public void testSaveBook() throws Exception{

        Book mockBook1 = new Book("book title 1","book summary 1","test");
        Book savedBook = new Book(1L,"book title 1","book summary 1","test");

        doReturn(savedBook).when(bookRepository).save(any());

        Book returnedBook = bookService.save(mockBook1);

        AssertionErrors.assertNotNull("Book should not be null",returnedBook);
        Assertions.assertSame(1L,returnedBook.getId());
        Assertions.assertSame("book title 1",returnedBook.getTitle());
        Assertions.assertSame("book summary 1",returnedBook.getSummary());
        Assertions.assertSame("test",returnedBook.getGenre());
    }

    @Test
    @DisplayName("Test Save book failure - throw ResourceAlreadyExistsException")
    public void testSaveBookAlreadyExistsFailure() throws Exception{

        Book mockBook1 = new Book(1L,"book title 1","book summary 1","test");

        doReturn(true).when(bookRepository).existsById(anyLong());

        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> bookService.save(mockBook1) );
    }

    @Test
    @DisplayName("Test Save book failure - throw BadResourceException")
    public void testSaveBookBadResourceFailure() throws Exception{

        Book mockBook1 = new Book(1L,"","book summary 1","test");

        Assertions.assertThrows(BadResourceException.class, () -> bookService.save(mockBook1) );
    }

    @Test
    @DisplayName("Test Update book successfully")
    public void testUpdateBook() throws Exception{

        Book mockBook1 = new Book(1L,"book title 1","book summary 1","test");
        Book savedBook = new Book(1L,"updated title 1","updated summary 1","fiction");

        doReturn(savedBook).when(bookRepository).save(any());
        doReturn(true).when(bookRepository).existsById(anyLong());

        bookService.update(mockBook1);

        AssertionErrors.assertNotNull("Book should not be null",savedBook);
        Assertions.assertSame(1L,savedBook.getId());
        Assertions.assertSame("updated title 1",savedBook.getTitle());
        Assertions.assertSame("updated summary 1",savedBook.getSummary());
        Assertions.assertSame("fiction",savedBook.getGenre());
    }

    @Test
    @DisplayName("Test Update book failure - throws ResourceNotFoundException")
    public void testUpdateBookNotExistsFailure() throws Exception{

        Book mockBook1 = new Book("book title 1","book summary 1","test");

        doReturn(false).when(bookRepository).existsById(anyLong());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookService.update(mockBook1) );
    }

    @Test
    @DisplayName("Test Update book failure - throws ResourceNotFoundException")
    public void testUpdateBookBadResourceFailure() throws Exception{

        Book mockBook1 = new Book(1L,"","book summary 1","test");

        Assertions.assertThrows(BadResourceException.class, () -> bookService.update(mockBook1) );
    }

    @Test
    @DisplayName("Test delete book successfully")
    public void testDeleteBookById() throws Exception {

        //given
        Long idToDelete = Long.valueOf(2L);
        //when
        doReturn(true).when(bookRepository).existsById(anyLong());
        bookService.deleteById(idToDelete);
        //then
        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Test delete book failure - throws ResourceNotFoundException")
    public void testDeleteBookByIdFailure() throws Exception {

        //given
        Long idToDelete = Long.valueOf(200L);
        //when
        doReturn(false).when(bookRepository).existsById(anyLong());
        //then
        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookService.deleteById(idToDelete) );
    }

    @Test
    @DisplayName("Test count books")
    public void testCountBooks() throws Exception{

        doReturn(2L).when(bookRepository).count();

        Long count = bookService.count();

        Assertions.assertEquals(2L,count);
    }

}
