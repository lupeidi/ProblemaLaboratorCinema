package Domain;

import Domain.Exceptions.MovieValidatorException;

import java.util.Calendar;

public class MovieValidator implements IValidator<Movie> {
    public void validate(Movie movie) {
        if (movie.getPrice() <= 0 ){
            throw new MovieValidatorException("The price must be greater than 0!");
        }

        if (movie.getReleaseYear() < 0 || movie.getReleaseYear() > Calendar.getInstance().get(Calendar.YEAR)+1) {
            throw new MovieValidatorException("The release year is not valid!");
        }
        if (!movie.getId().equals(new StringBuilder(movie.getId()).reverse().toString())) {
            throw new MovieValidatorException("Id must be a palindrome");
        }
    }
}