package com.Enotes_Api_Service.ServiceImpl;

import com.Enotes_Api_Service.Dto.CategoryDto;
import com.Enotes_Api_Service.Dto.CategoryResponse;
import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Repository.CategoryRepository;
import com.Enotes_Api_Service.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
   @Autowired
    private CategoryRepository  categoryRepository;
   @Autowired
   private ModelMapper mapper;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        if (ObjectUtils.isEmpty(category.getId())) {
            category.setCreatedDate(new Date());
            category.setIsDeleted(false);
            category.setCreatedBy(1);
        }
        else {
            updateCategory(category);
        }

        Category savedCategory = categoryRepository.save(category);

        if (savedCategory == null) {
            log.error("Error while saving category: {}", categoryDto);
            return false;
        }
        log.info("Category saved successfully: {}", savedCategory);
        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> repositoryById = categoryRepository.findById(category.getId());
        if (repositoryById.isPresent()) {
            Category existingCategory = repositoryById.get();

            // Update only non-null values
            if (category.getName() != null) {
                existingCategory.setName(category.getName());
            }
            if (category.getDescription() != null) {
                existingCategory.setDescription(category.getDescription());
            }
            if (category.getIsActive() != null) {
                existingCategory.setIsActive(category.getIsActive());
            }

            // Preserve audit fields
            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setCreatedDate(existingCategory.getCreatedDate());
            category.setIsDeleted(existingCategory.getIsDeleted());
            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());

            // Save updated entity
            categoryRepository.save(existingCategory);
        }
    }



    @Override
    public List<CategoryDto> AllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        log.info("All categories List {}", categoryList);
        List<CategoryDto> categoryDtoList = categoryList.stream().map(category -> mapper.map(category, CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> AllActiveCategories() {
        List<Category> categoryList = categoryRepository.findAllByIsActiveTrue();
        List<CategoryResponse> list = categoryList.stream().map(category -> mapper.map(category, CategoryResponse.class)).toList();
            log.info(" Category response data ,{} "+list);
        return list;
    }

    @Override
    public CategoryDto CategoryDetailsByID(Integer id) {
        Optional<Category> category = categoryRepository.findByIdAndIsDeletedFalse(id);
        if (category.isPresent()) {
            CategoryDto categoryDto = mapper.map(category.get(), CategoryDto.class);
            log.info("Category details by id: {}", categoryDto);
            return categoryDto;
        }
        return null;
    }

    @Override
    public Boolean CategoryDelateByID(Integer id) {
        Optional<Category> categoryRepositoryById = categoryRepository.findById(id);
if (categoryRepositoryById.isPresent()) {
    Category category = categoryRepositoryById.get();
    category.setIsDeleted(true);
    categoryRepository.save(category);
  log.info("Category delete successfully: " + category);
    return true;
}
        return false;
    }
}
