package com.jweb.servlets;

import com.jweb.beans.News;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.NewsDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by gaetan on 10/01/16.
 */
public class ListNews extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "daofactory";
    protected NewsDao newsDao;

    public void init() throws ServletException {
        this.newsDao = (NewsDao) ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getNewsDao();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<News> news = newsDao.getAll();
        request.setAttribute("news", news);
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}