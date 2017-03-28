package com.jweb.dao;

import com.jweb.beans.News;
import java.util.List;

/**
 * Created by gaetan on 06/01/16.
 */
public interface INewsDao {
    void create(News news) throws DAOException;
    List<News> getAll() throws DAOException;
}
