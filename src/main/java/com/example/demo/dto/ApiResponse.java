package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("OK", "성공적으로 처리되었습니다.", data);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>("OK", "성공적으로 처리되었습니다.", null);
    }

    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>("Error", message, null);
    }
}
