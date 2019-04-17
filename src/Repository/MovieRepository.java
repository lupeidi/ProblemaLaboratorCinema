package Repository;

import Domain.Movie;
import Domain.MovieValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieRepository {
    private Map<String, Movie> storage = new HashMap<>();
    private MovieValidator validator;

    public MovieRepository(MovieValidator validator){
        this.validator = validator;
    }

    public Movie getById(String Id){
        return storage.get(Id);
    }

    public void add(Movie movie) {
        if (storage.containsKey(movie.getId())) {
            throw new RuntimeException(String.format("There already is a movie with that id"));
        }
        validator.validate(movie);
        storage.put(movie.getId(), movie);
    }

    public void update(Movie movie) {
        if (!storage.containsKey(movie.getId())) {
            throw new RuntimeException(String.format("There is no movie with that id"));
        }
        validator.validate(movie);
        storage.put(movie.getId(), movie);
    }

    public void remove(String id) {
        if (!storage.containsKey(id)) {
            throw new RuntimeException(String.format("There is no movie with that id"));
        }
        storage.remove(id);
    }

    public List<Movie> getAll() {
        return new ArrayList<>(storage.values());
    }


}
