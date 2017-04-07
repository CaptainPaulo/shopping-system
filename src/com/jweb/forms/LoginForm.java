package com.jweb.forms;

import com.jweb.beans.Member;
import com.jweb.dao.MemberDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by gaetan on 05/01/16.
 */
public class LoginForm extends Form {

    public LoginForm(MemberDao memberDao) {
        super("email", "password");
        this.memberDao = memberDao;
    }

    private MemberDao memberDao;

    public Member connectMember(HttpServletRequest request) {
        getAllParametersValue(request);
        validateAllEmptyString();
        String email = parametersValue.get("email");
        String password = parametersValue.get("password");

        try {
            validateEmail(email);
        } catch (Exception e) {
            errors.put("email", e.getMessage());
        }
        if (!memberDao.emailAndPasswordMatch(email, password) && errors.isEmpty()) {
            result = "Email and password do not match.";
        }
        Member member = memberDao.findByEmail(email);
        if (member != null) {
            if (!member.isAdmin() && errors.isEmpty() && result == null) {
                result = "This member is not an admin.";
            }
        }
        if (errors.isEmpty() && result == null) {
            HttpSession session = request.getSession();
            assert member != null;
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
        if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
            throw new Exception("Please enter a valid mail address.");
        }
    }
}
