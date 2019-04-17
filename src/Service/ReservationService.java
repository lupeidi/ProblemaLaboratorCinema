package Service;

import Domain.Client;
import Domain.Movie;
import Domain.Reservation;
import Repository.ClientRepository;
import Repository.IRepository;
import Repository.MovieRepository;
import Service.Exceptions.ReservationServiceException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReservationService {
    private IRepository<Reservation>  repository;
    private IRepository<Movie> movieRepository;
    private IRepository<Client> clientRepository;

    private Stack<UndoRedoOperation<Reservation>> undoOperations = new Stack<>();
    private Stack<UndoRedoOperation<Reservation>> redoOperations = new Stack<>();


    public ReservationService(IRepository<Reservation> repository, IRepository<Movie> movieRepository, IRepository<Client>  clientRepository) {
        this.repository = repository;
        this.movieRepository = movieRepository;
        this.clientRepository = clientRepository;
    }

    /**
     *
     * @param id
     * @param id_movie
     * @param id_client
     * @param date
     * @param hour
     */
    public void addOrUpdate(String id, String id_movie, String id_client, LocalDate date, LocalTime hour){
        Movie movieToReserve = new Movie(movieRepository.getById(id_movie));

        Client clientCard = clientRepository.getById(id_client);

        if (movieToReserve == null) {
            throw new ReservationServiceException("There is no movie with that id.");
        }
        if (!movieToReserve.isAiring()) {
            throw  new ReservationServiceException("The movie is not airing!");
        }
        Reservation reservation = new Reservation(id, id_movie, id_client, date, hour);

        repository.upsert(reservation);
        movieToReserve.setBookings(movieToReserve.getBookings() + 1);

        movieRepository.upsert(movieToReserve);
        if ( clientCard == null ) {
            clientCard.setPoints((int)(clientCard.getPoints() + (movieToReserve.getPrice() / 10)));
        }

        undoOperations.add(new AddOperation<>(repository, reservation));
        redoOperations.clear();
    }


    /**
     * removes a reservation
     * @param id of reservation to remove
     */
    public void remove(String id) {

        Reservation reservation = repository.getById(id);
        repository.remove(id);

        Client client = clientRepository.getById((reservation.getId_client()));
        clientRepository.upsert(client);

        undoOperations.add(new RemoveOperation<>(repository, reservation));
        redoOperations.clear();
    }

    /**
     *
     * @return list of all reservations
     */
    public List<Reservation> getAll() {
        return repository.getAll();
    }

    /**
     * search in all reservations
     * @param text to find
     * @return list of reservations with text
     */
    public List<Reservation> textSearchReservations(String text) {
        List<Reservation> found = new ArrayList<>();
        for ( Reservation i: repository.getAll() ) {
            if ((i.getId().contains(text) || i.getId_client().contains(text) || i.getId_movie().contains(text) || i.getId_movie().contains(text) ) && !found.contains(i)) {
                found.add(i);
            }
        }
        return found;
    }

    /**
     * searches for all reservations in a period range
     * @param start range of time to search
     * @param end range of time to search
     * @return list with reservations in the range
     */
    public List<Reservation> periodSearchReservations (LocalTime start, LocalTime end) {
        List<Reservation> found = new ArrayList<>();
        for ( Reservation i: repository.getAll() ) {
            if ( i.getHour().isAfter(start) && i.getHour().isBefore(end)) {
                found.add(i);
            }
        }
        return found;
    }

    /**
     * removes all reservations in a period range
     * @param start range of time to remove
     * @param end range of time to remove
     */
    public void periodRemoveReservations (LocalDate start, LocalDate end) {
        List<Reservation> removePeriod = new ArrayList<>();

        for ( Reservation i: repository.getAll() ) {
            if ( i.getDate().isAfter(start) && i.getDate().isBefore(end) ) {
                repository.remove((i.getId()));
            }
        }

        undoOperations.add(new RemoveAllReservations<>(repository,removePeriod));
        redoOperations.clear();
    }

    /**
     *
     */
    public void undo() {
        if (!undoOperations.empty()) {
            UndoRedoOperation<Reservation> lastOperation = undoOperations.pop();
            lastOperation.doUndo();
            redoOperations.add(lastOperation);
        }
    }

    /**
     *
     */
    public void redo() {
        if(!redoOperations.empty()) {
            UndoRedoOperation<Reservation> lastOperation = redoOperations.pop();
            lastOperation.doRedo();
            undoOperations.add(lastOperation);
        }
    }
}
