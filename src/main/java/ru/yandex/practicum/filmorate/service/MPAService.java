package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.impl.MPADaoImpl;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.MPAStorage;

import java.util.List;

@Service
@Slf4j
public class MPAService {

    private final MPAStorage mpaStorage;


    public MPAService(MPAStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public MPA returnMPA(int id) {
        log.info("Service:method,MPAService->returnMPA");
        checkId(id);
        return mpaStorage.returnMPAById(id);
    }

    public List<MPA> returnMPAs() {
        log.info("Service:method,MPAService->returnMPAs");
        return mpaStorage.returnMPAs();
    }

    public static void checkId(int id) {
        if (id <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new BadRequestException("incorrect id for update user");
        }
    }


}
