package ru.practicum.mainserver.service.impl;

import ru.practicum.mainserver.dto.compilation.CompilationDto;
import ru.practicum.mainserver.dto.compilation.NewCompilationDto;
import ru.practicum.mainserver.dto.compilation.UpdateCompilationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.exception.NotFountException;
import ru.practicum.mainserver.repository.CompilationRepository;
import ru.practicum.mainserver.repository.EventRepository;
import ru.practicum.mainserver.service.CompilationService;
import ru.practicum.mainserver.service.entity.Compilation;
import ru.practicum.mainserver.service.entity.Event;
import ru.practicum.mainserver.service.mapper.CompilationMapper;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getAll(boolean pinned, int from, int size) {
        log.info("Server main (CompilationService): Try getAll()");
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return compilationRepository.findAllByPinned(pinned, page).stream()
                .map(compilationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto get(Long compilationId) {
        log.info("Server main (CompilationService): Try get()");
        return compilationRepository.findById(compilationId)
                .map(compilationMapper::toDto)
                .orElseThrow(() -> new NotFountException(
                        "Server main (CategoryService): Not found compilation with id: " + compilationId));
    }

    @Override
    public CompilationDto create(NewCompilationDto newCompilation) {
        log.info("Server main (CompilationService): Try create()");
        Set<Event> events = eventRepository.findAllByIdIn(newCompilation.getEvents());
        Compilation compilation = compilationMapper.toEntity(newCompilation);
        compilation.setEvents(events);
        compilation.setPinned(newCompilation.getPinned() != null ? newCompilation.getPinned() : false);
        compilation = compilationRepository.save(compilation);
        return compilationMapper.toDto(compilation);
    }

    @Override
    public CompilationDto update(Long compilationId, UpdateCompilationRequest updateCompilation) {
        log.info("Server main (CompilationService): Try update()");
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFountException(
                        "Server main (CategoryService): Not found compilation with id: " + compilationId));

        if (updateCompilation.getEvents() != null) {
            Set<Event> events = eventRepository.findAllByIdIn(updateCompilation.getEvents());
            compilation.setEvents(events);
        }

        if (updateCompilation.getPinned() != null)
            compilation.setPinned(updateCompilation.getPinned());

        if (updateCompilation.getTitle() != null)
            compilation.setTitle(updateCompilation.getTitle());

        compilation = compilationRepository.save(compilation);
        return compilationMapper.toDto(compilation);
    }

    @Override
    public void delete(Long compilationId) {
        log.info("Server main (CompilationService): Try delete()");
        compilationRepository.deleteById(compilationId);
    }
}
