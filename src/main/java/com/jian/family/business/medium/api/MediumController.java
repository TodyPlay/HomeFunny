package com.jian.family.business.medium.api;

import com.jian.family.business.medium.dto.MediumDto;
import com.jian.family.business.medium.dto.MediumListQuery;
import com.jian.family.business.medium.service.MediumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/medium")
public class MediumController {

    @Autowired
    private MediumService mediumService;

    @PostMapping("save")
    public MediumDto save(@RequestBody @Validated MediumDto request) {
        return mediumService.save(request);
    }

    @PostMapping("list")
    public PagedModel<MediumDto> list(@RequestBody MediumListQuery request, Pageable pageable) {
        return new PagedModel<>(mediumService.list(request, pageable));
    }

    @GetMapping("find-by-id")
    public Optional<MediumDto> findById(@RequestParam Long id) {
        return mediumService.findById(id);
    }

    @DeleteMapping("remove")
    public void remove(@RequestParam Long id) {
        mediumService.remove(id);
    }
}
