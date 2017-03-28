package com.jweb.dao;

import com.jweb.beans.Member;

import java.util.List;

/**
 * Created by gaetan on 29/12/15.
 */
public interface IMemberDao {
    void create(Member member) throws DAOException;
    Member findByEmail(String email) throws DAOException;
    Member findById(String id) throws DAOException;
    List<Member> getAll() throws DAOException;
    boolean emailAndPasswordMatch(String email, String password) throws DAOException;
    void delete(String id);
    void update(Member member);
}