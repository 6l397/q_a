package com.example.sandbox;

import com.example.sandbox.model.SandboxItem;
import com.example.sandbox.response.ApiResponse;
import com.example.sandbox.response.BaseMetaData;
import com.example.sandbox.service.SandboxItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
class LoggingTest {

    @Autowired
    private SandboxItemService underTest;

    @Test
    void shouldLogEnteringMethodGetById(CapturedOutput output) {
        SandboxItem item = underTest.getById(1L);

        assertNotNull(item);
        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("SandboxItemService.getById"));
        assertTrue(output.toString().contains("1"));
    }

    @Test
    void shouldLogAfterMethodGetById(CapturedOutput output) {
        SandboxItem item = underTest.getById(1L);

        assertNotNull(item);
        assertTrue(output.toString().contains("SandboxItemService.getById"));
        assertTrue(output.toString().contains("completed successfully"));
    }

    @Test
    void shouldLogEnteringMethodGetAll(CapturedOutput output) {
        underTest.getAll();

        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("SandboxItemService.getAll"));
    }

    @Test
    void shouldLogEnteringMethodGetItemsWithPaging(CapturedOutput output) {
        Page<SandboxItem> page = underTest.getItemsWithPaging(0, 5);

        assertNotNull(page);
        assertTrue(output.toString().contains("SandboxItemService.getItemsWithPaging"));
        assertTrue(output.toString().contains("0"));
        assertTrue(output.toString().contains("5"));
    }

    @Test
    void shouldLogAfterMethodGetByIdAsApiResponse(CapturedOutput output) {
        ApiResponse<BaseMetaData, SandboxItem> response =
                underTest.getByIdAsApiResponse(1L);

        assertNotNull(response);
        assertTrue(output.toString().contains("SandboxItemService.getByIdAsApiResponse"));
        assertTrue(output.toString().contains("completed successfully"));
    }
}