package org.example.Service;

import java.util.List;

public interface IService <T>{
    void add (T a);
    void delete (T a);
    void update (T a);
    List<T> readAll();
     T readById(int id);
}
