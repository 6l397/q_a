package com.example.sandbox;

import com.example.sandbox.repository.SandboxItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SandboxItemRepositoryTest {

    @Autowired
    private SandboxItemRepository sandboxItemRepository;

    @Test
    void shouldContainThirtyItemsInDatabase() {
        long count = sandboxItemRepository.count();
        assertEquals(30, count);
    }
}
