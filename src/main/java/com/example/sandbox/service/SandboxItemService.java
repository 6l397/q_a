package com.example.sandbox.service;

import com.example.sandbox.model.SandboxItem;
import com.example.sandbox.repository.SandboxItemRepository;
import com.example.sandbox.request.ItemPageRequest;
import com.example.sandbox.response.ApiResponse;
import com.example.sandbox.response.BaseMetaData;
import com.example.sandbox.response.PaginationMetaData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ApiResponse<PaginationMetaData, SandboxItem> getItemsPage(ItemPageRequest request) {
        List<SandboxItem> allItems = repository.findAll();

        int size = request.size();
        int requestedPage = request.page();
        long totalElements = allItems.size();

        if (size <= 0) {
            PaginationMetaData meta = new PaginationMetaData(
                    400,
                    false,
                    "Page size must be greater than 0",
                    0,
                    size,
                    totalElements,
                    0,
                    true,
                    true
            );

            return new ApiResponse<>(meta, new ArrayList<>());
        }

        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (totalElements == 0) {
            PaginationMetaData meta = new PaginationMetaData(
                    404,
                    false,
                    "List is empty",
                    0,
                    size,
                    0,
                    0,
                    true,
                    true
            );

            return new ApiResponse<>(meta, new ArrayList<>());
        }

        boolean outOfRange = requestedPage >= totalPages;
        int page = requestedPage;

        if (outOfRange) {
            page = totalPages - 1;
            System.out.println("Out of range. Maximal page for the size is " + totalPages);
        }

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, allItems.size());

        List<SandboxItem> pageData = allItems.subList(fromIndex, toIndex);

        PaginationMetaData meta = new PaginationMetaData(
                outOfRange ? 404 : 200,
                !outOfRange,
                outOfRange ? "Out of range. Maximal page for the size is " + totalPages : null,
                page,
                size,
                totalElements,
                totalPages,
                page == 0,
                page == totalPages - 1
        );

        return new ApiResponse<>(meta, pageData);
    }
}