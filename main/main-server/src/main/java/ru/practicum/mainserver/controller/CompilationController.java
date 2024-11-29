package ru.practicum.mainserver.controller;

import com.example.maincommon.dto.compilation.CompilationDto;
import com.example.maincommon.dto.compilation.NewCompilationDto;
import com.example.maincommon.dto.compilation.UpdateCompilationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompilationController {

    @GetMapping("/compilations")
    public List<CompilationDto> getAll(
            @RequestParam(required = false, defaultValue = "false") boolean pinned,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        log.info("Server main (CompilationController): Get compilations with param pinned={}, from={}, size={}", pinned, from, size);
        return null;
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto get(@PathVariable Long compId) {
        log.info("Server main (CompilationController): Get compilation with ID={}", compId);
        return null;
    }

    @PostMapping("/admin/compilations")
    public CompilationDto create(@RequestBody NewCompilationDto newCompilation) {
        log.info("Server main (CompilationController): Create compilation={}", newCompilation);
        return null;
    }

    @PatchMapping("/compilations/{compId}")
    public CompilationDto update(@PathVariable Long compId, @RequestBody UpdateCompilationRequest updateCompilation) {
        log.info("Server main (CompilationController): Update compilation with ID={}, body={}", compId, updateCompilation);
        return null;
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void delete(@PathVariable Long compId) {
        log.info("Server main (CompilationController): Delete compilation with ID={}", compId);
    }

}
