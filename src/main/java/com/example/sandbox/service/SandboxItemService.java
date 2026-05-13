package com.example.sandbox.service;

import com.example.sandbox.model.SandboxItem;
import com.example.sandbox.repository.SandboxItemRepository;
import com.example.sandbox.response.ApiResponse;
import com.example.sandbox.response.BaseMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SandboxItemService {

    private final SandboxItemRepository repository;

    public SandboxItemService(SandboxItemRepository repository) {
        this.repository = repository;
    }

    public List<SandboxItem> getAll() {
        return repository.findAll();
    }

    public SandboxItem getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Page<SandboxItem> getItemsWithPaging(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public ApiResponse<BaseMetaData, SandboxItem> getByIdAsApiResponse(Long id) {
        SandboxItem item = getById(id);

        if (item != null) {
            return new ApiResponse<>(new BaseMetaData(200, true), item);
        }

        BaseMetaData meta = new BaseMetaData(404, false);
        meta.setErrorMessage("Item not found");

        return new ApiResponse<>(meta);
    }
}