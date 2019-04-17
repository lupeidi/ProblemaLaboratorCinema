package Service;

import Domain.Entity;
import Repository.IRepository;

import java.util.List;

public class UpdateAllClients<T extends Entity> extends UndoRedoOperation {
    private List<T> updatedList;
    private List<T> actualList;

    UpdateAllClients (IRepository<T> repository, List<T> updatedList, List<T> actualList) {
        super(repository);
        this.updatedList = updatedList;
        this.actualList = actualList;
    }

    public void doUndo() {
        for (T updatedEntity : actualList) {
            repository.upsert(updatedEntity);
        }
    }

    public void doRedo() {
        for (T updatedEntity : updatedList) {
            repository.upsert(updatedEntity);
        }
    }
}
