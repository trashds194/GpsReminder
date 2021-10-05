package com.reminder.webapi.service;

import com.reminder.webapi.model.Account;
import com.reminder.webapi.model.Location;
import com.reminder.webapi.repository.AccountRepository;
import com.reminder.webapi.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, AccountRepository accountRepository) {
        this.locationRepository = locationRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public boolean create(Location location, UserDetails userDetails) {
        if (userDetails.getUsername() != null) {
            Account account = accountRepository.findOneByUsername(userDetails.getUsername());
            location.setUserId(account.getId());
            location.setDate(LocalDate.now());
            locationRepository.save(location);
            return true;
        } else return false;
    }

    @Override
    public List<Location> readAll() {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> readAllUserLocations(String login, UserDetails userDetails) {
        Account account = accountRepository.findOneByUsername(userDetails.getUsername());
        if (account.getUsername().equals(login))
            return locationRepository.findAllByUserId(account.getId());
        else return Collections.emptyList();
    }

    @Override
    public Location read(long id) {
        return locationRepository.getById(id);
    }

    @Override
    public Location read(String login, long id, UserDetails userDetails) {
        Account account = accountRepository.findOneByUsername(userDetails.getUsername());
        Location location = locationRepository.getById(id);
        if (Objects.equals(account.getId(), location.getUserId())) {
            return location;
        } else return null;
    }

    @Override
    public boolean update(Location location, long id) {
        if (locationRepository.existsById(id)) {
            location.setId(id);
            locationRepository.save(location);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
