package com.disneydemo.demo;

import com.disneydemo.demo.model.*;
import com.disneydemo.demo.model.Character;
import com.disneydemo.demo.repository.*;
import com.disneydemo.demo.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class DisneydemoApplication extends SpringBootServletInitializer {

	@Autowired
	CharacterService characterService;

	//@Autowired
	//GenreService genreService;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(DisneydemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(CharacterRepository charRepo, MovieRepository serieRepo, GenreRepository genreRepo,
									  CharacterSerieRepository characterSerieRepo, UserRepository userRepo){
		return (args) -> {
			Character ch1 = new Character("img1.png","Mickey Mouse", 0, 10.0,
					"Mickey is easily identified by his round ears, red shorts, white gloves, and yellow shoes. He speaks in a falsetto voice, " +
							"and his speech is typically infused with 1930s slang like \"swell\" and \"gee\". The essence of Mickey’s character is that of an underdog who, " +
							"despite being small and vulnerable, overcomes larger-than-life adversity through quick wit and a can-do spirit."
					//, Arrays.asList()

			);
			Character ch2 = new Character("img2.png","Ariel", 16, 10.0,
					"Ariel is the protagonist of Disney's 1989 animated feature film, The Little Mermaid. " +
							"She is the seventh and youngest daughter of King Triton and Queen Athena, rulers of the undersea kingdom of Atlantica." +
							" Ariel lived through much of her young life with a passionate - yet forbidden - admiration of the human world, and longed to someday experience life on the surface."

			);


			charRepo.save(ch1);
			charRepo.save(ch2);


			String sDate1 = "01/10/2000";
			SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");
			Date date1=formatter1.parse(sDate1);

			Movie s1 = new Movie(
					"img1.png",
					"Mickey Mouse's House",
						date1,
					4.2
			);

			serieRepo.save(s1);

			Genre g1 = new Genre(
					"img1.png",
					"Comedy"
			);

			Genre g2 = new Genre(
					"img2.png",
					"Fantasy"
			);

			Genre g3 = new Genre(
					"img2.png",
					"Documental"
			);

			Genre g4 = new Genre(
					"img2.png",
					"Drama"
			);

			Genre g5 = new Genre(
					"img2.png",
					"Action"
			);

			genreRepo.save(g1);
			genreRepo.save(g2);
			genreRepo.save(g3);
			genreRepo.save(g4);
			genreRepo.save(g5);

			CharacterMovie cs1 = new CharacterMovie(ch1, s1);

			characterSerieRepo.save(cs1);

			g1.addMovie(s1);

			genreRepo.save(g1);



			DisneyUser user = new DisneyUser("user","email1@gmail.com", passwordEncoder.encode("password"));

			userRepo.save(user);

		};
	}

}
