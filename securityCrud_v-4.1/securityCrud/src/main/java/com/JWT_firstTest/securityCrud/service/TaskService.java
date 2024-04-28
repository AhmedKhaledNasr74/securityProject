package com.JWT_firstTest.securityCrud.service;

import com.JWT_firstTest.securityCrud.entity.Task;

import java.util.List;

public interface TaskService {
    Task addTask(Task task); // Add a new task
    Task editTask(int taskId, Task task); // Edit task information
    List<Task> getAllTasks(); // Get a list of all tasks
    Task getTaskById(int taskId); // Get information of a specific task
    void deleteTaskById(int taskId);
    List<Task> getAllByIsDone(boolean isDone);
    Task markTaskAsDone(int taskId);
}
