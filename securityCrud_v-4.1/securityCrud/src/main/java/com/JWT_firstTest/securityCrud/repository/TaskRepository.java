package com.JWT_firstTest.securityCrud.repository;

import com.JWT_firstTest.securityCrud.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByIsDone(boolean isDone);
}
