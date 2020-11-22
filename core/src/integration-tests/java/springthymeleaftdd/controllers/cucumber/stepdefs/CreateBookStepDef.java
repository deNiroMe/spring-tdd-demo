package springthymeleaftdd.controllers.cucumber.stepdefs;

import edu.tdd.springthymeleaftdd.entities.Book;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Transpose;
import io.cucumber.java8.En;
import io.cucumber.java.en.Given;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springthymeleaftdd.controllers.cucumber.CucumberSpringContextConfiguration;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CreateBookStepDef extends CucumberSpringContextConfiguration implements En {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions resultActions;

    private Book bookToSave;

    public CreateBookStepDef() {

        Given("a book with following info", (@Transpose Book book) -> {
            System.out.println(book);
            this.bookToSave = book;
            System.out.println(book);
            Assertions.assertNotNull(this.bookToSave);
        });

        When("I post for creating a new book", () -> {
            resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/books/add")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "")
                    .param("title", this.bookToSave.getTitle())
                    .param("summary", this.bookToSave.getSummary())
                    .param("genre", this.bookToSave.getGenre())
            );
        });

        Then("a should get a response of status code {int}", (Integer status) -> {
            resultActions.andExpect(status().is(status));
        });

        Then("a new book is added", () -> {
            resultActions.andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/books/3"));
        });

    }
}
