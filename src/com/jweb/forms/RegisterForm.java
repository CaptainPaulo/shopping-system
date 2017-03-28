package com.jweb.forms;

import com.jweb.beans.Member;
import com.jweb.dao.DAOException;
import com.jweb.dao.MemberDao;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetan on 30/12/15.
 */
public class RegisterForm {
    private String result;
    private MemberDao memberDao;
    private Map<String, String> errors = new HashMap<>();

    public RegisterForm(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getResult() {
        return result;
    }

    public Member registerMember(HttpServletRequest request) {
        String email = getParameterValue(request, "email");
        String firstName = getParameterValue(request, "first-name");
        String lastName = getParameterValue(request, "last-name");
        String password = getParameterValue(request, "password");
        String passwordConfirmation = getParameterValue(request, "confirmation-password");
        String newsletter = getParameterValue(request, "newsletter");

        Member member = new Member();
        member.setNewsletter(Boolean.valueOf(newsletter));

        try {
            validateEmail(email);
        } catch (Exception e) {
            errors.put("email", e.getMessage());
        }
        member.setEmail(email);

        try {
            validatePassword(password, passwordConfirmation);
        } catch (Exception e) {
            errors.put("password", e.getMessage());
        }
        member.setPassword(password);

        try {
            validateFirstName(firstName);
        } catch (Exception e) {
            errors.put("firstName", e.getMessage());
        }
        member.setFirstName(firstName);

        try {
            validateLastName(lastName);
        } catch (Exception e) {
            errors.put("lastName", e.getMessage());
        }
        member.setLastName(lastName);

        result = "You have been successfully registered.";
        if (!errors.isEmpty()) {
            result = "Error in the form.";
        }
        else {
            try {
                memberDao.create(member);
            }
            catch (DAOException e) {
                result = e.getMessage();
            }
        }
        return member;
    }

    private static String getParameterValue(HttpServletRequest request, String parameter) {
        String value = request.getParameter(parameter);
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        return value.trim();
    }

    private void validateEmail(String email) throws Exception {
        if (email != null) {
            if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                throw new Exception("The email address format is not valid.");
            }
        }
        else {
            throw new Exception("The email field is empty.");
        }
    }

    private void validatePassword(String password, String confirmation) throws Exception {
        if (password != null && confirmation != null) {
            if (!password.equals(confirmation)) {
                throw new Exception("Passwords are different.");
            }
            if (password.length() < 4) {
                throw new Exception("The password is too short (min 4 characters).");
            }
        }
        else {
            throw new Exception("Password/confirmation password is/are empty.");
        }
    }

    private void validateFirstName(String firstName) throws Exception {
        if (firstName == null) {
            throw new Exception("First name field is empty.");
        }
    }

    private void validateLastName(String lastName) throws  Exception {
        if (lastName == null) {
            throw new Exception("Last name field is empty.");
        }
    }
}
