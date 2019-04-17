package Service;

import Domain.Entity;
import Repository.IRepository;

public class RemoveOperation<T extends Entity> extends UndoRedoOperation {

    private T removedEntity;

    RemoveOperation(IRepository<T> repository, T removedEntity) {
        super(repository);
        this.removedEntity = removedEntity;
    }

    public void doUndo() {
        repository.upsert(removedEntity);
    }

    public void doRedo() {
    repository.remove(removedEntity.getId());
    }
}
