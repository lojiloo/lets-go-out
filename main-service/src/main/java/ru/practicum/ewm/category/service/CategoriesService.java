package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoriesService {

    CategoryDto addNewCategory(NewCategoryDto request);

    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryById(int id);

    CategoryDto updateCategory(CategoryDto request, int id);

    void deleteCategory(int id);

}