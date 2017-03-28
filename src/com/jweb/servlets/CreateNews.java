package com.jweb.servlets;

import com.jweb.beans.Member;
import com.jweb.beans.News;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.MemberDao;
import com.jweb.dao.NewsDao;
import com.jweb.forms.NewsForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gaetan on 06/01/16.
 */
public class CreateNews extends MemberMainServlet {
    private NewsDao newsDao;

    public void init() throws ServletException {
        this.memberDao = (MemberDao) ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getMemberDao();
        this.newsDao = (NewsDao) ((DAOFactory)getServletContext().getAttribute(CONF_DAO_FACTORY)).getNewsDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NewsForm form = new NewsForm(newsDao, memberDao);
        News news = form.createNews(request);

        request.setAttribute("form", form);
        request.setAttribute("news", news);

        this.getServletContext().getRequestDispatcher("/admin/create-news.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/admin/create-news.jsp").forward(request, response);
    }
}
