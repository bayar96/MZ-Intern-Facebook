package com.pgs.eventservice.service;

import com.pgs.eventservice.domain.Event;
import com.pgs.eventservice.dto.EventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public interface EventService {

    Page<Event> getAllEvents(Pageable pageable);

    Event getEventById(Long id);

    void saveEvent(String eventDTO, MultipartFile[] files) throws IOException;

    void joinEvent(Long id);

    void deleteEvent(Long id);

    Page<Event> getAllByDateTimeIsBefore(Pageable pageable, String range);
}