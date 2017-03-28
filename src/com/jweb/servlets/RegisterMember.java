package com.jweb.servlets;

import com.jweb.beans.Member;
import com.jweb.forms.RegisterForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gaetan on 29/12/15.
 */
public class RegisterMember extends MemberMainServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RegisterForm form = new RegisterForm(memberDao);
        Member member = form.registerMember(request);

        if (member.isNewsletter())
            request.setAttribute("checked", "checked");
        request.setAttribute("form", form);
        request.setAttribute("member", member);

        this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
