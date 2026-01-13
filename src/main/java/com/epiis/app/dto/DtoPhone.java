package com.epiis.app.dto;

import com.epiis.app.generic.DtoGeneric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPhone extends DtoGeneric {
	private String idPhone;
	
	private String idPerson;
	
	private String number;
	
	private String provider;
	
	private DtoPerson parentPerson;
}
