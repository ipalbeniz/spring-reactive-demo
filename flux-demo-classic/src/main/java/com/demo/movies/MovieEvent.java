package com.demo.movies;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class MovieEvent {

	private String movieId;
	private LocalDateTime when;
}
