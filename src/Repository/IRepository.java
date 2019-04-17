package Repository;

import Domain.Movie;
import Domain.MovieValidator;
import Domain.Entity;

import java.util.List;

    public interface IRepository<T extends Entity> {
        /**
         *
         * @param id of entity
         * @return an entity
         */
        T getById(String id);

        /**
         * adds or updates entity
         * @param entity
         */
        void upsert(T entity);

        /**
         * removes an entity
         * @param id
         */
        void remove(String id);

        /**
         *
         * @return lsit with all entities
         */
        List<T> getAll();
    }

