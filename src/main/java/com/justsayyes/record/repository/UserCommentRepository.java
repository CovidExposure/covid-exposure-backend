package com.justsayyes.record.repository;

import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Comment;
import com.justsayyes.record.Entity.Visitor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface UserCommentRepository extends CrudRepository<Comment, Long> {
    @Query(value = "select comment from Comment comment where comment.location=:l order by comment.createDate")
    List<Comment> getAllComments(Location l);

    @Query(nativeQuery = true, value = "select comment from Comment comment where comment.location=:l order by comment.createDate limit 30")
    List<Comment> getLatestComments30(Location l);
}
