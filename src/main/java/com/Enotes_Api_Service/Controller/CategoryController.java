package com.Enotes_Api_Service.Controller;

import com.Enotes_Api_Service.Dto.CategoryDto;
import com.Enotes_Api_Service.Dto.CategoryResponse;
import com.Enotes_Api_Service.Entity.Category;
import com.Enotes_Api_Service.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/save-category")
    public ResponseEntity<String> saveCategory(@RequestBody CategoryDto categoryDto) {
        boolean isSaved = categoryService.saveCategory(categoryDto);
        if (isSaved) {
            log.info("Category saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body("Category saved successfully");
        } else {
            log.error("Error occurred while saving category");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving category");
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<?>> getALlCategoryList(){
        List<CategoryDto> categoryList = categoryService.AllCategories();
        if (CollectionUtils.isEmpty(categoryList)){
            log.info("No category found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            log.info("All category list fetched successfully :- "+categoryList);
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }

    @GetMapping("/getAllActiveCategories")
    public ResponseEntity<List<?>> getALlActiveCategoryList(){
        List<CategoryResponse> categoryList = categoryService.AllActiveCategories();
        if (CollectionUtils.isEmpty(categoryList)){
            log.info("No category found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            log.info("All category list fetched successfully :- "+categoryList);
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> CategoryDetailsByID(@PathVariable Integer id) {
        CategoryDto categoryId = categoryService.CategoryDetailsByID(id);
       if (ObjectUtils.isEmpty(categoryId)) {
           log.info("No category found for id : " + id);
           return new ResponseEntity<>("No category found for id "+id,HttpStatus.NOT_FOUND);
       }
       else {
           log.info("Category details fetched successfully for id : " + id + " :- " + categoryId);
           return new ResponseEntity<>(categoryId, HttpStatus.OK);
       }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryDetailsByID(@PathVariable Integer id) {
      Boolean delate = categoryService.CategoryDelateByID(id);
        if (delate) {
            log.info("No category found  delete for id : " + id);
            return new ResponseEntity<>(" category   delete id sucessFully  "+id,HttpStatus.OK);
        }
        else {
            log.info("Category details fetched successfully for id : " + id + " :- " + delate);
            return new ResponseEntity<>("Category Not Deleted By Id :- ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /// 7 get delete

}
