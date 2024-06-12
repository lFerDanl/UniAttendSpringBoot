package UniAttend.controller;

import UniAttend.request.ReqResProgramacionHr;
import UniAttend.service.ProgramacionHrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class ProgramacionHrController {
    @Autowired
    private ProgramacionHrService programacionHrService;

    @PostMapping("/programacionhr/guardar/{programacionId}")
    public ResponseEntity<ReqResProgramacionHr> guardar(@PathVariable Long programacionId, @RequestBody ReqResProgramacionHr reg) {
        return ResponseEntity.ok(programacionHrService.guardar(programacionId, reg));
    }

    @PutMapping("/programacionhr/actualizar/{programacionId}")
    public ResponseEntity<ReqResProgramacionHr> actualizar(@PathVariable Long programacionHrId, @RequestBody ReqResProgramacionHr reqres) {
        return ResponseEntity.ok(programacionHrService.actualizar(programacionHrId, reqres));
    }

    @DeleteMapping("/programacionhr/eliminar/{programacionId}")
    public ResponseEntity<ReqResProgramacionHr> eliminar(@PathVariable Long programacionHrId) {
        return ResponseEntity.ok(programacionHrService.eliminar(programacionHrId));
    }

    @GetMapping("/programacionhr/listar")
    public ResponseEntity<ReqResProgramacionHr> listar() {
        return ResponseEntity.ok(programacionHrService.listar());
    }

    @GetMapping("/programacionhr/{programacionHrId}")
    public ResponseEntity<ReqResProgramacionHr> getProgramacionHrByID(@PathVariable Long programacionHrId) {
        return ResponseEntity.ok(programacionHrService.getProgramacionHorarioById(programacionHrId));
    }
}
