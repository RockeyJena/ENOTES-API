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
        category.setCreatedDate(new Date());
        category.setIsDeleted(false);
        category.setCreatedBy(1); 

        Category savedCategory = categoryRepository.save(category);

        if (savedCategory == null) {
            log.error("Error while saving category: {}", categoryDto);
            return false;
        }

        log.info("Category saved successfully: {}", savedCategory);
        return true;
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
}
