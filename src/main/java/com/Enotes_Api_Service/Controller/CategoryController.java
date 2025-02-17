package com.Enotes_Api_Service.Controller;

import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody  Category category){
        Boolean saveCategory = categoryService.saveCategory(category);
        if (saveCategory){
            log.info("Category saved successfully :- "+saveCategory);
            return  new ResponseEntity<>("Category saved successfully", HttpStatus.CREATED);
        }
        else {
            log.error("Error occurred while saving category :- "+saveCategory);
            return  new ResponseEntity<>("Error occurred while saving category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getALL")
    public ResponseEntity<List<?>> getALlCategoryList(){
        List<Category> categoryList = categoryService.AllCategories();
        if (CollectionUtils.isEmpty(categoryList)){
            log.info("No category found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            log.info("All category list fetched successfully :- "+categoryList);
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }

}
