package com.grippaweb.jwt.dtos;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Roles {

	private long id;
	private String name;
	private String code;
}
