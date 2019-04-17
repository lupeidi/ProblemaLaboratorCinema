package Tests;

import Domain.Movie;
import Domain.MovieValidator;
import Repository.IRepository;
import Repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovieRepositoryTest  {
    @Test
    void getById() {
        IRepository<Movie> repository = new InMemoryRepository<>(new MovieValidator());
        Movie movie = new Movie("151", "Film", 2018,  20, true);
        repository.upsert(movie);
        Movie found = repository.getById("151");
        assertNotNull(found, "Returned null for existing id!");
        assertEquals("151", found.getId(), String.format("Returned id %s instead of correct id=151!", found.getId()));
        assertEquals("Film", found.getTitle(), String.format("Returned title=%s instead of %s", found.getTitle()));
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void remove() {
    }

    @Test
    void getAll() {
    }
}
