package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.*;
import ru.practicum.ewm.comment.dto.enums.CommentsSort;

import java.util.List;
import java.util.UUID;

public interface CommentsService {

    CommentFullDto addNewCommentPrivate(NewCommentDto request, Integer userId, Integer eventId);

    CommentFullDto getCommentByIdPublic(UUID comId);

    CommentAdminDto getCommentByIdAdmin(UUID comId);

    List<CommentFullDto> getCommentsByEventPublic(Integer eventId, CommentsSort sort, Integer from, Integer size);

    List<CommentShortDto> getAllUsersCommentsPrivate(Integer userId, CommentsSort sort, Integer from, Integer size);

    List<CommentAdminDto> getAllUsersCommentsAdmin(Integer userId, Integer from, Integer size);

    List<CommentAdminDto> getCommentsForReview(Integer from, Integer size);

    CommentFullDto updateUsersCommentPrivate(UpdateCommentDto request, Integer userId);

    void disableCommentsOnEvent(Integer userId, Integer eventId);

    void enableCommentsOnEvent(Integer userId, Integer eventId);

    void submitCommentForAdminModeration(Integer userId, UUID comId);

    CommentFullDto likeComment(Integer userId, UUID comId);

    void deleteCommentByIdPrivate(Integer userId, UUID comId);

    void deleteCommentByIdAdmin(UUID comId);

    void deleteAllUsersCommentsAdmin(Integer userId);

}
