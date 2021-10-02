package com.reminder.webapi.service;

import com.reminder.webapi.model.Location;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LocationService {
    void create(Location location);

    List<Location> readAll();

    Location read(int id);

    boolean update(Location location, int id);

    boolean delete(int id);
}
