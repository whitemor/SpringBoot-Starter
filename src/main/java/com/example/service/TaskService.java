package com.example.service;

import java.util.List;

import com.example.model.Task;

public interface TaskService {
	public List<Task> getAllTasks();
	public Task getTaskById(long id);
	public Task saveTask(Task todo);
	public void removeTask(Task todo);
}
