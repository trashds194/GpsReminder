package com.reminder.webapi.repository;

import com.reminder.webapi.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByUserId(long id);
}
