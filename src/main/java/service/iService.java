package service;

public interface iService <T> {
    void add(T t);

    void delete(T t);

    void update(T t);

    java.util.List<T> readAll();

    T readById(int i);
}