package com.JWT_firstTest.securityCrud.service;

import com.JWT_firstTest.securityCrud.entity.Task;
import com.JWT_firstTest.securityCrud.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;
    @Override
    public List<Task> getAllByIsDone(boolean isDone){
        return taskRepository.findAllByIsDone(false);
    }
    @Override
    public Task markTaskAsDone(int taskId) {
        // Check if the task exists in the database
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if(existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            // Update only the done field to true
            existingTask.setDone(true);
            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
    }
    @Override
    public Task addTask(Task task) {
        // Implement the algorithm here to generate the id automatically
        // You can use UUID library to generate a unique identifier if you're not using a database that supports auto-increment
        return taskRepository.save(task);
    }
    @Override
    public Task editTask(int taskId, Task task) {
        // Check if the task exists in the database
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if(existingTaskOptional.isPresent()) {
            Task existingTask = existingTaskOptional.get();
            // Update the data of the current task
            existingTask.setName(task.getName());
            existingTask.setDeadLine(task.getDeadLine());
            existingTask.setDone(task.isDone());
            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
    }
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(int taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }
    @Override
    public void deleteTaskById(int taskId) {
        // Check if the task exists in the database
        Optional<Task> existingTaskOptional = taskRepository.findById(taskId);
        if(existingTaskOptional.isPresent()) {
            // Task exists, delete it
            taskRepository.deleteById(taskId);
        } else {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
    }
}
