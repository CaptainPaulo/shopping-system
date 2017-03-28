package com.jweb.forms;

import com.jweb.beans.Member;
import com.jweb.dao.MemberDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetan on 05/01/16.
 */
public class LoginForm {

    public LoginForm(MemberDao memberDao) {
        this.result = null;
        this.memberDao = memberDao;
    }

    private String result;
    private Map<String, String> errors = new HashMap<String, String>();
    private MemberDao memberDao;

    public String getResult() {
        return result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public Member connectMember(HttpServletRequest request) {
        String email = getFieldValue(request, "email");
        String password = getFieldValue(request, "password");
        Member member = null;

        try {
            validateEmail(email);
        } catch (Exception e) {
            errors.put("email", e.getMessage());
        }
        try {
            validatePassword(password);
        } catch (Exception e) {
            errors.put("password", e.getMessage());
        }
        if (!memberDao.emailAndPasswordMatch(email, password) && errors.isEmpty()) {
            result = "Email and password do not match.";
        }
        member = memberDao.findByEmail(email);
        if (member != null) {
            if (!member.isAdmin() && errors.isEmpty() && result == null) {
                result = "This member is not an admin.";
            }
        }
        if (errors.isEmpty() && result == null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", member.getId());
            result = "You have been successfully connected.";
        }
        else {
            member = new Member();
            member.setId(-1);
            member.setPassword(password);
            member.setEmail(email);
        }
        return member;
    }

    private void validateEmail(String email) throws Exception {
        if (email != null && !email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
            throw new Exception("Please enter a valid mail address.");
        }
        if (email == null) {
            throw new Exception("The mail address is empty.");
        }
    }

    private void validatePassword(String motDePasse) throws Exception {
        if (motDePasse == null) {
            throw new Exception("The password is empty.");
        }
    }

    private static String getFieldValue(HttpServletRequest request, String nomChamp) {
        String value = request.getParameter(nomChamp);
        if (value == null || value.trim().length() == 0) {
            return null;
        } else {
            return value;
        }
    }
}
