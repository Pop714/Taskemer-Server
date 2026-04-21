package net.pop.taskemer.utils;

public record ApiResponse<T>(
    String message,
    int statusCode,
    T data
) { }
