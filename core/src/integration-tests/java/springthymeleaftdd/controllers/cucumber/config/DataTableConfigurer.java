package springthymeleaftdd.controllers.cucumber.config;

import edu.tdd.springthymeleaftdd.entities.Book;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Map;

public class DataTableConfigurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
        registry.defineDataTableType(new DataTableType(Book.class, new TableEntryTransformer<Book>() {
            @Override
            public Book transform(Map<String, String> entry) {
                Book book = new Book(entry.get("title"), entry.get("summary"), entry.get("genre"));
                if(!StringUtils.isEmpty(entry.get("id")))
                    book.setId(Long.valueOf(entry.get("id")));
                return book;
            }
        }));
    }

}