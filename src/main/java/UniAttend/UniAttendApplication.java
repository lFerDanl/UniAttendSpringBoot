package UniAttend;

import UniAttend.entity.*;
import UniAttend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class  UniAttendApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniAttendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder,
						   AulaRepository aulaRepository, ModuloRepository moduloRepository,
						   FacultadRepository facultadRepository, CarreraRepository carreraRepository,
						   HorarioRepository horarioRepository, MateriaRepository materiaRepository,
						   GrupoRepository grupoRepository
	) {
		return args -> {

			/* CREATE USERS */
			UserEntity Usuario = new UserEntity();
			Usuario.setEmail("santiago@gmail.com");
			Usuario.setCity("Santa Cruz");
			Usuario.setRole("ADMIN");
			Usuario.setName("Santiago");
			Usuario.setTelefono("75551574");
			Usuario.setDireccion("C/hola");
			Usuario.setCi("9767661");
			String password = passwordEncoder.encode("12345678");
			Usuario.setPassword(password);
			userRepository.save(Usuario);

			UserEntity regularUser = new UserEntity();
			regularUser.setEmail("jose@gmail.com");
			regularUser.setCity("Santa Cruz");
			regularUser.setRole("USER");
			regularUser.setName("Jose");
			regularUser.setTelefono("12345678");
			regularUser.setDireccion("C/abc");
			regularUser.setCi("87654321");
			String userPassword = passwordEncoder.encode("12345678");
			regularUser.setPassword(userPassword);
			userRepository.save(regularUser);

			Aula aula1 = new Aula();
			aula1.setNro("1");
			aula1.setCapacidad("30");
			aulaRepository.save(aula1);

			Aula aula2 = new Aula();
			aula2.setNro("2");
			aula2.setCapacidad("25");
			aulaRepository.save(aula2);

			Aula aula3 = new Aula();
			aula3.setNro("3");
			aula3.setCapacidad("20");
			aulaRepository.save(aula3);

			Aula aula4 = new Aula();
			aula4.setNro("4");
			aula4.setCapacidad("35");
			aulaRepository.save(aula4);

			// Crear dos módulos
			Modulo modulo1 = new Modulo();
			modulo1.setNro("256");

			Modulo modulo2 = new Modulo();
			modulo2.setNro("257");

			// Asignar las aulas a los módulos correspondientes
			modulo1.setAulas(Arrays.asList(aula1, aula2, aula3, aula4));
			modulo2.setAulas(Arrays.asList(aula1, aula2));

			// Guardar los módulos
			moduloRepository.save(modulo1);
			moduloRepository.save(modulo2);

			Facultad facultad1 = new Facultad();
			facultad1.setNombre("Facultad de Ciencias de la Computación y Telecomunicaciones");
			facultadRepository.save(facultad1);

			Facultad facultad2 = new Facultad();
			facultad2.setNombre("Facultad de Tecnología");
			facultadRepository.save(facultad2);

			// Crear dos carreras y asignarlas a la primera facultad
			Carrera carrera1 = new Carrera();
			carrera1.setNombre("Ing. Informatica");
			carrera1.setFacultad(facultad1);
			carreraRepository.save(carrera1);

			Carrera carrera2 = new Carrera();
			carrera2.setNombre("Ing. en Sistemas");
			carrera2.setFacultad(facultad1);
			carreraRepository.save(carrera2);

			// Crear y guardar horarios
			Horario horario1 = new Horario();
			horario1.setDia("Lunes");
			horario1.setHorarioInicio("07:00");
			horario1.setHorarioFin("08:00");
			horarioRepository.save(horario1);

			Horario horario2 = new Horario();
			horario2.setDia("Miércoles");
			horario2.setHorarioInicio("07:00");
			horario2.setHorarioFin("08:00");
			horarioRepository.save(horario2);

			Horario horario3 = new Horario();
			horario3.setDia("Viernes");
			horario3.setHorarioInicio("07:00");
			horario3.setHorarioFin("08:00");
			horarioRepository.save(horario3);

			Horario horario4 = new Horario();
			horario4.setDia("Lunes");
			horario4.setHorarioInicio("08:00");
			horario4.setHorarioFin("09:00");
			horarioRepository.save(horario4);

			Horario horario5 = new Horario();
			horario5.setDia("Miércoles");
			horario5.setHorarioInicio("08:00");
			horario5.setHorarioFin("09:00");
			horarioRepository.save(horario5);

			Horario horario6 = new Horario();
			horario6.setDia("Viernes");
			horario6.setHorarioInicio("08:00");
			horario6.setHorarioFin("09:00");
			horarioRepository.save(horario6);

			// Crear y guardar materias
			Materia materia1 = new Materia();
			materia1.setNombre("Calculo I");
			materiaRepository.save(materia1);

			Materia materia2 = new Materia();
			materia2.setNombre("Calculo II");
			materiaRepository.save(materia2);

			Materia materia3 = new Materia();
			materia3.setNombre("Fisica I");
			materiaRepository.save(materia3);

			Materia materia4 = new Materia();
			materia4.setNombre("Fisica II");
			materiaRepository.save(materia4);

			Materia materia5 = new Materia();
			materia5.setNombre("Programación");
			materiaRepository.save(materia5);

			Grupo grupo1 = new Grupo();
			grupo1.setNombre("SA");
			grupoRepository.save(grupo1);

			Grupo grupo2 = new Grupo();
			grupo2.setNombre("SB");
			grupoRepository.save(grupo2);

			Grupo grupo3 = new Grupo();
			grupo3.setNombre("SC");
			grupoRepository.save(grupo3);
		};
	}
}
