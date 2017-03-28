package com.jweb.servlets;

import com.jweb.dao.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by gaetan on 08/01/16.
 */
public class DeleteMember extends MemberMainServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            memberDao.delete(request.getParameter("id"));
        }
        catch (DAOException e) {
            request.setAttribute("deleteError", e.getMessage());
        }
        response.sendRedirect("/admin/members");
    }
}
