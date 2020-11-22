package springthymeleaftdd.controllers.cucumber.stepdefs;

import edu.tdd.springthymeleaftdd.entities.Book;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springthymeleaftdd.controllers.cucumber.CucumberSpringContextConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UpdateBookStepDef extends CucumberSpringContextConfiguration {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions resultActions;

    private Book bookToUpdate;

    @Given("the book with values")
    public void the_book_with_values(@Transpose Book book) {
        this.bookToUpdate = book;
        System.out.println(book);
        Assertions.assertNotNull(this.bookToUpdate);
    }

    @When("when i update the book with following values")
    public void when_i_update_the_book_with_following_values() throws Exception {
        resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/books/{bookId}/edit",1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("title", this.bookToUpdate.getTitle())
                .param("summary", this.bookToUpdate.getSummary())
                .param("genre", this.bookToUpdate.getGenre())
        );
    }

    @Then("a should get a response of status code {int} for updating")
    public void a_should_get_a_response_of_status_code_for_updating(Integer status) throws Exception {
        System.out.println(resultActions);
        resultActions.andExpect(status().is(status));
    }

    @And("an updated valid updated book")
    public void an_updated_valid_updated_book() throws Exception {
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books/"+this.bookToUpdate.getId()));
    }

}
