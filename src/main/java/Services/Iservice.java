package Services;

import java.util.List;

public interface Iservice <T>{
    void add(T t);
    void delete(int idU);
    void update(T t,int idU);
    List<T> readall();
    T readByid(int idU);
}
