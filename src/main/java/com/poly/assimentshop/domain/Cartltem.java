package com.poly.assimentshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cartltem {
	private int productId;
	private String name;
	private int quantily;
	private double unitPrice;
}
