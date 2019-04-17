package Service;

import Domain.Entity;
import Repository.IRepository;

import java.util.List;

public class RemoveAllReservations<T extends Entity> extends UndoRedoOperation {
    private List<T> removedList;

    RemoveAllReservations(IRepository<T> repository, List<T> removedList) {
        super(repository);
        this.removedList = removedList;
    }

    public void doUndo() {
        for (T removedEntity : removedList ) {
            repository.upsert(removedEntity);
        }
    }

    public void doRedo() {
        for (T removedEntity : removedList ) {
            repository.remove(removedEntity.getId());
        }
    }
}
