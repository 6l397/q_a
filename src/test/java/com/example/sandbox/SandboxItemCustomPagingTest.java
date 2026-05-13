package com.example.sandbox;

import com.example.sandbox.model.SandboxItem;
import com.example.sandbox.request.ItemPageRequest;
import com.example.sandbox.response.ApiResponse;
import com.example.sandbox.response.PaginationMetaData;
import com.example.sandbox.service.SandboxItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class SandboxItemCustomPagingTest {

    @Autowired
    private SandboxItemService underTest;

    @Test
    void whenHappyPathThenOk() {
        ItemPageRequest request = new ItemPageRequest(0, 5);

        ApiResponse<PaginationMetaData, SandboxItem> response =
                underTest.getItemsPage(request);

        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());
        assertNull(response.getMeta().getErrorMessage());

        assertEquals(0, response.getMeta().getNumber());
        assertEquals(5, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(6, response.getMeta().getTotalPages());
        assertTrue(response.getMeta().isFirst());
        assertFalse(response.getMeta().isLast());

        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
        assertEquals(5, response.getData().size());
        assertEquals(1L, response.getData().get(0).getId());
    }

    @Test
    void whenSizeIs7AndPageIs4ThenIsLastTrueAndSizeEquals2() {
        ItemPageRequest request = new ItemPageRequest(4, 7);

        ApiResponse<PaginationMetaData, SandboxItem> response =
                underTest.getItemsPage(request);

        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());

        assertEquals(4, response.getMeta().getNumber());
        assertEquals(7, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(5, response.getMeta().getTotalPages());
        assertFalse(response.getMeta().isFirst());
        assertTrue(response.getMeta().isLast());

        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
    }

    @Test
    void whenPageValueIsOutOfRangeThenErrorMessageHasTheWarning() {
        ItemPageRequest request = new ItemPageRequest(9, 4);

        ApiResponse<PaginationMetaData, SandboxItem> response =
                underTest.getItemsPage(request);

        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(404, response.getMeta().getCode());
        assertFalse(response.getMeta().isSuccess());
        assertNotNull(response.getMeta().getErrorMessage());
        assertTrue(response.getMeta().getErrorMessage()
                .contains("Maximal page for the size is " + response.getMeta().getTotalPages()));
    }

    @Test
    void whenRequestIsIncorrectThenGiveTheLastPage() {
        ItemPageRequest request = new ItemPageRequest(9, 4);

        ApiResponse<PaginationMetaData, SandboxItem> response =
                underTest.getItemsPage(request);

        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(404, response.getMeta().getCode());
        assertFalse(response.getMeta().isSuccess());

        assertEquals(7, response.getMeta().getNumber());
        assertEquals(4, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(8, response.getMeta().getTotalPages());
        assertFalse(response.getMeta().isFirst());
        assertTrue(response.getMeta().isLast());

        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
        assertEquals(2, response.getData().size());
        assertEquals(29L, response.getData().get(0).getId());
        assertEquals(30L, response.getData().get(1).getId());
    }

    @Test
    void whenSizeIsIncorrectThenReturnBadRequest() {
        ItemPageRequest request = new ItemPageRequest(0, 0);

        ApiResponse<PaginationMetaData, SandboxItem> response =
                underTest.getItemsPage(request);

        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(400, response.getMeta().getCode());
        assertFalse(response.getMeta().isSuccess());
        assertEquals("Page size must be greater than 0", response.getMeta().getErrorMessage());

        assertNotNull(response.getData());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void testLoggingWhenPageIsOutOfRange(CapturedOutput output) {
        ItemPageRequest request = new ItemPageRequest(9, 4);

        underTest.getItemsPage(request);

        assertTrue(output.toString().contains("Out of range"));
    }
}