package com.jweb.forms;

import com.jweb.beans.Member;
import com.jweb.dao.DAOException;
import com.jweb.dao.MemberDao;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetan on 08/01/16.
 */
public class MemberUpdateForm {
    private String result;
    private MemberDao memberDao;
    private Map<String, String> errors = new HashMap<>();

    public MemberUpdateForm(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getResult() {
        return result;
    }

    public Member updateMember(HttpServletRequest request) {
        String email = getParameterValue(request, "email");
        String firstName = getParameterValue(request, "first-name");
        String lastName = getParameterValue(request, "last-name");
        String id = getParameterValue(request, "id");
        String newsletter = getParameterValue(request, "newsletter");

        Member member = new Member();
        member.setId(Long.valueOf(id));
        member.setNewsletter(Boolean.valueOf(newsletter));
        try {
            validateEmail(email);
        } catch (Exception e) {
            errors.put("email", e.getMessage());
        }
        member.setEmail(email);

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

        result = "The member has been successfully updated.";
        if (!errors.isEmpty()) {
            result = "Error in the form.";
        }
        else {
            try {
                memberDao.update(member);
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
