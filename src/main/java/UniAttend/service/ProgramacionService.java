package UniAttend.service;

import UniAttend.dto.*;
import UniAttend.entity.*;
import UniAttend.repository.*;
import UniAttend.request.ReqResProgramacion;
import UniAttend.request.ResponseProgramacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramacionService {

    @Autowired
    private ProgramacionRepository programacionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private ProgramacionHrRepository programacionHorarioRepository;

    public ReqResProgramacion guardar(ReqResProgramacion registrationRequest) {
        ReqResProgramacion resp = new ReqResProgramacion();

        try {
            ProgramacionAcademica programacion = new ProgramacionAcademica();
            programacion.setCupos(registrationRequest.getCupos());
            programacion.setMateria(registrationRequest.getMateria());
            programacion.setGrupo(registrationRequest.getGrupo());
            UserEntity usuario = userRepository.findById(registrationRequest.getUsuario().getId())
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
            programacion.setUsuario(usuario);
            List<Carrera> carreras = (List<Carrera>) carreraRepository.findAllById(registrationRequest.getCarrerasId());
            programacion.setCarreras(carreras);
            List<Long> carreraIds = new ArrayList<>();
            for (Carrera carrera : carreras) {
                carreraIds.add(carrera.getId());
            }

            // Guarda la programación académica
            ProgramacionAcademica programacionResult = programacionRepository.save(programacion);
            resp.setProgramacionAcademicaId(programacionResult.getId());
            resp.setProgramacion(new ProgramacionDTO(programacionResult, carreraIds));
            resp.setMessage("Programación académica guardada exitosamente");
            resp.setStatusCode(200);

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqResProgramacion actualizar(Long id, ReqResProgramacion updatedProgramacion) {
        ReqResProgramacion resp = new ReqResProgramacion();

        try {
            Optional<ProgramacionAcademica> optionalProgramacion = programacionRepository.findById(id);
            if (optionalProgramacion.isPresent()) {
                ProgramacionAcademica existingProgramacion = optionalProgramacion.get();
                existingProgramacion.setCupos(updatedProgramacion.getCupos());

                // Obtener las carreras actualizadas
                List<Carrera> carreras = (List<Carrera>) carreraRepository.findAllById(updatedProgramacion.getCarrerasId());
                existingProgramacion.getCarreras().clear();
                existingProgramacion.setCarreras(carreras);
                List<Long> carreraIds = new ArrayList<>();
                for (Carrera carrera : carreras) {
                    carreraIds.add(carrera.getId());
                }

                existingProgramacion.setMateria(updatedProgramacion.getMateria());
                existingProgramacion.setGrupo(updatedProgramacion.getGrupo());

                // Obtener el usuario actualizado
                Optional<UserEntity> optionalUserEntity = userRepository.findById(updatedProgramacion.getUsuario().getId());
                optionalUserEntity.ifPresent(existingProgramacion::setUsuario);

                // Guardar la programación académica actualizada
                ProgramacionAcademica savedProgramacion = programacionRepository.save(existingProgramacion);

                resp.setProgramacion(new ProgramacionDTO(savedProgramacion, carreraIds));
                resp.setStatusCode(200);
                resp.setMessage("Programación académica actualizada exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Programación académica no encontrada para actualizar");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Ocurrió un error al actualizar la programación académica: " + e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacion eliminar(Long id) {
        ReqResProgramacion resp = new ReqResProgramacion();

        try {
            Optional<ProgramacionAcademica> optionalProgramacion = programacionRepository.findById(id);
            if (optionalProgramacion.isPresent()) {
                programacionRepository.deleteById(id);
                resp.setStatusCode(200);
                resp.setMessage("Programación académica eliminada exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Programación académica no encontrada para eliminar");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error ocurrido al eliminar la programación académica: " + e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacion listar() {
        ReqResProgramacion resp = new ReqResProgramacion();

        try {
            List<ProgramacionAcademica> programaciones = (List<ProgramacionAcademica>) programacionRepository.findAll();
            // Mapear cada objeto ProgramacionAcademica a su respectivo DTO
            List<ProgramacionDTO> programacionDTOs = programaciones.stream()
                    .map(ProgramacionDTO::new) // Suponiendo que tienes un constructor en ProgramacionDTO que acepta ProgramacionAcademica como parámetro
                    .collect(Collectors.toList());

            resp.setProgramacionList(programacionDTOs);
            resp.setMessage("Programaciones académicas recuperadas exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacion listar(Long usuarioId) {
        ReqResProgramacion resp = new ReqResProgramacion();

        try {
            System.out.println("UsuarioId: "+usuarioId);
            // Buscar programaciones por el ID del usuario
            List<ProgramacionAcademica> programaciones = programacionRepository.findByUsuarioId(usuarioId);

            // Mapear cada objeto ProgramacionAcademica a su respectivo DTO
            List<ProgramacionDTO> programacionDTOs = programaciones.stream()
                    .map(ProgramacionDTO::new) // Suponiendo que tienes un constructor en ProgramacionDTO que acepta ProgramacionAcademica como parámetro
                    .collect(Collectors.toList());

            resp.setProgramacionList(programacionDTOs);
            resp.setMessage("Programaciones académicas recuperadas exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ResponseProgramacion listarProgramacionesUsuario(Long usuarioId) {
        ResponseProgramacion resp = new ResponseProgramacion();

        try {
            // Buscar programaciones por el ID del usuario
            List<ProgramacionAcademica> programaciones = programacionRepository.findByUsuarioId(usuarioId);


            // Mapear cada objeto ProgramacionAcademica a su respectivo DTO
            List<Programacion> programacions = programaciones.stream()
                    .map(Programacion::new) // Suponiendo que tienes un constructor en ProgramacionDTO que acepta ProgramacionAcademica como parámetro
                    .collect(Collectors.toList());

            resp.setProgramacionList(programacions);
            resp.setMessage("Programaciones exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ResponseProgramacion clasesHoy(Long usuarioId) {
        ResponseProgramacion resp = new ResponseProgramacion();
        try {
            LocalDate fechaActual = LocalDate.now();
            String diaActual = obtenerDiaEnEspanol(fechaActual.getDayOfWeek().toString().toUpperCase());

            // Obtener todas las programaciones académicas del usuario
            List<ProgramacionAcademica> programaciones = programacionRepository.findByUsuarioId(usuarioId);

            // Crear una lista para las clases de hoy
            List<Programacion> clasesDeHoy = new ArrayList<>();

            for (ProgramacionAcademica programacion : programaciones) {
                // Obtener las programaciones horarias de la programación académica para el día de hoy
                List<ProgramacionHorario> programacionHorarios = programacionHorarioRepository.findByProgramacionAcademica(programacion);

                // Filtrar las programaciones horarias por el día de hoy
                List<ProgramacionHr> horariosDeHoy = programacionHorarios.stream()
                        .filter(ph -> ph.getHorario().getDia().equalsIgnoreCase(diaActual))
                        .map(ProgramacionHr::new)
                        .collect(Collectors.toList());

                // Crear una instancia de Programacion que represente las clases de hoy
                Programacion claseDeHoy = new Programacion(programacion, true); // Utiliza el constructor con bandera

                // Agregar los horarios de hoy a la clase de hoy
                claseDeHoy.setProgramacionHorarios(horariosDeHoy);

                // Agregar la clase de hoy a la lista final
                clasesDeHoy.add(claseDeHoy);
            }

            // Establecer la lista de clases de hoy en la respuesta
            resp.setProgramacionList(clasesDeHoy);
            resp.setMessage("Clases de hoy para el usuario recuperadas exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
            e.printStackTrace(); // Log para depuración de errores
        }
        return resp;
    }



    public ReqResProgramacion listarClasesDeHoy(Long usuarioId) {
        ReqResProgramacion resp = new ReqResProgramacion();
        try {
            LocalDate fechaActual = LocalDate.now();
            String diaActual = obtenerDiaEnEspanol(fechaActual.getDayOfWeek().toString().toUpperCase());

            // Obtener todas las programaciones académicas del usuario
            List<ProgramacionAcademica> programaciones = programacionRepository.findByUsuarioId(usuarioId);

            // Crear una lista para las programaciones horarias del día de hoy
            List<ProgramacionHorarioDTO> programacionHorarioDTOs = new ArrayList<>();

            for (ProgramacionAcademica programacion : programaciones) {
                // Obtener las programaciones horarias de la programación académica
                List<ProgramacionHorario> programacionHorarios = programacionHorarioRepository.findByProgramacionAcademica(programacion);

                // Filtrar las programaciones horarias por el día de hoy
                List<ProgramacionHorarioDTO> horariosDeHoy = programacionHorarios.stream()
                        .filter(ph -> ph.getHorario().getDia().equalsIgnoreCase(diaActual))
                        .map(ProgramacionHorarioDTO::new)
                        .collect(Collectors.toList());

                // Agregar las programaciones horarias filtradas a la lista final
                programacionHorarioDTOs.addAll(horariosDeHoy);
            }

            resp.setDia(diaActual);
            resp.setProgramacionHorarios(programacionHorarioDTOs);
            resp.setMessage("Clases de hoy para el usuario recuperadas exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
            e.printStackTrace(); // Log para depuración de errores
        }
        return resp;
    }

    private String obtenerDiaEnEspanol(String diaEnIngles) {
        switch (diaEnIngles) {
            case "MONDAY":
                return "Lunes";
            case "TUESDAY":
                return "Martes";
            case "WEDNESDAY":
                return "Miércoles";
            case "THURSDAY":
                return "Jueves";
            case "FRIDAY":
                return "Viernes";
            case "SATURDAY":
                return "Sábado";
            case "SUNDAY":
                return "Domingo";
            default:
                return diaEnIngles; // Por si acaso no se encuentra, se devuelve el nombre original
        }
    }

    public ReqResProgramacion getProgramacionById(Long programacionId) {
        ReqResProgramacion resp = new ReqResProgramacion();
        try {
            Optional<ProgramacionAcademica> optionalProgramacion = programacionRepository.findById(programacionId);
            if (optionalProgramacion.isPresent()) {
                ProgramacionAcademica programacion = optionalProgramacion.get();
                List<Carrera> carreras = programacion.getCarreras();
                List<Long> carreraIds = new ArrayList<>();
                for (Carrera carrera : carreras) {
                    carreraIds.add(carrera.getId());
                }
                // Mapear la entidad ProgramacionAcademica a su DTO correspondiente
                ProgramacionDTO programacionDTO = new ProgramacionDTO(programacion, carreraIds); // Suponiendo que tengas un constructor en ProgramacionDTO que acepta ProgramacionAcademica como parámetro
                resp.setCarrerasId(carreraIds);
                resp.setProgramacion(programacionDTO);
                resp.setStatusCode(200);
                resp.setMessage("Programacion with id '" + programacionId + "' found successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Programacion with id '" + programacionId + "' not found");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacion getProgramacionHorarios(Long programacionId) {
        ReqResProgramacion resp = new ReqResProgramacion();
        try {
            Optional<ProgramacionAcademica> optionalProgramacion = programacionRepository.findById(programacionId);
            if (optionalProgramacion.isPresent()) {
                // Se encontró la ProgramacionAcademica con el id dado
                ProgramacionAcademica programacion = optionalProgramacion.get();
                List<ProgramacionHorario> programacionHorarios = programacionHorarioRepository.findByProgramacionAcademica(programacion);

                // Convertir la lista de ProgramacionHorario a una lista de ProgramacionHorarioDTO
                List<ProgramacionHorarioDTO> programacionHorarioDTOs = programacionHorarios.stream()
                        .map(ProgramacionHorarioDTO::new) // Utilizando el constructor de ProgramacionHorarioDTO
                        .collect(Collectors.toList());

                resp.setProgramacionHorarios(programacionHorarioDTOs);
                resp.setStatusCode(200);
                resp.setMessage("Programaciones horarias de la programación con id '" + programacionId + "' recuperadas exitosamente");
            } else {
                // No se encontró ninguna ProgramacionAcademica con el id dado
                resp.setStatusCode(404);
                resp.setMessage("Programación académica con id '" + programacionId + "' no encontrada");
            }
        } catch (Exception e) {
            // Ocurrió un error al recuperar las programaciones horarias
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacion getCarreras(Long programacionId) {
        ReqResProgramacion resp = new ReqResProgramacion();
        try {
            Optional<ProgramacionAcademica> optionalProgramacion = programacionRepository.findById(programacionId);
            if (optionalProgramacion.isPresent()) {
                ProgramacionAcademica programacion = optionalProgramacion.get();

                // Obtener la lista de carreras de la programación académica
                List<Carrera> carreras = programacion.getCarreras();

                // Mapear la lista de carreras a una lista de DTOs correspondientes
                List<CarreraDTO> carrerasDTO = carreras.stream().map(CarreraDTO::new).collect(Collectors.toList());

                resp.setCarreraList(carrerasDTO);
                resp.setStatusCode(200);
                resp.setMessage("Carreras retrieved successfully for programacion with id '" + programacionId + "'");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Programacion with id '" + programacionId + "' not found");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }

}
