package Repository;

import Domain.Client;
import Domain.ClientValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository {
    private Map<String, Client> storage = new HashMap<>();
    private ClientValidator validator;


    public ClientRepository(ClientValidator validator){
        this.validator = validator;
    }

    public Client getById(String Id){
        return storage.get(Id);
    }

    public void add(Client client) {
        if (storage.containsKey(client.getId())) {
            throw new RuntimeException("This client already exists!");
        }
        validator.validate(client);
        storage.put(client.getId(), client);
    }

    public void update(Client client) {
        if (!storage.containsKey(client.getId())) {
            throw new RuntimeException("This client doesn't exists!");
        }
        validator.validate(client);
        storage.put(client.getId(), client);
    }

    public void remove (String id){
        if (!storage.containsKey(id)) {
            throw new RuntimeException("This client doesn't exists!");
        }
        storage.remove(id);
    }

    public List<Client> getAll(){
        return new ArrayList<>(storage.values());
    }


}
