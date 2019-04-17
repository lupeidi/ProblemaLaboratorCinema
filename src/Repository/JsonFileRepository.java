package Repository;

import Domain.Entity;
import Domain.IValidator;
import Repository.Exceptions.RepositoryException;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFileRepository<T extends Entity> implements IRepository<T> {
    private IValidator<T> validator;
    private String filename;
    private Map<String, T> storage = new HashMap<>();
    private Type type;

    public JsonFileRepository(IValidator<T> validator, String filename, Type type) {
        this.validator = validator;
        this.filename = filename;
        this.type = type;
    }

    private void loadFile(){
        storage.clear();
        Gson gson = new Gson();
        try (FileReader in = new FileReader(filename)) {
            try (BufferedReader bufferedReader = new BufferedReader(in)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    T entity = gson.fromJson(line, type);
                    storage.put(entity.getId(), entity);
                }
            }
        } catch (Exception ex) {
            throw new RepositoryException("Load from file error, " + ex.getMessage());
        }
    }

    private void writeFile() {
        Gson gson = new Gson();
        try (FileWriter out = new FileWriter(filename)) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(out)) {
                for (T entity : storage.values()) {
                    bufferedWriter.write(gson.toJson(entity));
                    bufferedWriter.newLine();
                }
            }
        } catch (Exception ex) {
            System.out.println("Write to file error: " + ex.getMessage());
        }
    }

    @Override
    public T getById(String id) {
        loadFile();
        return storage.get(id);
    }

    @Override
    public void upsert(T entity) {
        loadFile();
        validator.validate(entity);
        storage.put(entity.getId(), entity);
        writeFile();
    }

    @Override
    public void remove(String id) {
        loadFile();
        if (!storage.containsKey(id)) {
            throw new RepositoryException("There is no entity with the given id to remove!");
        }
        storage.remove(id);
        writeFile();
    }

    @Override
    public List<T> getAll() {
        loadFile();
        return new ArrayList<>(storage.values());
    }


}
