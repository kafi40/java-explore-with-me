package ru.practicum.mainserver.controller;

import ru.practicum.mainserver.dto.compilation.CompilationDto;
import ru.practicum.mainserver.dto.compilation.NewCompilationDto;
import ru.practicum.mainserver.dto.compilation.UpdateCompilationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping("/compilations")
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getAll(
            @RequestParam(required = false, defaultValue = "false") boolean pinned,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size
    ) {
        log.info("Server main (CompilationController): Get compilations with param pinned={}, from={}, size={}", pinned, from, size);
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto get(@Positive @PathVariable Long compId) {
        log.info("Server main (CompilationController): Get compilation with ID={}", compId);
        return compilationService.get(compId);
    }

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilation) {
        log.info("Server main (CompilationController): Create compilation={}", newCompilation);
        return compilationService.create(newCompilation);
    }

    @PatchMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto update(
            @Positive @PathVariable Long compId,
            @Valid @RequestBody UpdateCompilationRequest updateCompilation) {
        log.info("Server main (CompilationController): Update compilation with ID={}, body={}", compId, updateCompilation);
        return compilationService.update(compId, updateCompilation);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long compId) {
        log.info("Server main (CompilationController): Delete compilation with ID={}", compId);
        compilationService.delete(compId);
    }

}
