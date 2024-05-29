package UniAttend;

import UniAttend.entity.UserEntity;
import UniAttend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class  UniAttendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniAttendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {

			/* CREATE USERS */
			UserEntity Usuario = new UserEntity();
			Usuario.setEmail("santiago@gmail.com");
			Usuario.setCity("Santa Cruz");
			Usuario.setRole("ADMIN");
			Usuario.setName("Santiago");
			Usuario.setPassword("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6");
			userRepository.save(Usuario);
		};
	}
}
