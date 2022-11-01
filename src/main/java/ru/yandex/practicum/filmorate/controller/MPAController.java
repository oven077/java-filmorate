package ru.yandex.practicum.filmorate.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j

public class MPAController {


    private final MPAService mpaService;

    public MPAController(MPAService mpaService) {
        this.mpaService = mpaService;
    }


    @GetMapping()
    public List<MPA> returnMPAs() {
        log.info("controller:method mpaController -> returnMPAs");
        return mpaService.returnMPAs();
    }

    //get user
    @GetMapping("/{id}")
    public MPA returnMPA(@PathVariable int id) {
        log.info("controller:method mpaController -> returnMPA");
        return mpaService.returnMPA(id);
    }


}
