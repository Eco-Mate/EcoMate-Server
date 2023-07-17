package com.greeny.ecomate.utils.api;

public class ApiUtil {

    public static <T> ApiSuccessResult<T> success(String message, T response) {
        return new ApiSuccessResult<>(message, response);
    }

    public static <T> ApiErrorResult<T> error(int code, T message) {
        return new ApiErrorResult<>(code, message);
    }

    public static class ApiSuccessResult<T> {
        private final String message;
        private final T response;

        private ApiSuccessResult(String message, T response) {
            this.message = message;
            this.response = response;
        }

        public T getResponse() {
            return response;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class ApiErrorResult<T> {
        private final int statusCode;
        private final T message;

        private ApiErrorResult(int statusCode, T message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        public int getStatus() {
            return statusCode;
        }

        public T getMessage() {
            return message;
        }
    }

}
