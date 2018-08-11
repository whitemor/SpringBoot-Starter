package com.example.util;

import com.example.model.Task;

public class PayloadValidator {
	
	public static boolean validateCreatePayload(Task task){
		if (task.getId() > 0){
			return false;
		}
		return true;
	}

}
