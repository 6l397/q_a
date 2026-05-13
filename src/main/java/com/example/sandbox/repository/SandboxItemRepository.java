package com.example.sandbox.repository;

import com.example.sandbox.model.SandboxItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SandboxItemRepository extends JpaRepository<SandboxItem, Long> {
}