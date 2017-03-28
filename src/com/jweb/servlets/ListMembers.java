package com.jweb.servlets;

import com.jweb.beans.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by gaetan on 04/01/16.
 */
public class ListMembers extends MemberMainServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberDao.getAll();
        request.setAttribute("members", members);
        this.getServletContext().getRequestDispatcher("/admin/members.jsp").forward(request, response);
    }
}
