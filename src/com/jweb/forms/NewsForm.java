package com.jweb.forms;

import com.jweb.beans.Member;
import com.jweb.beans.News;
import com.jweb.dao.DAOException;
import com.jweb.dao.MemberDao;
import com.jweb.dao.NewsDao;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetan on 06/01/16.
 */
public class NewsForm {
    private String result;
    private NewsDao newsDao;
    private MemberDao memberDao;
    private Map<String, String> errors = new HashMap<>();

    public NewsForm(NewsDao newsDao, MemberDao memberDao) {
        this.newsDao = newsDao;
        this.memberDao = memberDao;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getResult() {
        return result;
    }

    private String getAuthorName(HttpServletRequest request) {
        String memberID = String.valueOf(request.getSession().getAttribute("admin"));
        Member member = memberDao.findById(memberID);
        return member.getFirstName();
    }

    public News createNews(HttpServletRequest request) {
        String author = getAuthorName(request);
        String title = getParameterValue(request, "title");
        String body = getParameterValue(request, "body");

        News news = new News();
        news.setAuthor(author);
        news.setPublicationDate(new Timestamp(new Date().getTime()));
        try {
            validateTitle(title);
        } catch (Exception e) {
            errors.put("title", e.getMessage());
        }
        news.setTitle(title);
        try {
            validateBody(body);
        } catch (Exception e) {
            errors.put("body", e.getMessage());
        }
        news.setBody(body);

        if (!errors.isEmpty()) {
            result = "Error in the form.";
        }
        else {
            try {
                newsDao.create(news);
                result = "News successfully created.";
            }
            catch (DAOException e) {
                result = e.getMessage();
            }
        }
        return news;
    }

    private static String getParameterValue(HttpServletRequest request, String parameter) {
        String value = request.getParameter(parameter);
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        return value.trim();
    }

    private void validateTitle(String title) throws Exception {
        if (title == null) {
            throw new Exception("Title field is empty.");
        }
    }

    private void validateBody(String body) throws  Exception {
        if (body == null) {
            throw new Exception("Body field is empty.");
        }
    }
}
