package com.reminder.webapi.service;

import com.reminder.webapi.model.Location;
import com.reminder.webapi.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void create(Location location) {
        locationRepository.save(location);
    }

    @Override
    public List<Location> readAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location read(int id) {
        return locationRepository.getById(id);
    }

    @Override
    public boolean update(Location location, int id) {
        if (locationRepository.existsById(id)) {
            location.setId(id);
            locationRepository.save(location);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
