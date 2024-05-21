package com.example.sample;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameForm {
	
	@NotNull(message = "Number must not be empty")
	@Range(min = 1, max = 100, message = "Number must be between 1 and 100")
	private Integer number;
}
