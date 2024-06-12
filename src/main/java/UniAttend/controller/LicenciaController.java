package UniAttend.controller;

import UniAttend.request.ReqResLicencia;
import UniAttend.entity.Licencia;
import UniAttend.service.LicenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class LicenciaController {

    @Autowired
    private LicenciaService licenciaService;

    @PostMapping("/licencia/guardar")
    public ResponseEntity<ReqResLicencia> guardar(@RequestBody ReqResLicencia reg) {
        return ResponseEntity.ok(licenciaService.guardar(reg));
    }

    @PutMapping("/licencia/actualizar/{licenciaId}")
    public ResponseEntity<ReqResLicencia> actualizar(@PathVariable Long licenciaId, @RequestBody Licencia reqres) {
        return ResponseEntity.ok(licenciaService.actualizar(licenciaId, reqres));
    }

    @DeleteMapping("/licencia/eliminar/{licenciaId}")
    public ResponseEntity<ReqResLicencia> eliminar(@PathVariable Long licenciaId) {
        return ResponseEntity.ok(licenciaService.eliminar(licenciaId));
    }

    @GetMapping("/licencia/listar")
    public ResponseEntity<ReqResLicencia> listar() {
        return ResponseEntity.ok(licenciaService.listar());
    }

    @GetMapping("/licencia/{licenciaId}")
    public ResponseEntity<ReqResLicencia> getLicenciaByID(@PathVariable Long licenciaId) {
        return ResponseEntity.ok(licenciaService.getLicenciaById(licenciaId));
    }
}
