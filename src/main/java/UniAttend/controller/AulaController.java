package UniAttend.controller;

import UniAttend.request.ReqResAula;
import UniAttend.entity.Aula;
import UniAttend.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @PostMapping("/aula/guardar")
    public ResponseEntity<ReqResAula> guardar(@RequestBody ReqResAula aula) {
        return ResponseEntity.ok(aulaService.guardar(aula));
    }

    @PutMapping("/aula/actualizar/{id}")
    public ResponseEntity<ReqResAula> actualizar(@PathVariable Long id, @RequestBody ReqResAula aula) {
        return ResponseEntity.ok(aulaService.actualizar(id, aula));
    }

    @DeleteMapping("/aula/eliminar/{id}")
    public ResponseEntity<ReqResAula> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(aulaService.eliminar(id));
    }

    @GetMapping("/aula/listar")
    public ResponseEntity<ReqResAula> listar() {
        return ResponseEntity.ok(aulaService.listar());
    }

    @GetMapping("/aula/{id}")
    public ResponseEntity<ReqResAula> getAulaByID(@PathVariable Long id) {
        return ResponseEntity.ok(aulaService.getAulaById(id));
    }
}
