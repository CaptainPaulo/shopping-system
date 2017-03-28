package com.jweb.forms;

import com.jweb.beans.Review;
import com.jweb.dao.DAOException;
import com.jweb.dao.ReviewDao;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaetan on 10/01/16.
 */
public class ReviewForm {
    private String result;
    private ReviewDao reviewDao;
    private Map<String, String> errors = new HashMap<>();

    public ReviewForm(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getResult() {
        return result;
    }

    public Review createReview(HttpServletRequest request) {
        String author = getParameterValue(request, "author");
        String description = getParameterValue(request, "description");
        String note =getParameterValue(request, "note");
        Long productId = Long.valueOf(getParameterValue(request, "product-id"));

        Review review = new Review();
        review.setProductId(productId);
        try {
            validateAuthor(author);
        } catch (Exception e) {
            errors.put("author", e.getMessage());
        }
        review.setAuthor(author);

        try {
            validateDescription(description);
        } catch (Exception e) {
            errors.put("description", e.getMessage());
        }
        review.setDescription(description);

        try {
            validateNote(note);
        } catch (Exception e) {
            errors.put("note", e.getMessage());
        }
        if (note != null)
            review.setNote(Integer.parseInt(getParameterValue(request, "note")));

        result = "Review successfully posted.";
        if (!errors.isEmpty()) {
            result = "Error in the form.";
        }
        else {
            try {
                reviewDao.create(review);
            }
            catch (DAOException e) {
                result = e.getMessage();
            }
        }
        return review;
    }

    private static String getParameterValue(HttpServletRequest request, String parameter) {
        String value = request.getParameter(parameter);
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        return value.trim();
    }

    private void validateAuthor(String author) throws Exception {
        if (author == null) {
            throw new Exception("Author field is empty.");
        }
    }

    private void validateNote(String note) throws Exception {
        if (note == null) {
            throw new Exception("Note field is empty.");
        }
        else {
            int noteInt = Integer.parseInt(note);
            if (noteInt < 0 || noteInt > 5)
                throw new Exception("Mark has to be between 0 and 5.");
        }
    }

    private void validateDescription(String description) throws Exception {
        if (description == null) {
            throw new Exception("Description field is empty.");
        }
    }
}
