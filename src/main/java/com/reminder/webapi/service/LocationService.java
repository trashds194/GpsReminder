package com.reminder.webapi.service;

import com.reminder.webapi.model.Location;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface LocationService {
    boolean create(Location location, UserDetails userDetails);

    List<Location> readAll();

    List<Location> readAllUserLocations(String login,UserDetails userDetails);

    Location read(long id);

    Location read(String login, long id, UserDetails userDetails);

    boolean update(Location location, long id);

    boolean delete(long id);
}
