package com.jweb.servlets;

import com.jweb.beans.Member;
import com.jweb.forms.LoginForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gaetan on 05/01/16.
 */
public class ConnectMember extends MemberMainServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginForm form = new LoginForm(memberDao);
        Member member = form.connectMember(request);

        request.setAttribute("form", form);
        request.setAttribute("member", member);
        if (member.getId() != -1)
            response.sendRedirect("/admin/members");
        else
            this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = new Member();
        request.setAttribute("member", member);
        this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
