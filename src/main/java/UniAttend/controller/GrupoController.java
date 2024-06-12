package UniAttend.controller;

import UniAttend.request.ReqResGrupo;
import UniAttend.entity.Grupo;
import UniAttend.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping("/grupo/guardar")
    public ResponseEntity<ReqResGrupo> guardar(@RequestBody ReqResGrupo grupo) {
        return ResponseEntity.ok(grupoService.guardar(grupo));
    }

    @PutMapping("/grupo/actualizar/{id}")
    public ResponseEntity<ReqResGrupo> actualizar(@PathVariable Long id, @RequestBody Grupo grupo) {
        return ResponseEntity.ok(grupoService.actualizar(id, grupo));
    }

    @DeleteMapping("/grupo/eliminar/{id}")
    public ResponseEntity<ReqResGrupo> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.eliminar(id));
    }

    @GetMapping("/grupo/listar")
    public ResponseEntity<ReqResGrupo> listar() {
        return ResponseEntity.ok(grupoService.listar());
    }

    @GetMapping("/grupo/{id}")
    public ResponseEntity<ReqResGrupo> getGrupoByID(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.getGrupoById(id));
    }
}
