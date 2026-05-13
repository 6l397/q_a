package com.example.sandbox;

import com.example.sandbox.model.SandboxItem;
import com.example.sandbox.service.SandboxItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SandboxItemPagingTest {

    @Autowired
    private SandboxItemService underTest;

    @Test
    void shouldReturnFirstPageWithTenElements() {
        Page<SandboxItem> page = underTest.getItemsWithPaging(0, 10);

        assertNotNull(page);
        assertEquals(10, page.getContent().size());
    }

    @Test
    void shouldReturnSecondPageWithTenElements() {
        Page<SandboxItem> page = underTest.getItemsWithPaging(1, 10);

        assertNotNull(page);
        assertEquals(10, page.getContent().size());
    }

    @Test
    void shouldReturnThirdPageWithTenElements() {
        Page<SandboxItem> page = underTest.getItemsWithPaging(2, 10);

        assertNotNull(page);
        assertEquals(10, page.getContent().size());
    }

    @Test
    void shouldReturnTotalElementsThirty() {
        Page<SandboxItem> page = underTest.getItemsWithPaging(0, 10);

        assertNotNull(page);
        assertEquals(30, page.getTotalElements());
    }

    @Test
    void shouldReturnTotalPagesThree() {
        Page<SandboxItem> page = underTest.getItemsWithPaging(0, 10);

        assertNotNull(page);
        assertEquals(3, page.getTotalPages());
    }

    @Test
    void shouldReturnEmptyPageWhenPageNumberTooLarge() {
        Page<SandboxItem> page = underTest.getItemsWithPaging(5, 10);

        assertNotNull(page);
        assertTrue(page.getContent().isEmpty());
    }

    @Test
    void shouldReturnFirstElementOfFirstPage() {
        Page<SandboxItem> page = underTest.getItemsWithPaging(0, 10);

        assertNotNull(page);
        assertFalse(page.getContent().isEmpty());
        assertEquals(1L, page.getContent().get(0).getId());
    }
}