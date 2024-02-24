package tn.Gesport.iservices;

import java.util.ArrayList;

public interface IService <T> {

    public  void add(T t);
    public ArrayList<T> getAll();
    public void update(T t);
    public boolean delete(T t);
}
