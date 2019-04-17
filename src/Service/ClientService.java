package Service;

import Domain.Client;
import Repository.ClientRepository;
import Repository.IRepository;
import Service.Exceptions.ClientServiceException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class ClientService {
    private IRepository<Client> repository;

    private Stack<UndoRedoOperation<Client>> undoOperations = new Stack<>();
    private Stack<UndoRedoOperation<Client>> redoOperations = new Stack<>();


    public ClientService(IRepository<Client> repository) {
        this.repository = repository;
    }

    /**
     * adds or updates a client card
     * @param id card client
     * @param firstName client
     * @param lastName client
     * @param cnp client
     * @param birthdayDate client
     * @param registrationDate client
     * @param points card
     * @throws ClientServiceException if the CNP is not unique
     */
    public void addOrUpdate (String id, String firstName, String lastName, String cnp, LocalDate birthdayDate, LocalDate registrationDate, int points){

        Client client = new Client(id, firstName, lastName, cnp, birthdayDate, registrationDate, points);
        List<Client> clients = new ArrayList<>(repository.getAll());

        for (Client i : clients) {
            if (cnp.equals(i.getCnp())) {
                throw new ClientServiceException("CNP already exists");
            }
        }
        repository.upsert(client);
        undoOperations.add(new AddOperation<>(repository, client));
        redoOperations.clear();
    }

    /**
     * removes a client card
     * @param id of the card to remove
     */
    public void remove(String id) {
        Client client = repository.getById(id);
        repository.remove(id);
        undoOperations.add(new RemoveOperation<>(repository, client));
        redoOperations.clear();
    }

    /**
     *
     * @return list of all client cards
     */
    public List<Client> getAll() {
        return repository.getAll();
    }

    /**
     * searches text in all client cards
     * @param text to search
     * @return client cards with text
     */
    public List<Client> textSearchClients(String text) {
        List<Client> found = new ArrayList<>();
        for ( Client i: repository.getAll() ) {
            if ((i.getId().contains(text) || i.getFirstName().contains(text) || i.getLastName().contains(text) || i.getCnp().contains(text) ) && !found.contains(i)) {
                found.add(i);
            }
        }
        return found;
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    public List<Client> periodSearchClients (LocalDate start, LocalDate end) {
        List<Client> found = new ArrayList<>();
        for ( Client i: repository.getAll() ) {
            if ( (i.getBirthdayDate().isAfter(start) && i.getBirthdayDate().isBefore(end)) || ( i.getRegistrationDate().isAfter(start) && i.getRegistrationDate().isBefore(end))) {
                found.add(i);
            }
        }
        return found;
    }

    /**
     * adds points to client card
     * @param start period range
     * @param end period range
     * @param bonus points to add
     */
    public void bonusPoints (LocalDate start, LocalDate end, int bonus) {

        List<Client> updatedClients = new ArrayList<>();
        List<Client> actualClients = new ArrayList<>();

        for ( Client i: repository.getAll() ) {
            if (i.getBirthdayDate().getDayOfYear() > start.getDayOfYear() && i.getBirthdayDate().getDayOfYear() < end.getDayOfYear()) {
                Client clientUpdate = new Client(i.getId(),i.getFirstName(), i.getLastName(), i.getCnp(), i.getBirthdayDate(), i.getRegistrationDate(), i.getPoints());
                actualClients.add(clientUpdate);
                i.setPoints(i.getPoints() + bonus );
                repository.upsert(i);
            }
        }
        updatedClients.addAll(repository.getAll());
        undoOperations.add(new UpdateAllClients<Client>(repository,updatedClients, actualClients));
        redoOperations.clear();
    }

    /**
     *
     * @return list of all client cards, sorted by number of points
     */
    public List<Client> sortClientsByPoints() {
        List<Client> orderedClients = repository.getAll();
        orderedClients.sort(Comparator.comparing(Client::getPoints).reversed());
        return orderedClients;
    }

    public void undo() {
        if (! undoOperations.empty()) {
            UndoRedoOperation<Client> lastOperation = undoOperations.pop();
            lastOperation.doUndo();
            redoOperations.add(lastOperation);
        }
    }

    public void redo(){
        if (!redoOperations.empty()) {
            UndoRedoOperation<Client> lastOperation = redoOperations.pop();
            lastOperation.doRedo();
            undoOperations.add(lastOperation);
        }
    }

    public IRepository<Client> getClientRepository() {
        return repository;
    }
}
