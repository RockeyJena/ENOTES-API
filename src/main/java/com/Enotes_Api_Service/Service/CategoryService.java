package com.Enotes_Api_Service.Service;

import com.Enotes_Api_Service.Dto.CategoryDto;
import com.Enotes_Api_Service.Dto.CategoryResponse;
import com.Enotes_Api_Service.Entity.Category;

import java.util.List;

public interface CategoryService {
public Boolean saveCategory(CategoryDto categoryDto);
public List<CategoryDto>AllCategories();

    List<CategoryResponse> AllActiveCategories();

    CategoryDto CategoryDetailsByID(Integer id);

    Boolean CategoryDelateByID(Integer id);
}
