package database;

import java.util.ArrayList;

interface DAO<T> {
    boolean add(T item);

    T get(String key);

    void remove(String key);

    boolean update(T item);

    ArrayList<T> list();
}
