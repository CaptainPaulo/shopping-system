package com.jweb.servlets;

import com.jweb.beans.Member;
import com.jweb.forms.MemberUpdateForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gaetan on 08/01/16.
 */
public class UpdateMember extends MemberMainServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberUpdateForm form = new MemberUpdateForm(memberDao);
        Member member = form.updateMember(request);

        request.setAttribute("form", form);
        request.setAttribute("member", member);
        if (member.isNewsletter())
            request.setAttribute("checked", "checked");
        this.getServletContext().getRequestDispatcher("/admin/member.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null)
            response.sendRedirect("/admin/members");
        else {
            Member member = memberDao.findById(request.getParameter("id"));
            request.setAttribute("member", member);
            if (member.isNewsletter())
                request.setAttribute("checked", "checked");
            this.getServletContext().getRequestDispatcher("/admin/member.jsp").forward(request, response);
        }
    }
}