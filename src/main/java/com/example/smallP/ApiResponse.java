package com.example.smallP;
import java.util.List;
public class ApiResponse <T>{
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
