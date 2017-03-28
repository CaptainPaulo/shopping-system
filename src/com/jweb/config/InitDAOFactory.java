package com.jweb.config;

import com.jweb.dao.DAOFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by gaetan on 29/12/15.
 */
public class InitDAOFactory implements ServletContextListener {
    private static final String ATT_DAO_FACTORY = "daofactory";
    private DAOFactory daoFactory;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        this.daoFactory = DAOFactory.getInstance();
        servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
    }

    @Override
    public void contextDestroyed( ServletContextEvent event ) {
    }
}
