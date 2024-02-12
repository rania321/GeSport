package service;

import java.util.List;

public interface IService <T> {
    void add(T t);

    void delete(T t);

    void deleteById(int idC);

    void update(T t);

    void update(commentaireService commentaireService);

    List<T> readAll();

    T readById(int idRec);

}
