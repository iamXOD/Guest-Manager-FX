/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import javafx.collections.ObservableList;

/**
 *
 * @author HAROLD
 * @param <Object>
 */
public interface DAOInterface<Object> {

    public Object get(String ID);

    public ObservableList<Object> getAll();

    public boolean add(Object o);

    public boolean update(Object o, String ID);

    public boolean delete(String ID);
}
