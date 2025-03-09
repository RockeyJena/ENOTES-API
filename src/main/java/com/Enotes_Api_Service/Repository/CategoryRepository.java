package com.Enotes_Api_Service.Repository;

import com.Enotes_Api_Service.Dto.CategoryDto;
import com.Enotes_Api_Service.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findAllByIsActiveTrue();
    Optional<Category> findByIdAndIsDeletedFalse(Integer id);
}
