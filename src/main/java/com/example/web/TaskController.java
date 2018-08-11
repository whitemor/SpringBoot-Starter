package com.example.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.TaskException;
import com.example.model.Response;
import com.example.model.Task;
import com.example.service.TaskService;
import com.example.util.PayloadValidator;

@RestController
public class TaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value="/tasks", method=RequestMethod.GET)
	public ResponseEntity<List<Task>> getAllTasks(){
    	logger.info("Returning all the Tasks");
		return new ResponseEntity<List<Task>>(taskService.getAllTasks(), HttpStatus.OK);
	}
	
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
	public ResponseEntity<Task> getTaskById(@PathVariable("id") long id) throws TaskException{
    	logger.info("Task id to return " + id);
    	Task toDo = taskService.getTaskById(id);
    	if (toDo == null || toDo.getId() <= 0){
            throw new TaskException("Task doesn´t exist");
    	}
		return new ResponseEntity<Task>(taskService.getTaskById(id), HttpStatus.OK);
	}

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> removeTaskById(@PathVariable("id") long id) throws TaskException{
    	logger.info("Task id to remove " + id);
    	Task toDo = taskService.getTaskById(id);
    	if (toDo == null || toDo.getId() <= 0){
            throw new TaskException("Task to delete doesn´t exist");
    	}
		taskService.removeTask(toDo);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Task has been deleted"), HttpStatus.OK);
	}
    
    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
   	public ResponseEntity<Task> createTask(@RequestBody Task payload) throws TaskException{
    	logger.info("Payload to save " + payload);
    	if (!PayloadValidator.validateCreatePayload(payload)){
            throw new TaskException("Payload malformed, id must not be defined");
    	}
		return new ResponseEntity<Task>(taskService.saveTask(payload), HttpStatus.OK);
   	}
    
    @RequestMapping(value = "/tasks", method = RequestMethod.PATCH)
   	public ResponseEntity<Task>  updateTask(@RequestBody Task payload) throws TaskException{
    	logger.info("Payload to update " + payload);
    	Task toDo = taskService.getTaskById(payload.getId());
    	if (toDo == null || toDo.getId() <= 0){
            throw new TaskException("Task to update doesn´t exist");
    	}
		return new ResponseEntity<Task>(taskService.saveTask(payload), HttpStatus.OK);
   	}
	
}
