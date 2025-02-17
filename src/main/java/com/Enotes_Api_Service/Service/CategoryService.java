package com.Enotes_Api_Service.Service;

import com.Enotes_Api_Service.Entity.Category;

import java.util.List;

public interface CategoryService {
public Boolean saveCategory(Category category);
public List<Category>AllCategories();

}
