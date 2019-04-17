package Repository;

import Domain.Reservation;
import Domain.ReservationValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationRepository {

    private Map<String, Reservation> storage = new HashMap<>();
    private ReservationValidator validator;

    public ReservationRepository(ReservationValidator validator) {
        this.validator = validator;
    }

    public Reservation getById(String id) {
        return storage.get(id);
    }

    /**
     * Adds or updates a reservation if it already exists.
     * @param reservation the transaction to add or update.
     */
    public void add(Reservation reservation) {
        if (storage.containsKey(reservation.getId())) {
            throw new RuntimeException(String.format("There already is a reservation with that id"));
        }
        validator.validate(reservation);
        storage.put(reservation.getId(), reservation);
    }

    public void update(Reservation reservation) {
        if (!storage.containsKey(reservation.getId())) {
            throw new RuntimeException(String.format("There is no reservation with that id"));
        }
        validator.validate(reservation);
        storage.put(reservation.getId(), reservation);
    }

    /**
     * Removes a reservation with a given id.
     * @param id the id.
     * @throws RuntimeException if there is no transaction with the given id.
     */
    public void remove(String id) {
        if (!storage.containsKey(id)) {
            throw new RuntimeException("There is no reservation with the given id to remove.");
        }

        storage.remove(id);
    }

    public List<Reservation> getAll() {
        return new ArrayList<>(storage.values());
    }

}
