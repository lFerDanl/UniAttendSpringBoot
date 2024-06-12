package UniAttend.controller;

import UniAttend.request.ReqResFacultad;
import UniAttend.entity.Facultad;
import UniAttend.service.FacultadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class FacultadController {

    @Autowired
    private FacultadService facultadService;

    @PostMapping("/facultad/guardar")
    public ResponseEntity<ReqResFacultad> guardar(@RequestBody ReqResFacultad facultad) {
        return ResponseEntity.ok(facultadService.guardar(facultad));
    }

    @PutMapping("/facultad/actualizar/{id}")
    public ResponseEntity<ReqResFacultad> actualizar(@PathVariable Long id, @RequestBody Facultad facultad) {
        return ResponseEntity.ok(facultadService.actualizar(id, facultad));
    }

    @DeleteMapping("/facultad/eliminar/{id}")
    public ResponseEntity<ReqResFacultad> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(facultadService.eliminar(id));
    }

    @GetMapping("/facultad/listar")
    public ResponseEntity<ReqResFacultad> listar() {
        return ResponseEntity.ok(facultadService.listar());
    }

    @GetMapping("/facultad/{id}")
    public ResponseEntity<ReqResFacultad> getFacultadByID(@PathVariable Long id) {
        return ResponseEntity.ok(facultadService.getFacultadById(id));
    }
}
