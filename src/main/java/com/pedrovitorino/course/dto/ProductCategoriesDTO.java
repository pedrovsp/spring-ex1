package com.pedrovitorino.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.pedrovitorino.course.entities.Product;

public class ProductCategoriesDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty
	@Length(min=3, max=80)
	private String name;
	@NotEmpty
	@Length(min=8)
	private String description;
	@Positive(message="preco deve ser maior que 0")
	private double price;
	private String imgUrl;
	private List<CategoryDTO> categories = new ArrayList<CategoryDTO>();
	
	public ProductCategoriesDTO() {}

	public ProductCategoriesDTO(String name, String description, double price, String imgUrl,
			List<CategoryDTO> categories) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.categories = categories;
	}
	
	public ProductCategoriesDTO(Product entity) {
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	public Product toEntity() {
		return new Product(null, name, description, price, imgUrl);
	}
}
