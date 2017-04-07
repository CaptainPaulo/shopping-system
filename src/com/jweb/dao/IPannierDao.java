package com.jweb.dao;

import com.jweb.beans.Pannier;

import java.util.List;

/**
 * Created by adenis_e on 17-4-6.
 */
public interface IPannierDao {
    List<Pannier> getAll() throws DAOException;
    List<Pannier> getByBuy(boolean buy, String id) throws DAOException;
    void create(Pannier pannier) throws DAOException;
    void delete(String id) throws DAOException;
    void update(Pannier pannier) throws DAOException;
    Pannier findById(String id) throws DAOException;
}
