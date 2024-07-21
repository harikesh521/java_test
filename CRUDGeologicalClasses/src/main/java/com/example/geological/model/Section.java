package com.example.geological.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<GeologicalClass> geologicalClasses=new ArrayList<>();

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

	public List<GeologicalClass> getGeologicalClasses() {
		return geologicalClasses;
	}

	public void setGeologicalClasses(List<GeologicalClass> geologicalClasses) {
		this.geologicalClasses = geologicalClasses;
	}

	public Section(Long id, String name, List<GeologicalClass> geologicalClasses) {
		super();
		this.id = id;
		this.name = name;
		this.geologicalClasses = geologicalClasses;
	}

	public Section() {
		this.geologicalClasses = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", name=" + name + ", geologicalClasses=" + geologicalClasses + "]";
	}
    
    
    
    
}
