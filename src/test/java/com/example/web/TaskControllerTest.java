package com.example.web;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.DemoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;

	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void verifyAllTaskList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/tasks").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(4))).andDo(print());
	}
	
	@Test
	public void verifyTaskById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/tasks/3").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.text").exists())
		.andExpect(jsonPath("$.completed").exists())
		.andExpect(jsonPath("$.id").value(3))
		.andExpect(jsonPath("$.text").value("Build the artifacts"))
		.andExpect(jsonPath("$.completed").value(false))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidTaskArgument() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/tasks/f").accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.errorCode").value(400))
			.andExpect(jsonPath("$.message").value("The request could not be understood by the server due to malformed syntax."))
			.andDo(print());
	}
	
	@Test
	public void verifyInvalidTaskId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/tasks/0").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("Task doesn´t exist"))
		.andDo(print());
	}
	
	@Test
	public void verifyNullTask() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/tasks/6").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("Task doesn´t exist"))
		.andDo(print());
	}
	
	@Test
	public void verifyDeleteTask() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/4").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value(200))
		.andExpect(jsonPath("$.message").value("Task has been deleted"))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidTaskIdToDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/9").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("Task to delete doesn´t exist"))
		.andDo(print());
	}
	
	
	@Test
	public void verifySaveTask() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/tasks/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"text\" : \"New Task Sample\", \"completed\" : \"false\" }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.text").exists())
		.andExpect(jsonPath("$.completed").exists())
		.andExpect(jsonPath("$.text").value("New Task Sample"))
		.andExpect(jsonPath("$.completed").value(false))
		.andDo(print());
	}
	
	@Test
	public void verifyMalformedSaveTask() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/tasks/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": \"8\", \"text\" : \"New Task Sample\", \"completed\" : \"false\" }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("Payload malformed, id must not be defined"))
		.andDo(print());
	}
	
	@Test
	public void verifyUpdateTask() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/tasks/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"id\": \"1\", \"text\" : \"New Task Text\", \"completed\" : \"false\" }")
        .accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.text").exists())
		.andExpect(jsonPath("$.completed").exists())
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.text").value("New Task Text"))
		.andExpect(jsonPath("$.completed").value(false))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidTaskUpdate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.patch("/tasks/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"idd\": \"8\", \"text\" : \"New Task Sample\", \"completed\" : \"false\" }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("Task to update doesn´t exist"))
		.andDo(print());
	}

}
