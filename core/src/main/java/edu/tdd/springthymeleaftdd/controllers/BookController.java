package edu.tdd.springthymeleaftdd.controllers;

import edu.tdd.springthymeleaftdd.entities.Book;
import edu.tdd.springthymeleaftdd.exceptions.ResourceNotFoundException;
import edu.tdd.springthymeleaftdd.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class BookController {

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private BookService bookService;

    @Value("${msg.title}")
    private String title;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", title);
        return "index/index";
    }

    @GetMapping(value = "/books")
    public String getBooks(Model model,@RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Book> books = bookService.findAll(pageNumber, ROW_PER_PAGE);

        long count = bookService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("books", books);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "books/book-list";
    }

    @GetMapping(value = {"/books/add"})
    public String showAddBook(Model model) {
        Book book = new Book();
        model.addAttribute("add", true);
        model.addAttribute("book", book);

        return "books/book-edit";
    }

    @PostMapping(value = "/books/add")
    public String addBook(Model model,@ModelAttribute("book") Book book) {
        try {
            Book newBook = bookService.save(book);
            return "redirect:/books/" + String.valueOf(newBook.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "books/book-edit";
        }
    }

    @GetMapping(value = {"/books/{bookId}/edit"})
    public String showEditBook(Model model, @PathVariable long bookId) {
        Book book = null;
        try {
            book = bookService.findById(bookId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Book not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("book", book);
        return "books/book-edit";
    }

    // to fix and change method to PUT
    @PostMapping(value = {"/books/{bookId}/edit"})
    public String updateBook(Model model,
                                @PathVariable long bookId,
                                @ModelAttribute("book") Book book) {
        try {
            book.setId(bookId);
            bookService.update(book);
            return "redirect:/books/" + String.valueOf(book.getId());
        } catch (Exception ex) {
            // log exception first,
            // then show error
            String errorMessage = ex.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "books/book-edit";
        }
    }

    @GetMapping(value = "/books/{bookId}")
    public String getBook(Model model, @PathVariable long bookId) {
        Book book = null;
        try {
            book = bookService.findById(bookId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Book not found");
        }
        model.addAttribute("book", book);
        return "books/book";
    }

    @GetMapping(value = {"/books/{bookId}/delete"})
    public String showDeleteBook(Model model, @PathVariable long bookId) {

        Book book = null;

        try {
            book = bookService.findById(bookId);
        } catch (ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", "Book not found");
        }

        model.addAttribute("allowDelete", true);
        model.addAttribute("book", book);

        return "books/book";
    }

    // to fix and change method to delete
    @PostMapping(value = {"/books/{bookId}/delete"})
    public String deleteBookById(Model model, @PathVariable long bookId) {

        try {
            bookService.deleteById(bookId);
            return "redirect:/books";
        } catch (ResourceNotFoundException ex) {
            String errorMessage = ex.getMessage();
            log.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "books/book";
        }
    }

}
