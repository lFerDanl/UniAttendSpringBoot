package UniAttend.service;

import UniAttend.dto.AulaDTO;
import UniAttend.dto.ModuloDTO;
import UniAttend.request.ReqResModulo;
import UniAttend.entity.Aula;
import UniAttend.entity.Modulo;
import UniAttend.repository.AulaRepository;
import UniAttend.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private AulaRepository aulaRepository;

    public ReqResModulo guardar(ReqResModulo registroModulo) {
        ReqResModulo respuesta = new ReqResModulo();

        try {
            Modulo modulo = new Modulo();
            modulo.setNro(registroModulo.getNro());

            // Obtener las aulas correspondientes a las IDs de la base de datos
            List<Aula> aulas = (List<Aula>) aulaRepository.findAllById(registroModulo.getAulasId());

            // Asignar las aulas al módulo
            modulo.setAulas(aulas);

            // Guardar el módulo en la base de datos
            Modulo moduloGuardado = moduloRepository.save(modulo);

            // Comprobar si se guardó correctamente
            if (moduloGuardado.getId() != null) {
                ModuloDTO moduloDTO = new ModuloDTO(moduloGuardado); // Convertir la entidad a DTO
                respuesta.setModulo(moduloDTO);
                respuesta.setMessage("Módulo guardado exitosamente");
                respuesta.setStatusCode(200);
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }


    public ReqResModulo actualizar(Long id, ReqResModulo moduloActualizado) {
        ReqResModulo respuesta = new ReqResModulo();

        try {
            Optional<Modulo> optionalModulo = moduloRepository.findById(id);
            if (optionalModulo.isPresent()) {
                Modulo moduloExistente = optionalModulo.get();

                // Actualizar los campos del módulo con los valores del módulo actualizado
                moduloExistente.setNro(moduloActualizado.getNro());

                // Obtener las aulas correspondientes a las IDs de la base de datos
                List<Aula> aulas = (List<Aula>) aulaRepository.findAllById(moduloActualizado.getAulasId());

                // Limpiar las aulas existentes y asignar las nuevas
                moduloExistente.getAulas().clear();
                moduloExistente.setAulas(aulas);

                // Guardar los cambios en la base de datos
                Modulo moduloGuardado = moduloRepository.save(moduloExistente);

                ModuloDTO moduloDTO = new ModuloDTO(moduloGuardado);

                respuesta.setModulo(moduloDTO);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Módulo actualizado exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Módulo no encontrado para actualizar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al actualizar el módulo: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResModulo eliminar(Long id) {
        ReqResModulo respuesta = new ReqResModulo();

        try {
            Optional<Modulo> optionalModulo = moduloRepository.findById(id);
            if (optionalModulo.isPresent()) {
                // Asegurarse de que la eliminación maneje las relaciones correctamente
                moduloRepository.deleteById(id);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Módulo eliminado exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Módulo no encontrado para eliminar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al eliminar el módulo: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResModulo listar() {
        ReqResModulo resp = new ReqResModulo();

        try {
            List<Modulo> modulos = (List<Modulo>) moduloRepository.findAll();
            List<ModuloDTO> moduloDTOs = modulos.stream().map(ModuloDTO::new).collect(Collectors.toList());
            resp.setModuloList(moduloDTOs);
            resp.setMessage("Módulos recuperados exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqResModulo getModuloById(Long moduloId) {
        ReqResModulo resp = new ReqResModulo();
        try {
            Optional<Modulo> optionalModulo = moduloRepository.findById(moduloId);
            if (optionalModulo.isPresent()) {
                Modulo modulo = optionalModulo.get();
                ModuloDTO moduloDTO = new ModuloDTO(modulo); // Convertir la entidad a DTO
                resp.setModulo(moduloDTO);
                resp.setStatusCode(200);
                resp.setMessage("Modulo con id '" + moduloId + "' encontrado exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Modulo con id '" + moduloId + "' no encontrado");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error ocurrido: " + e.getMessage());
        }
        return resp;
    }
    public ReqResModulo getAulasbyModuloID(Long moduloId) {
        ReqResModulo resp = new ReqResModulo();
        try {
            Optional<Modulo> optionalModulo = moduloRepository.findById(moduloId);
            if (optionalModulo.isPresent()) {
                Modulo modulo = optionalModulo.get();
                List<Aula> aulas = modulo.getAulas();

                // Convertir la lista de entidades Aula a una lista de DTO AulaDTO
                List<AulaDTO> aulaDTOs = aulas.stream()
                        .map(AulaDTO::new)
                        .collect(Collectors.toList());

                resp.setAulas(aulaDTOs);
                resp.setStatusCode(200);
                resp.setMessage("Aulas del módulo con id '" + moduloId + "' encontradas exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Módulo con id '" + moduloId + "' no encontrado");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error ocurrido: " + e.getMessage());
        }
        return resp;
    }



}
