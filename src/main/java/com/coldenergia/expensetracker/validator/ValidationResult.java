package com.coldenergia.expensetracker.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains validation errors.
 * User: coldenergia
 * Date: 5/12/14
 * Time: 8:51 PM
 */
public class ValidationResult {

    private List<ObjectError> objectErrors = new ArrayList<ObjectError>();

    private List<FieldError> fieldErrors = new ArrayList<FieldError>();

    /**
     * Registers a global error for the validated object.
     * */
    public void reject(String errorCode) {
        objectErrors.add(new ObjectError(errorCode));
    }

    /**
     * Registers an error for a specific field of a validated object.
     * */
    public void rejectValue(String fieldName, String errorCode) {
        fieldErrors.add(new FieldError(fieldName, errorCode));
    }

    /**
     * @return {@code true}, if there were any errors registered, {@code false} otherwise.
     * */
    public boolean hasErrors() {
        boolean hasNoErrors = objectErrors.isEmpty() && fieldErrors.isEmpty();
        return !hasNoErrors;
    }

    public String getAggregatedErrorCodes() {
        return getAggregatedErrorCodes(";");
    }

    public String getAggregatedErrorCodes(String errorCodeSeparator) {
        StringBuilder errorCodeAggregator = new StringBuilder("");
        for (ObjectError error : objectErrors) {
            errorCodeAggregator.append(error).append(errorCodeSeparator);
        }
        for (FieldError error : fieldErrors) {
            errorCodeAggregator.append(error).append(errorCodeSeparator);
        }
        deleteLastErrorCodeSeparator(errorCodeAggregator, errorCodeSeparator);
        return errorCodeAggregator.toString();
    }

    private void deleteLastErrorCodeSeparator(StringBuilder aggregator, String separator) {
        if (hasErrors()) {
            aggregator.delete(aggregator.length() - separator.length(), aggregator.length());
        }
    }

    public static class FieldError extends ObjectError {

        private String fieldName;

        private FieldError(String fieldName, String errorCode) {
            super(errorCode);
            this.fieldName = fieldName;
        }

        private String getFieldName() {
            return fieldName;
        }

        private void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public String toString() {
            return super.toString();
        }

    }

    public static class ObjectError {

        private String errorCode;

        public ObjectError(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        @Override
        public String toString() {
            return String.valueOf(errorCode);
        }

    }

}
