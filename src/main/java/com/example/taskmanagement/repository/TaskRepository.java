package com.example.taskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByUser(User user);
}