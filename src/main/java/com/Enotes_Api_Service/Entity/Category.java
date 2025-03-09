package com.Enotes_Api_Service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Category_Table_DB")
public class Category extends BaseModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
private Integer id;
private String name;
private String description;

}
