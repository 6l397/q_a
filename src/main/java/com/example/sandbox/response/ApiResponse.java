package com.example.sandbox.response;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse<M, D> {

    private M meta;
    private List<D> data;

    public ApiResponse() {
        this.data = new ArrayList<>();
    }

    public ApiResponse(M meta) {
        this.meta = meta;
        this.data = new ArrayList<>();
    }

    public ApiResponse(M meta, D data) {
        this.meta = meta;
        this.data = new ArrayList<>();
        if (data != null) {
            this.data.add(data);
        }
    }

    public ApiResponse(M meta, List<D> data) {
        this.meta = meta;
        this.data = data == null ? new ArrayList<>() : data;
    }

    public M getMeta() {
        return meta;
    }

    public List<D> getData() {
        return data;
    }

    public void setMeta(M meta) {
        this.meta = meta;
    }

    public void setData(List<D> data) {
        this.data = data;
    }
}