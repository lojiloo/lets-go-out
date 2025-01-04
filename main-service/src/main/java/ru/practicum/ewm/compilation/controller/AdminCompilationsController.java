package ru.practicum.ewm.compilation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.service.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationsController {
    private final CompilationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addNewCompilation(@RequestBody @Valid NewCompilationDto request) {
        log.info("AdminCompilationController: пришёл запрос на добавление новой подборки: {}", request);
        return service.addNewCompilation(request);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilationById(@RequestBody @Valid UpdateCompilationRequest request,
                                                @PathVariable Integer compId) {
        log.info("AdminCompilationController: пришёл запрос на обновление подборки с id={}: {}", compId, request);
        return service.updateCompilationById(request, compId);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationById(@PathVariable Integer compId) {
        log.info("AdminCompilationController: пришёл запрос на удаление подборки с id={}", compId);
        service.deleteCompilationById(compId);
    }
}