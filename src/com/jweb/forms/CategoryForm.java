package com.jweb.forms;

import com.jweb.beans.Category;
import com.jweb.dao.CategoryDao;
import com.jweb.dao.DAOException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by adenis_e on 17-4-7.
 */
public class CategoryForm extends Form {
    private CategoryDao categoryDao;

    public CategoryForm(CategoryDao categoryDao) {
        super("name");
        this.categoryDao = categoryDao;
    }

    public Category createCategory(HttpServletRequest request) {
        getAllParametersValue(request);
        validateAllEmptyString();
        Category category = new Category();
        category.setName(parametersValue.get("name"));
        if (!checkErrors()) {
            try {
                categoryDao.create(category);
            } catch (DAOException e) {
                result = e.getMessage();
            }
        }
        return category;
    }
}
