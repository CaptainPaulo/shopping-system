package com.jweb.servlets;

import com.jweb.beans.Product;
import com.jweb.beans.Review;
import com.jweb.dao.DAOFactory;
import com.jweb.dao.ProductDao;
import com.jweb.dao.ReviewDao;
import com.jweb.forms.RegisterForm;
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
    public static final String CONF_DAO_FACTORY = "daofactory";
    protected ProductDao productDao;
    protected ReviewDao reviewDao;

    public void init() throws ServletException {
        this.productDao = (ProductDao) ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getProductDao();
        this.reviewDao = (ReviewDao) ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getReviewDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ReviewForm form = new ReviewForm(reviewDao);
        Review review = form.createReview(request);

        request.setAttribute("form", form);
        request.setAttribute("review", review);

        Product product = this.productDao.findById("1");
        List<Review> reviews = this.reviewDao.getAll();

        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);

        this.getServletContext().getRequestDispatcher("/product.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = this.productDao.findById("1");
        List<Review> reviews = this.reviewDao.getAll();

        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);

        this.getServletContext().getRequestDispatcher("/product.jsp").forward(request, response);
    }
}
