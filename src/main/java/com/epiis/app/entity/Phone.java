package com.epiis.app.entity;

import com.epiis.app.generic.EntityGeneric;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tphone")
@Getter
@Setter
public class Phone extends EntityGeneric {
	@Id
	@Column(name = "idPhone")
	private String idPhone;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "provider")
	private String provider;
	
	@ManyToOne
	@JoinColumn(name = "idPerson")
    private Person parentPerson;
}
