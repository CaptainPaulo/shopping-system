package com.jweb.servlets;

import com.jweb.beans.Category;
import com.jweb.beans.Product;
import com.jweb.beans.Review;
import com.jweb.dao.*;
import com.jweb.forms.ReviewForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by gaetan on 10/01/16.
 */
public class ShowProduct extends HttpServlet {
    private static final String CONF_DAO_FACTORY = "daofactory";
    private ProductDao productDao;
    private ReviewDao reviewDao;
    private CategoryDao categoryDao;

    public void init() throws ServletException {
        this.productDao = (ProductDao) ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProductDao();
        this.reviewDao = (ReviewDao) ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getReviewDao();
        this.categoryDao = (CategoryDao) ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getCategoryDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReviewForm form = new ReviewForm(reviewDao);
        Review review = form.createReview(request);

        request.setAttribute("form", form);
        request.setAttribute("review", review);

        if (review.getId() == -1) {
            response.sendRedirect(request.getContextPath() + "/jweb_war_exploded/product");
        } else {
            this.getServletContext().getRequestDispatcher("/product.jsp").forward(request, response);
        }
//        Product product = this.productDao.findById("1");
//        List<Review> reviews = this.reviewDao.getAll();
//        Category category = this.categoryDao.findById(String.valueOf(product.getCategory()));
//
//        request.setAttribute("product", product);
//        request.setAttribute("reviews", reviews);
//        request.setAttribute("category", category);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = this.productDao.findById("1");
        List<Review> reviews = this.reviewDao.getAll();
        Category category = this.categoryDao.findById(String.valueOf(product.getCategory()));

        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);
        request.setAttribute("category", category);

        this.getServletContext().getRequestDispatcher("/product.jsp").forward(request, response);
    }
}
