package com.masai.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractUser {
	
	@NotNull(message = "Username cannot be NULL")
	@Pattern(regexp = "^[a-zA-Z0-9]{3,25}", message = "length must be >=3")
	private String username;

	
	@Size(min=8,message="Password should have minimum 8 alphabets")
	@Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}", message = "Password must be combination of Uppercase,Lowercase,Numbers and special characters")
	private String password;
	
	@NotNull
	private String address;
	
	@Pattern(regexp = "[6789]{1}[0-9]{10}", message = "Enter valid 10 digit mobile number")
	private String mobile;
	
	
	@Email(message="Please enter the valid email Id")
	private String email;
	
	@NotNull
	private String userType;

}
