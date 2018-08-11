package com.example.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.example.model.Task;

public class PayloadValidatorTest {

	@Test
	public void validatePayLoad() {
		Task task = new Task(1, "Sample Task 1", true);
		assertEquals(false, PayloadValidator.validateCreatePayload(task));
	}
	
	@Test
	public void validateInvalidPayLoad() {
		Task task = new Task(0, "Sample Task 1", true);
		assertEquals(true, PayloadValidator.validateCreatePayload(task));
	}
}
