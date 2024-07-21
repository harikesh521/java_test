package com.example.geological.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeologicalClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public GeologicalClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GeologicalClass(Long id, String name, String code) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}
	@Override
	public String toString() {
		return "GeologicalClass [id=" + id + ", name=" + name + ", code=" + code + "]";
	}
    
    
    
}
