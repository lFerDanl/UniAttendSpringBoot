package UniAttend.controller;

import UniAttend.request.ReqResMateria;
import UniAttend.entity.Materia;
import UniAttend.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PostMapping("/materia/guardar")
    public ResponseEntity<ReqResMateria> guardar(@RequestBody ReqResMateria reg) {
        return ResponseEntity.ok(materiaService.guardar(reg));
    }

    @PutMapping("/materia/actualizar/{materiaId}")
    public ResponseEntity<ReqResMateria> actualizar(@PathVariable Long materiaId, @RequestBody Materia reqres) {
        return ResponseEntity.ok(materiaService.actualizar(materiaId, reqres));
    }

    @DeleteMapping("/materia/eliminar/{materiaId}")
    public ResponseEntity<ReqResMateria> eliminar(@PathVariable Long materiaId) {
        return ResponseEntity.ok(materiaService.eliminar(materiaId));
    }

    @GetMapping("/materia/listar")
    public ResponseEntity<ReqResMateria> listar() {
        return ResponseEntity.ok(materiaService.listar());
    }

    @GetMapping("/materia/{materiaId}")
    public ResponseEntity<ReqResMateria> getMateriaByID(@PathVariable Long materiaId) {
        return ResponseEntity.ok(materiaService.getMateriaById(materiaId));
    }
}