package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoriesRepository;
import ru.practicum.ewm.error.exceptions.ConditionsViolationException;
import ru.practicum.ewm.error.exceptions.EntityExistsException;
import ru.practicum.ewm.error.exceptions.NotFoundException;
import ru.practicum.ewm.event.repository.EventsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriesServiceImpl implements CategoriesService {
    private final ModelMapper mapper;

    private final CategoriesRepository categoriesRepository;
    private final EventsRepository eventsRepository;

    @Override
    public CategoryDto addNewCategory(NewCategoryDto request) {
        if (categoriesRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
            log.error("Категория с именем {} уже существует в базе данных. Добавить повторно категорию нельзя", request.getName());
            throw new EntityExistsException("Категория с данным именем уже существует");
        }

        Category category = new Category();
        category.setName(request.getName());
        categoriesRepository.save(category);
        log.info("Создана новая категория с id={}: {}", category.getId(), category);

        return mapper.map(category, CategoryDto.class);
    }


    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        return categoriesRepository.findAll(PageRequest.of(from, size))
                .getContent()
                .stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = categoriesRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category with id=%d was not found", id))
        );

        log.info("Получена категория по id={}: {}", id, category);
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto request, int id) {
        Category category = categoriesRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Category with id=%d was not found", id))
        );
        if (category.getName().equals(request.getName())) {
            return mapper.map(category, CategoryDto.class);
        }

        if (categoriesRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
            log.error("Категория с именем {} уже существует в базе данных. Добавить повторно категорию нельзя", request.getName());
            throw new EntityExistsException("Категория с данным именем уже существует");
        }

        if (request.getName() != null) {
            category.setName(request.getName());
        }
        categoriesRepository.save(category);
        log.info("Категория с id={} обновлена: {}", category.getId(), category);

        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(int catId) {
        if (eventsRepository.countByCategoryId(catId) > 0) {
            log.error("Невозможно удалить категорию с id={}: с категорией связаны события", catId);
            throw new ConditionsViolationException("The category is not empty");
        }

        Category category = categoriesRepository.findById(catId).orElseThrow(
                () -> new NotFoundException(String.format("Category with id=%d was not found", catId))
        );

        categoriesRepository.delete(category);
        log.info("Удалена категория с id={}", category.getId());
    }
}
