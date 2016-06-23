package database;

import java.util.ArrayList;

public interface DAO<T> {
	public boolean add(T item);

	public T get(String key);

	public void remove(String key);

	public boolean update(T item);

	public ArrayList<T> list();
}
