package com.jweb.forms;

import com.jweb.beans.Pannier;
import com.jweb.dao.DAOException;
import com.jweb.dao.PannierDao;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by adenis_e on 17-4-7.
 */
public class PannierForm extends Form {
    private PannierDao pannierDao;

    public PannierForm(PannierDao pannierDao) {
        super("member-id",
                "product-id",
                "number-of-product",
                "buy");
        this.pannierDao = pannierDao;
    }

    public Pannier createPannier(HttpServletRequest request) {
        getAllParametersValue(request);
        validateAllEmptyString();
        String product = parametersValue.get("product-id");
        String member = parametersValue.get("member-id");
        String number = parametersValue.get("number-of-product");
        String buy = parametersValue.get("buy");

        Pannier pannier = new Pannier();
        try {
            validateLong(product, "product");
        } catch (Exception e) {
            errors.put("product", e.getMessage());
        }
        try {
            validateLong(member, "member");
        } catch (Exception e) {
            errors.put("member", e.getMessage());
        }
        try {
            validateLong(number, "number");
        } catch (Exception e) {
            errors.put("number", e.getMessage());
        }
        try {
            validateBool(buy);
        } catch (Exception e) {
            errors.put("buy" , e.getMessage());
        }
        pannier.setMember(Long.getLong(member));
        pannier.setProduct(Long.getLong(product));
        pannier.setNumberOfProduct(Long.getLong(number));
        pannier.setBuy(Boolean.getBoolean(buy));
        if (!checkErrors()) {
            try {
                pannierDao.create(pannier);
            } catch (DAOException e) {
                result = e.getMessage();
            }
        }
        return pannier;
    }

    private static void validateLong(String number, String name) throws Exception {
        long longNumber = Long.getLong(number);
        if (longNumber <= 0) {
            throw new Exception(name + " have to be more than 0");
        }
    }

    private static void validateBool(String bool) throws Exception {
        boolean buy = Boolean.getBoolean(bool);
        if (buy) {
            throw new Exception("The buy can't be true on create");
        }
    }
}
