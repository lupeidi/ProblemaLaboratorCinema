package Tests;

import Domain.Client;
import Domain.ClientValidator;
import Repository.IRepository;
import Repository.InMemoryRepository;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest {

    @org.junit.jupiter.api.Test
    void getById() {
        IRepository<Client> repository = new InMemoryRepository<>(new ClientValidator());
        Client client = new Client("151", "prenume", "nume",  "0123456789120", "","",0);
        repository.upsert(client);
        Client found = repository.getById("151");
        assertNotNull(found, "Returned null for existing id!");
        assertEquals("151", found.getId(), String.format("Returned id %s instead of correct id=151!", found.getId()));
        assertEquals("prenume", found.getFirstName(), String.format("Returned firstName=%s instead of %s", found.getFirstName()));
    }

    @org.junit.jupiter.api.Test
    void add() {
    }

    @org.junit.jupiter.api.Test
    void update() {
    }

    @org.junit.jupiter.api.Test
    void remove() {
    }

    @org.junit.jupiter.api.Test
    void getAll() {
    }
}