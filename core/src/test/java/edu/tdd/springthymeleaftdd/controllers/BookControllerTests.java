package edu.tdd.springthymeleaftdd.controllers;

import edu.tdd.springthymeleaftdd.entities.Book;
import edu.tdd.springthymeleaftdd.services.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
public class BookControllerTests {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test get index page - Get /")
    public void testGetIndexPage() throws Exception {

        // perform GET Request
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                // validate 200 OK received
                .andExpect(status().isOk())
                .andExpect(view().name("index/index"))
                .andExpect(model().attributeExists("title"));
    }

    @Test
    @DisplayName("Test get list of books page - Get /books")
    public void testGetBooksPage() throws Exception {

        // perform GET Request
        mockMvc.perform(
                    MockMvcRequestBuilders.get("/books")
                    .queryParam("page","1")
            )
                // validate 200 OK received
                .andExpect(status().isOk())
                .andExpect(view().name("books/book-list"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("hasPrev"))
                .andExpect(model().attributeExists("prev"))
                .andExpect(model().attributeExists("hasNext"))
                .andExpect(model().attributeExists("next"));
    }

    @Test
    @DisplayName("Test get add new book page - Get /books/add")
    public void testGetCreateNewBookPage() throws Exception {

        // perform GET Request
        mockMvc.perform(MockMvcRequestBuilders.get("/books/add"))
                // validate 200 OK received
                .andExpect(status().isOk())
                .andExpect(view().name("books/book-edit"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("add"));
    }

    @Test
    @DisplayName("Test add new book - POST /books/add")
    public void testCreateNewBook() throws Exception {
        Book book = new Book();
        book.setId(2L);

        when(bookService.save(ArgumentMatchers.any())).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post("/books/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("title", "a title")
                .param("summary", "summary ....")
                .param("genre", "a genre")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/2"));
    }

    @Test
    @DisplayName("Test update book - POST /books/{bookId}/edit")
    public void testUpdateBook() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit",1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "book title 1")
                .param("summary", "book summary 1")
                .param("genre", "updated")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/1"));
    }

    @Test
    @DisplayName("Test get books page - Get /books/{bookId}")
    public void testGetBookPage() throws Exception {

        Book book = new Book(1L,"book title 1","book summary 1","test");

        when(bookService.findById(anyLong())).thenReturn(book);

        // perform GET Request
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/{bookId}",1)
        )
                // validate 200 OK received
                .andExpect(status().isOk())
                .andExpect(view().name("books/book"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    @DisplayName("Test get delete books page - Get /books/{bookId}/delete")
    public void testGetDeleteBookPage() throws Exception {

        Book book = new Book(1L,"book title 1","book summary 1","test");

        when(bookService.findById(anyLong())).thenReturn(book);

        // perform GET Request
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/{bookId}/delete",1)
        )
                // validate 200 OK received
                .andExpect(status().isOk())
                .andExpect(view().name("books/book"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("allowDelete"));
    }

    @Test
    @DisplayName("Test delete book - POST /books/{bookId}/delete")
    public void testDeleteBook() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/delete",1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books"));
    }

}
