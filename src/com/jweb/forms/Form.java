package com.jweb.forms;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adenis_e on 17-4-7.
 */
abstract class Form {
    protected String result;
    protected Map<String, String> errors = new HashMap<>();
    protected Map<String, String> parametersValue; // Key is the name and value is the value

    protected Form(String... parameters) {
        parametersValue = new HashMap<>();
        result = null;
        for (String parameter : parameters) {
            parametersValue.put(parameter, null);
        }
    }

    public String getResult() {
        return result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    private static void validateEmptyString(String value, String name) throws Exception {
        if (value == null) {
            throw new Exception(name + " field is empty");
        }
    }

    protected void validateAllEmptyString() {
        for (Map.Entry<String, String> entry : parametersValue.entrySet()) {
            try {
                validateEmptyString(entry.getValue(), entry.getKey());
            } catch (Exception e) {
                errors.put(entry.getKey(), e.getMessage());
            }
        }
    }

    private static String getParameterValue(HttpServletRequest request, String parameter) {
        String value = request.getParameter(parameter);
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        return value.trim();
    }

    protected void getAllParametersValue(HttpServletRequest request) {
        for (Map.Entry<String, String> entry : parametersValue.entrySet()) {
            parametersValue.put(entry.getKey(), getParameterValue(request, entry.getKey()));
        }
    }

    protected boolean checkErrors() {
        if (!errors.isEmpty()) {
            result = "Error in the form.";
        }
        return !errors.isEmpty();
    }
}
