package com.JWT_firstTest.securityCrud.controller;

import com.JWT_firstTest.securityCrud.entity.Task;
import com.JWT_firstTest.securityCrud.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")

public class TaskController {

    @Autowired
    private TaskService taskService;

    // Endpoint to add a new task
    @PostMapping("/admin/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task addedTask = taskService.addTask(task);
        return new ResponseEntity<>(addedTask, HttpStatus.CREATED);
    }

    // Endpoint to edit an existing task
    @PutMapping("/admin/edit/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Task> editTask(@PathVariable int taskId, @RequestBody Task task) {
        Task editedTask = taskService.editTask(taskId, task);
        return new ResponseEntity<>(editedTask, HttpStatus.OK);
    }

    // Endpoint to get all tasks
    @GetMapping("/user/mainPage")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Task>> getAllTasksForUser() {
//        List<Task> tasks = taskService.getAllTasks();
        List<Task> tasks = taskService.getAllByIsDone( false );
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    // Endpoint to get all tasks
    @GetMapping("/admin/mainPage")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<Task>> getAllTasksForAdmin() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
    // Endpoint to delete a task by its id
    @DeleteMapping("/admin/delete/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable int taskId) {
        taskService.deleteTaskById(taskId);
            return ResponseEntity.noContent().build();
    }
    // Endpoint to mark a task as done
    @PutMapping("/user/markDone/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Task> markTaskAsDone(@PathVariable int taskId) {
        Task updatedTask = taskService.markTaskAsDone(taskId);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    // Endpoint to get a task by its id
    @GetMapping("/admin/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Task> getTaskById(@PathVariable int taskId) {
        Task task = taskService.getTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}
