package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.request.dto.enums.Status;
import ru.practicum.ewm.request.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestsRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByUserId(int userId);

    List<Request> findAllByEventId(int eventId);

    List<Request> findAllByEventIdIn(List<Integer> eventIds);

    Optional<Request> findByUserIdAndEventId(int userId, int eventId);

    @Modifying
    @Transactional
    @Query("update Request r set r.status = :status where r.id in :requestIds")
    void updateStatus(Status status, List<Integer> requestIds);

}