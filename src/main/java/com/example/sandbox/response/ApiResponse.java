package com.example.sandbox.response;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse<M, D> extends BaseMetaData {

    private M meta;
    private List<D> data;

    public ApiResponse() {
    }

    public ApiResponse(M meta, D data) {
        this.meta = meta;
        this.data = new ArrayList<>();
        this.data.add(data);
    }

    public ApiResponse(M meta) {
        this.meta = meta;
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