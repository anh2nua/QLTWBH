package com.poly.assimentshop.model;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Customer implements Serializable{

	private int customerId;
	private String name;
	private String email;
	private String password;
	private String phone;
	private Date registeredDate;
	private short status;
}
