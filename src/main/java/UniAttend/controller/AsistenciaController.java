package UniAttend.controller;

import UniAttend.request.ReqResAsistencia;
import UniAttend.entity.Asistencia;
import UniAttend.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminuser")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping("/asistencia/{asistenciaId}")
    public ResponseEntity<ReqResAsistencia> getAsistenciaByID(@PathVariable Long asistenciaId) {
        return ResponseEntity.ok(asistenciaService.getAsistenciaById(asistenciaId));
    }

    @PostMapping("/asistencia/registrar/{programacionHorarioId}")
    public ResponseEntity<ReqResAsistencia> registrarAsistencia(@PathVariable Long programacionHorarioId) {
        return ResponseEntity.ok(asistenciaService.registrarAsistencia(programacionHorarioId));
    }
}
