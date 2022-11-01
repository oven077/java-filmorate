package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.impl.MPADaoImpl;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@Service
@Slf4j
public class MPAService {

    private MPADaoImpl mpaDao;


    public MPAService(MPADaoImpl mpaDao) {
        this.mpaDao = mpaDao;
    }

    public MPA returnMPA(int id) {
        log.info("Service:method,MPAService->returnMPA");
        checkId(id);
        return mpaDao.returnMPAById(id);
    }

    public List<MPA> returnMPAs() {
        log.info("Service:method,MPAService->returnMPAs");
        return mpaDao.returnMPAs();
    }

    public static void checkId(int id) {
        if (id <= 0) {
            log.info("При обновлении пользователя пустой id");
            throw new BadRequestException("incorrect id for update user");
        }
    }


}
