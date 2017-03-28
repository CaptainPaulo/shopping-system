package com.jweb.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by gaetan on 04/01/16.
 */
public class RestrictionFilter implements Filter {
    public void init( FilterConfig config ) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpSession session = servletRequest.getSession();
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        if (session.getAttribute("admin") == null) {
            servletResponse.sendRedirect(servletRequest.getContextPath() + "/login");
        }
        else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }
}