package ru.practicum.mainserver.service;

import ru.practicum.mainserver.dto.compilation.CompilationDto;
import ru.practicum.mainserver.dto.compilation.NewCompilationDto;
import ru.practicum.mainserver.dto.compilation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getAll(boolean pinned, int from, int size);

    CompilationDto get(Long compilationId);

    CompilationDto create(NewCompilationDto newCompilation);

    CompilationDto update(Long compilationId, UpdateCompilationRequest updateCompilation);

    void delete(Long compilationId);
}
