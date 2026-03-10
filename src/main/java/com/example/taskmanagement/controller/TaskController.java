package com.example.taskmanagement.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.security.JwtService;
import com.example.taskmanagement.service.TaskService;
import com.example.taskmanagement.service.UserService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Task createTask(@RequestBody Task task,
                           @RequestHeader("Authorization") String token) {
    	task.setCreatedAt(LocalDateTime.now());

        String username = jwtService.extractUsername(token.substring(7));

        User user = userService.findByUsername(username);

        task.setUser(user);
        task.setStatus("TODO");

        return taskService.saveTask(task);
    }

    @GetMapping
    public List<Task> getTasks(@RequestHeader("Authorization") String token) {

        String username = jwtService.extractUsername(token.substring(7));

        User user = userService.findByUsername(username);

        return taskService.getTasks(user);
    }

    @PutMapping("/{id}/complete")
    public Task markCompleted(@PathVariable Long id) {

        return taskService.markCompleted(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);
    }
}