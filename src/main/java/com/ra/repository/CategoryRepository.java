package com.ra.repository;

import com.ra.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByName(String name);
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<Category> findBySearchText(@Param("searchText") String searchText, Pageable pageable);

    Page<Category> findByStatus(Boolean status, Pageable pageable);

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchText, '%')) AND c.status = :status")
    Page<Category> findBySearchTextAndStatus(@Param("searchText") String searchText, @Param("status") Boolean status, Pageable pageable);
}
