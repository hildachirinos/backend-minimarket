package com.epiis.app.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epiis.app.dataaccess.PersonRepository;
import com.epiis.app.dto.DtoPerson;
import com.epiis.app.dto.DtoPhone;
import com.epiis.app.entity.Person;
import com.epiis.app.entity.Phone;

@Service
public class PersonBusiness {
	@Autowired
	private PersonRepository personRepository;
	
	public boolean insert(DtoPerson dtoPerson) {
		dtoPerson.setIdPerson(UUID.randomUUID().toString());
		dtoPerson.setCreatedAt(new Date());
		dtoPerson.setUpdatedAt(dtoPerson.getCreatedAt());
		
		Person person = new Person();
		
		person.setIdPerson(dtoPerson.getIdPerson());
		person.setFirstName(dtoPerson.getFirstName());
		person.setSurName(dtoPerson.getSurName());
		person.setDni(dtoPerson.getDni());
		person.setGender(dtoPerson.isGender());
		person.setBirthDate(new java.sql.Date(dtoPerson.getBirthDate().getTime()));
		person.setCreatedAt(new java.sql.Timestamp(dtoPerson.getCreatedAt().getTime()));
		person.setUpdatedAt(new java.sql.Timestamp(dtoPerson.getUpdatedAt().getTime()));
		
		this.personRepository.save(person);
		
		return true;
	}
	
	public List<DtoPerson> getAll() {
		List<Person> listPerson = this.personRepository.getAll();
		
		List<DtoPerson> listDtoPerson = new ArrayList<>();
		
		for(Person item: listPerson) {
			DtoPerson dtoPersonTemp = new DtoPerson();
			
			dtoPersonTemp.setIdPerson(item.getIdPerson());
			dtoPersonTemp.setFirstName(item.getFirstName());
			dtoPersonTemp.setSurName(item.getSurName());
			dtoPersonTemp.setDni(item.getDni());
			dtoPersonTemp.setGender(item.isGender());
			dtoPersonTemp.setBirthDate(item.getBirthDate());
			dtoPersonTemp.setCreatedAt(item.getCreatedAt());
			dtoPersonTemp.setUpdatedAt(item.getUpdatedAt());
			
			dtoPersonTemp.setListPhone(new ArrayList<>());
			
			for(Phone value: item.getListPhone())  {
				DtoPhone dtoPhoneTemp = new DtoPhone();
				
				dtoPhoneTemp.setIdPhone(value.getIdPhone());
				dtoPhoneTemp.setIdPerson(item.getIdPerson());
				dtoPhoneTemp.setNumber(value.getNumber());
				dtoPhoneTemp.setProvider(value.getProvider());
				dtoPhoneTemp.setCreatedAt(value.getCreatedAt());
				dtoPhoneTemp.setUpdatedAt(value.getUpdatedAt());
				
				dtoPersonTemp.getListPhone().add(dtoPhoneTemp);
			}
			
			listDtoPerson.add(dtoPersonTemp);
		}
		
		return listDtoPerson;
	}
}
