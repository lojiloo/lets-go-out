package ru.practicum.ewm.comment.repository.likes;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.comment.model.Like;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikesRepository extends JpaRepository<Like, Integer> {

    @Query("""
            select new ru.practicum.ewm.comment.repository.likes.LikesQueryDto(l.comment, count(l.user.id)) from Like l
            where l.comment.author.id = :userId
            group by l.comment
            order by count(l.user.id) DESC
            """)
    List<LikesQueryDto> findAllByCommentAuthorIdOrderByLikes(int userId, Pageable pageable);

    @Query("""
            select new ru.practicum.ewm.comment.repository.likes.LikesQueryDto(l.comment, count(l.comment.event.id)) from Like l
            where l.comment.event.id = :eventId
            group by l.comment
            order by count(l.comment.event.id) DESC
            """)
    List<LikesQueryDto> findAllByCommentEventIdOrderByLikes(int eventId, Pageable pageable);

    @Query("""
            select new ru.practicum.ewm.comment.repository.likes.LikesQueryDto(l.comment, count(l.user.id)) from Like l
            where l.comment.id in :ids
            group by l.comment
            """)
    List<LikesQueryDto> findAllByCommentIdIn(List<UUID> ids);

    @Query("""
            select new ru.practicum.ewm.comment.repository.likes.LikesQueryDto(l.comment, count(l.user.id)) from Like l
            where l.comment.id = :id
            group by l.comment
            """)
    Optional<LikesQueryDto> findByCommentId(UUID id);

    Optional<Like> findByUserIdAndCommentId(int userId, UUID comId);

}
