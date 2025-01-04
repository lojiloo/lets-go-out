package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository<Event, Integer>, QuerydslPredicateExecutor<Event> {

    @Query("select e from Event e where e.initiator.id = :userId")
    List<Event> findAllByInitiatorId(int userId, Pageable pageable);

    List<Event> findAllByCategoryId(int categoryId);

    Optional<Event> findByIdAndInitiatorId(int eventId, int userId);

}