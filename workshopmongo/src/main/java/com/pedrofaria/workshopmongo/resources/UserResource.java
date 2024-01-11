package com.pedrofaria.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pedrofaria.workshopmongo.domain.Post;
import com.pedrofaria.workshopmongo.domain.User;
import com.pedrofaria.workshopmongo.dto.UserDTO;
import com.pedrofaria.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> List = service.findAll();
		List<UserDTO> listDto = List.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping(value = "/{id}") 
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findbyId(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable String id) {
	 service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	@PostMapping 
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
		User obj = service.FromDTO(objDto);
		obj =  service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@PutMapping 
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto,@PathVariable String id) {
		User obj = service.FromDTO(objDto);
		obj.setId(id);
		obj =  service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping(value = "/{id}/posts") 
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User obj = service.findbyId(id);
		return ResponseEntity.ok().body(obj.getPosts());
	}
	
}
