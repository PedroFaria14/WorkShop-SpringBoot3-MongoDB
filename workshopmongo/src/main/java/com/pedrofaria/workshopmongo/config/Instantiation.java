package com.pedrofaria.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.pedrofaria.workshopmongo.domain.Post;
import com.pedrofaria.workshopmongo.domain.User;
import com.pedrofaria.workshopmongo.dto.AuthorDTO;
import com.pedrofaria.workshopmongo.dto.CommentDTO;
import com.pedrofaria.workshopmongo.repository.PostRepository;
import com.pedrofaria.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private PostRepository PostRepository;

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		PostRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User cleiton = new User(null, "Cleiton", "Cleiton@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, cleiton));
		
		Post post1 = new Post(null,sdf.parse("21/03/2018"), "Partiu viagem","Vou viajar para São Paulo. Abraços!",new AuthorDTO(maria));
		Post post2 = new Post(null,sdf.parse("22/04/2022"),"Bom Dia!!","Partiu churrasco! ",new AuthorDTO(maria));

		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("22/03/2018"), new AuthorDTO(cleiton));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthorDTO(alex));
		
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		PostRepository.saveAll(Arrays.asList(post1,post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);
		
		
		
	}

	
	
}
