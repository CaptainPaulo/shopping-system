package com.jweb.forms;

import com.jweb.beans.Product;
import com.jweb.dao.CategoryDao;
import com.jweb.dao.DAOException;
import com.jweb.dao.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by adenis_e on 17-4-7.
 */

public class ProductForm extends Form {
    private ProductDao productDao;
    private CategoryDao categoryDao;

    public ProductForm(ProductDao productDao, CategoryDao categoryDao) {
        super("name",
                "description",
                "price",
                "category-id");
        this.productDao = productDao;
        this.categoryDao = categoryDao;
    }


    public Map<String, String> getErrors() {
        return errors;
    }

    public Product createProduct(HttpServletRequest request) {
        getAllParametersValue(request);
        validateAllEmptyString();
        Product product = new Product();
        product.setName(parametersValue.get("name"));
        product.setDescription(parametersValue.get("description"));
        try {
            validatePrice(parametersValue.get("price"));
        } catch (Exception e) {
            errors.put("price", e.getMessage());
        }
        product.setPrice(Float.parseFloat(parametersValue.get("price")));
        try {
            validateCategory(parametersValue.get("category-id"));
        } catch (Exception e) {
            errors.put("category-id", e.getMessage());
        }
        product.setCategory(Long.parseLong(parametersValue.get("category-id")));
        if (!checkErrors()) {
            try {
                productDao.create(product);
            } catch (DAOException e) {
                result = e.getMessage();
            }
        }
        return product;
    }

    private static void validatePrice(String price) throws Exception {
        float priceInt = Float.parseFloat(price);
        if (priceInt <= 0.0) {
            throw new Exception("Price has to be more than 0");
        }
    }

    private void validateCategory(String category) throws Exception {
        try {
            categoryDao.findById(category);
        } catch (DAOException e) {
            throw new Exception(e);
        }
    }
}
