package io.acts.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.acts.springboot.entity.Suggestion;
import io.acts.springboot.repository.SuggestionRepository;

@Service
public class SuggService {
	
	@Autowired 
	private  SuggestionRepository sugRepo;
	
	
	public List getAllSuggestions() {
		List<Suggestion> suggestion=new ArrayList<>();
		sugRepo.findAll().forEach(suggestion::add);
		return suggestion;
		
//		sugRepo.save("sugg");
	}
	
	
	public List getByTypeSuggestion(String type) {
		List<Suggestion> ByType=new ArrayList<>();
		sugRepo.findByType(type).forEach(ByType::add);
		return ByType;
	}
	
//	public List<Employee> getAllEmployee(){
//		List<Employee> employee=new ArrayList<>();
//		employeeRepository.findAll().forEach(employee::add);
//		return employee;
//	}

}
