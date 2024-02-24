package tn.Gesport.services;

import tn.Gesport.iservices.IService;
import tn.Gesport.models.Reponse;
import tn.Gesport.utils.Database;

import java.net.ConnectException;
import java.sql.Connection;
import java.util.ArrayList;

public class ServiceReponse implements IService<Reponse> {

    private Connection connection;

    public ServiceReponse() {
        connection = Database.getInstance().getConnection();
    }
    @Override
    public void add(Reponse reponse) {

    }

    @Override
    public ArrayList<Reponse> getAll() {
        return null;
    }

    @Override
    public void update(Reponse reponse) {

    }

    @Override
    public boolean delete(Reponse reponse) {
        return false;
    }
}
