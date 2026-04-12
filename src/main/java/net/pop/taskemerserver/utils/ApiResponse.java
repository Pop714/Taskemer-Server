package net.pop.taskemerserver.utils;

public record ApiResponse<T>(
        String message,
        int statusCode,
        T data
) { }