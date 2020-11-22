package edu.tdd.springthymeleaftdd.services;

import edu.tdd.springthymeleaftdd.entities.Book;
import edu.tdd.springthymeleaftdd.exceptions.BadResourceException;
import edu.tdd.springthymeleaftdd.exceptions.ResourceAlreadyExistsException;
import edu.tdd.springthymeleaftdd.exceptions.ResourceNotFoundException;
import edu.tdd.springthymeleaftdd.repositories.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    private boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    public Book findById(Long id) throws ResourceNotFoundException {
        Book book = bookRepository.findById(id).orElse(null);
        if (book==null) {
            throw new ResourceNotFoundException("Cannot find book with id: " + id);
        }
        else return book;
    }

    public List<Book> findAll(int pageNumber, int rowPerPage) {
        List<Book> books = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());

        bookRepository.findAll(sortedByIdAsc).forEach(books::add);
        return books;
    }

    public Book save(Book book) throws BadResourceException, ResourceAlreadyExistsException {
        if (!StringUtils.isEmpty(book.getTitle())) {
            if (book.getId() != null && existsById(book.getId())) {
                throw new ResourceAlreadyExistsException("Book with id: " + book.getId() +
                        " already exists");
            }
            return bookRepository.save(book);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save book");
            exc.addErrorMessage("book is null or empty");
            throw exc;
        }
    }

    public void update(Book book)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(book.getTitle())) {
            if (!existsById(book.getId())) {
                throw new ResourceNotFoundException("Cannot find book with id: " + book.getId());
            }
            bookRepository.save(book);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to update book");
            exc.addErrorMessage("Book is null or empty");
            throw exc;
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find book with id: " + id);
        }
        else {
            bookRepository.deleteById(id);
        }
    }

    public Long count() {
        return bookRepository.count();
    }

}
