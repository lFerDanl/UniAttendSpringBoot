package UniAttend.controller;

import UniAttend.request.ReqResModulo;
import UniAttend.service.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    @PostMapping("/modulo/guardar")
    public ResponseEntity<ReqResModulo> guardar(@RequestBody ReqResModulo modulo) {
        return ResponseEntity.ok(moduloService.guardar(modulo));
    }

    @PutMapping("/modulo/actualizar/{id}")
    public ResponseEntity<ReqResModulo> actualizar(@PathVariable Long id, @RequestBody ReqResModulo modulo) {
        return ResponseEntity.ok(moduloService.actualizar(id, modulo));
    }

    @DeleteMapping("/modulo/eliminar/{id}")
    public ResponseEntity<ReqResModulo> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(moduloService.eliminar(id));
    }

    @GetMapping("/modulo/listar")
    public ResponseEntity<ReqResModulo> listar() {
        return ResponseEntity.ok(moduloService.listar());
    }

    @GetMapping("/modulo/{id}")
    public ResponseEntity<ReqResModulo> getModuloByID(@PathVariable Long id) {
        return ResponseEntity.ok(moduloService.getModuloById(id));
    }
    @GetMapping("/modulo/aulas/{id}")
    public ResponseEntity<ReqResModulo> getAulasByModuloID(@PathVariable Long id) {
        return ResponseEntity.ok(moduloService.getAulasbyModuloID(id));
    }
}