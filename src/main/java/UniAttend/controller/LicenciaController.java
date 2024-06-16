package UniAttend.controller;

import UniAttend.request.ReqResLicencia;
import UniAttend.entity.Licencia;
import UniAttend.service.LicenciaService;
import UniAttend.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LicenciaController {

    @Autowired
    private LicenciaService licenciaService;

    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/adminuser/licencia/guardar")
    public ResponseEntity<ReqResLicencia> guardar(@RequestBody ReqResLicencia reg) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long usuarioId = usersManagementService.getMyId(email);
        System.out.println("UsuarioId: "+usuarioId);
        return ResponseEntity.ok(licenciaService.guardar(reg,usuarioId));
    }

    @PutMapping("/adminuser/licencia/actualizar/{licenciaId}")
    public ResponseEntity<ReqResLicencia> actualizar(@PathVariable Long licenciaId, @RequestBody ReqResLicencia reqres) {
        return ResponseEntity.ok(licenciaService.actualizar(licenciaId, reqres));
    }

    @DeleteMapping("/adminuser/licencia/eliminar/{licenciaId}")
    public ResponseEntity<ReqResLicencia> eliminar(@PathVariable Long licenciaId) {
        return ResponseEntity.ok(licenciaService.eliminar(licenciaId));
    }

    @GetMapping("/admin/licencia/listar")
    public ResponseEntity<ReqResLicencia> listar() {
        return ResponseEntity.ok(licenciaService.listar());
    }

    @GetMapping("/adminuser/licencia/{licenciaId}")
    public ResponseEntity<ReqResLicencia> getLicenciaByID(@PathVariable Long licenciaId) {
        return ResponseEntity.ok(licenciaService.getLicenciaById(licenciaId));
    }

    @GetMapping("/admin/licencia/listar/{usuarioId}")
    public ResponseEntity<ReqResLicencia> listarByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(licenciaService.listarByUsuarioId(usuarioId));
    }

    @GetMapping("/adminuser/licencia/listar/usuario")
    public ResponseEntity<ReqResLicencia> listarByUsuarioId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long usuarioId = usersManagementService.getMyId(email);
        return ResponseEntity.ok(licenciaService.listarByUsuarioId(usuarioId));
    }

    @PutMapping("/admin/licencia/aprobar/{licenciaId}")
    public ResponseEntity<ReqResLicencia> aprobarLicencia(@PathVariable Long licenciaId, @RequestBody ReqResLicencia reqres) {
        return ResponseEntity.ok(licenciaService.aprobarLicencia(licenciaId,reqres));
    }
}
