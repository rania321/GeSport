package Services;

import java.util.List;

public interface Iservice <T>{
    void add(T t);
    void delete(T t);
    void update(T t);
    List<T> readall();
    T readById(int idU);
}
