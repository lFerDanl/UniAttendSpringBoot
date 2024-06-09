package UniAttend.controller;

import UniAttend.dto.ReqResHorario;
import UniAttend.entity.Horario;
import UniAttend.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HorarioController {
    @Autowired
    private HorarioService horarioService;

    @PostMapping("/horario/guardar")
    public ResponseEntity<ReqResHorario> guardar(@RequestBody ReqResHorario reg){
        return ResponseEntity.ok(horarioService.guardar(reg));
    }

    @PutMapping("/horario/actualizar/{horarioId}")
    public ResponseEntity<ReqResHorario> actualizar(@PathVariable Long horarioId, @RequestBody Horario reqres){
        return ResponseEntity.ok(horarioService.actualizar(horarioId,reqres));
    }

    @DeleteMapping("/horario/eliminar/{horarioId}")
    public ResponseEntity<ReqResHorario> eliminar(@PathVariable Long horarioId){
        return ResponseEntity.ok(horarioService.eliminar(horarioId));
    }

    @GetMapping("/horario/listar")
    public ResponseEntity<ReqResHorario> listar() {
        return ResponseEntity.ok(horarioService.listar());
    }

    @GetMapping("/horario/{horarioId}")
    public ResponseEntity<ReqResHorario> getHorarioByID(@PathVariable Long horarioId) {
        return ResponseEntity.ok(horarioService.getHorarioById(horarioId));
    }

}
