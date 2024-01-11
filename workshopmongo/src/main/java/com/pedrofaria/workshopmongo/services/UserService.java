package com.pedrofaria.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedrofaria.workshopmongo.domain.User;
import com.pedrofaria.workshopmongo.dto.UserDTO;
import com.pedrofaria.workshopmongo.repository.UserRepository;
import com.pedrofaria.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;

	public List<User> findAll(){
		return repo.findAll();
		
	}
	public User findbyId(String id) {
		 Optional<User>  obj= repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
			
			
			
				}
	
	public User insert(User obj) {
		return repo.insert(obj);
		
	}
	
	public void delete (String id) {
		findbyId(id);
		repo.deleteById(id);
		
	}
	
	

	
	public User update(User obj) {
		User newObj = findbyId(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	private void updateData(User newOBJ, User obj) {
		newOBJ.setName(obj.getName());
		newOBJ.setEmail(obj.getEmail());
	}
	public User FromDTO(UserDTO objDto) {
		return new User ( objDto.getId(),objDto.getName(),objDto.getEmail());
		
	}
		
	}
	
	
	

