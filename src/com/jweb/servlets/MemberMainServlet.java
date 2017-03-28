package com.jweb.servlets;

import com.jweb.dao.DAOFactory;
import com.jweb.dao.MemberDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by gaetan on 04/01/16.
 */
public class MemberMainServlet extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "daofactory";
    protected MemberDao memberDao;

    public void init() throws ServletException {
        this.memberDao = (MemberDao) ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMemberDao();
    }
}