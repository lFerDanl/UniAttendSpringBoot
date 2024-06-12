package UniAttend.service;

import UniAttend.dto.CarreraDTO;
import UniAttend.dto.ModuloDTO;
import UniAttend.entity.Modulo;
import UniAttend.request.ReqResCarrera;
import UniAttend.entity.Carrera;
import UniAttend.entity.Facultad;
import UniAttend.repository.CarreraRepository;
import UniAttend.repository.FacultadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private FacultadRepository facultadRepository;

    public ReqResCarrera guardar(ReqResCarrera registroCarrera) {
        ReqResCarrera respuesta = new ReqResCarrera();

        try {
            Carrera carrera = new Carrera();
            carrera.setNombre(registroCarrera.getNombre());
            carrera.setFacultad(registroCarrera.getFacultad());

            // Guardar la carrera en la base de datos
            Carrera carreraGuardada = carreraRepository.save(carrera);

            // Comprobar si se guardó correctamente
            if (carreraGuardada.getId() != null) {
                respuesta.setCarrera(new CarreraDTO(carreraGuardada));
                respuesta.setMessage("Carrera guardada exitosamente");
                respuesta.setStatusCode(200);
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }


    public ReqResCarrera actualizar(Long id, ReqResCarrera carreraActualizada) {
        ReqResCarrera respuesta = new ReqResCarrera();

        try {
            Optional<Carrera> optionalCarrera = carreraRepository.findById(id);
            if (optionalCarrera.isPresent()) {
                Carrera carreraExistente = optionalCarrera.get();
                carreraExistente.setNombre(carreraActualizada.getNombre());
                // Actualiza la facultad si se proporciona en carreraActualizada
                if (carreraActualizada.getFacultad() != null) {
                    Optional<Facultad> optionalFacultad = facultadRepository.findById(carreraActualizada.getFacultad().getId());
                    if (optionalFacultad.isPresent()) {
                        carreraExistente.setFacultad(optionalFacultad.get());
                    } else {
                        respuesta.setStatusCode(404);
                        respuesta.setMessage("Facultad no encontrada");
                        return respuesta;
                    }
                }

                // Guardar los cambios en la base de datos
                Carrera carreraGuardada = carreraRepository.save(carreraExistente);

                respuesta.setCarrera(new CarreraDTO(carreraGuardada));
                respuesta.setStatusCode(200);
                respuesta.setMessage("Carrera actualizada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Carrera no encontrada para actualizar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al actualizar la carrera: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResCarrera eliminar(Long id) {
        ReqResCarrera respuesta = new ReqResCarrera();

        try {
            Optional<Carrera> optionalCarrera = carreraRepository.findById(id);
            if (optionalCarrera.isPresent()) {
                carreraRepository.deleteById(id);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Carrera eliminada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Carrera no encontrada para eliminar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al eliminar la carrera: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResCarrera listar() {
        ReqResCarrera respuesta = new ReqResCarrera();

        try {
            List<Carrera> carreras = (List<Carrera>) carreraRepository.findAll();
            List<CarreraDTO> carreraDTOs = carreras.stream().map(CarreraDTO::new).collect(Collectors.toList());
            respuesta.setCarreraList(carreraDTOs);
            respuesta.setMessage("Carreras recuperadas exitosamente");
            respuesta.setStatusCode(200);
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResCarrera getCarreraById(Long carreraId) {
        ReqResCarrera respuesta = new ReqResCarrera();
        try {
            Optional<Carrera> optionalCarrera = carreraRepository.findById(carreraId);
            if (optionalCarrera.isPresent()) {
                Carrera carrera = optionalCarrera.get();
                respuesta.setCarrera(new CarreraDTO(carrera));
                respuesta.setStatusCode(200);
                respuesta.setMessage("Carrera con id '" + carreraId + "' encontrada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Carrera con id '" + carreraId + "' no encontrada");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al recuperar la carrera: " + e.getMessage());
        }
        return respuesta;
    }
}
