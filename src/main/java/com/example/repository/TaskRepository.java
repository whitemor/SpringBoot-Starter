package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Task;

@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Long>{

}