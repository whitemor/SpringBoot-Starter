package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.model.Task;
import com.example.repository.TaskRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class TaskServiceTest {
	
	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskServiceImpl taskService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAllToDo(){
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task(1,"Task Sample 1",true));
		taskList.add(new Task(2,"Task Sample 2",true));
		taskList.add(new Task(3,"Task Sample 3",false));
		when(taskRepository.findAll()).thenReturn(taskList);
		
		List<Task> result = taskService.getAllTasks();
		assertEquals(3, result.size());
	}
	
	@Test
	public void testGetToDoById(){
		Task task = new Task(1,"Task Sample 1",true);
		when(taskRepository.findOne(1L)).thenReturn(task);
		Task result = taskService.getTaskById(1);
		assertEquals(1, result.getId());
		assertEquals("Task Sample 1", result.getText());
		assertEquals(true, result.isCompleted());
	}
	
	@Test
	public void saveToDo(){
		Task task = new Task(8,"Task Sample 8",true);
		when(taskRepository.save(task)).thenReturn(task);
		Task result = taskService.saveTask(task);
		assertEquals(8, result.getId());
		assertEquals("Task Sample 8", result.getText());
		assertEquals(true, result.isCompleted());
	}
	
	@Test
	public void removeToDo(){
		Task task = new Task(8,"Task Sample 8",true);
		taskService.removeTask(task);
        verify(taskRepository, times(1)).delete(task);
	}
	
	

}

