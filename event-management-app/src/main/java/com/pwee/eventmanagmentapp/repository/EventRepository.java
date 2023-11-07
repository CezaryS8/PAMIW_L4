package com.pwee.eventmanagmentapp.repository;

import com.pwee.eventmanagmentapp.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
