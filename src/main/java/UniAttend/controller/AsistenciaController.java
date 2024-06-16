package UniAttend.controller;

import UniAttend.request.ReqResAsistencia;
import UniAttend.entity.Asistencia;
import UniAttend.request.ReqResHorario;
import UniAttend.request.ReqResProgramacion;
import UniAttend.service.AsistenciaService;
import UniAttend.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private UsersManagementService usersManagementService;

    @GetMapping("/adminuser/asistencia/{asistenciaId}")
    public ResponseEntity<ReqResAsistencia> getAsistenciaByID(@PathVariable Long asistenciaId) {
        return ResponseEntity.ok(asistenciaService.getAsistenciaById(asistenciaId));
    }

    @PostMapping("/adminuser/asistencia/registrar/{programacionHorarioId}")
    public ResponseEntity<ReqResAsistencia> registrarAsistencia(@PathVariable Long programacionHorarioId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long usuarioId = usersManagementService.getMyId(email);
        return ResponseEntity.ok(asistenciaService.registrarAsistencia(programacionHorarioId, usuarioId));
    }

    @GetMapping("/adminuser/programacion/{programacionHorarioId}/horario")
    public ResponseEntity<ReqResAsistencia> getAsistenciaDeHoy(@PathVariable Long programacionHorarioId) {
        return ResponseEntity.ok(asistenciaService.getAsistenciaDeHoy(programacionHorarioId));
    }

    @GetMapping("/admin/asistencia/listar")
    public ResponseEntity<ReqResAsistencia> listar() {
        return ResponseEntity.ok(asistenciaService.listar());
    }


}
