package Service;

import Domain.Movie;
import Domain.Reservation;
import Repository.IRepository;
import Repository.MovieRepository;

import java.util.*;

public class MovieService {
    private IRepository<Movie> repository;
    private IRepository<Reservation> reservationRepository;

    private Stack<UndoRedoOperation<Movie>> undoOperations = new Stack<>();
    private Stack<UndoRedoOperation<Movie>> redoOperations = new Stack<>();

    public MovieService(IRepository<Movie> repository, IRepository<Reservation> reservationRepository){
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }


    /**
     * adds or updates a movie
     * @param id movie
     * @param title movie
     * @param releaseYear movie
     * @param price movie
     * @param airing if it is on the program
     */
    public void addOrUpdate(String id, String title, int releaseYear, double price, boolean airing){
        Movie movie = new Movie(id, title, releaseYear, price, airing);
        repository.upsert(movie);
        undoOperations.add(new AddOperation<>(repository, movie));
        redoOperations.clear();
    }

    /**
     * removes a movie
     * @param id movie to remove
     */
    public void remove(String id){
        Movie movie = repository.getById(id);
        repository.remove(id);
        undoOperations.add(new RemoveOperation<>(repository, movie));
        redoOperations.clear();
    }

    /**
     *
     * @return list of all movies
     */
    public List<Movie> getAll() {
        return repository.getAll();
    }

    /**
     * searches for text in all movies
     * @param text to find
     * @return list of all movies with text found
     */
    public List<Movie> textSearchMovies(String text) {
        List<Movie> found = new ArrayList<>();
        for ( Movie i: repository.getAll() ) {
            if ((i.getId().contains(text) || i.getTitle().contains(text) || Integer.toString(i.getReleaseYear()).contains(text) || Integer.toString(i.getBookings()).contains(text) || Double.toString(i.getPrice()).contains(text) || Boolean.toString(i.isAiring()).contains(text)) && !found.contains(i)) {
                found.add(i);
            }
        }
        return found;
    }

    public List<Movie> sortByReservations() {

        List<Movie> movieList = this.getAll();
        for ( int i=0;i<movieList.size()-1;i++)
            for(int j = i+1; j< movieList.size(); j++)
            {
                if (movieList.get(i).getBookings() < movieList.get(j).getBookings())
                {
                    Movie aux = movieList.get(j);
                    movieList.set(j,movieList.get(i));
                    movieList.set(i,aux);
                }
            }
        return movieList;
    }

    public void undo() {
        if (!undoOperations.empty()) {
            UndoRedoOperation<Movie> lastOperation = undoOperations.pop();
            lastOperation.doUndo();
            redoOperations.add(lastOperation);

        }
    }

    public void redo() {
        if (!redoOperations.empty()) {
            UndoRedoOperation<Movie> lastOperation = redoOperations.pop();
            lastOperation.doRedo();
            undoOperations.add(lastOperation);
        }
    }

}
