package com.poly.assimentshop.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdminLoginDto implements Serializable{
@NotEmpty
	private String username;
@NotEmpty
	private String password;

private Boolean rememberMe = false;
}
