package UniAttend.controller;

import UniAttend.request.ReqRes;
import UniAttend.request.ReqResProgramacion;
import UniAttend.request.ResponseProgramacion;
import UniAttend.service.ProgramacionService;
import UniAttend.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProgramacionController {
    @Autowired
    private ProgramacionService programacionService;

    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/admin/programacion/guardar")
    public ResponseEntity<ReqResProgramacion> guardar(@RequestBody ReqResProgramacion reg) {
        return ResponseEntity.ok(programacionService.guardar(reg));
    }

    @PutMapping("/admin/programacion/actualizar/{programacionId}")
    public ResponseEntity<ReqResProgramacion> actualizar(@PathVariable Long programacionId, @RequestBody ReqResProgramacion reqres) {
        return ResponseEntity.ok(programacionService.actualizar(programacionId, reqres));
    }

    @DeleteMapping("/admin/programacion/eliminar/{programacionId}")
    public ResponseEntity<ReqResProgramacion> eliminar(@PathVariable Long programacionId) {
        return ResponseEntity.ok(programacionService.eliminar(programacionId));
    }

    @GetMapping("/admin/programacion/listar")
    public ResponseEntity<ReqResProgramacion> listar() {
        return ResponseEntity.ok(programacionService.listar());
    }

    @GetMapping("/admin/programacion/{programacionId}")
    public ResponseEntity<ReqResProgramacion> getProgramacionByID(@PathVariable Long programacionId) {
        return ResponseEntity.ok(programacionService.getProgramacionById(programacionId));
    }

    @GetMapping("/adminuser/programacion/{programacionId}/horarios")
    public ResponseEntity<ReqResProgramacion> getProgramacionHorarios(@PathVariable Long programacionId) {
        return ResponseEntity.ok(programacionService.getProgramacionHorarios(programacionId));
    }

    @GetMapping("/adminuser/programacion/{programacionId}/carreras")
    public ResponseEntity<ReqResProgramacion> getCarreras(@PathVariable Long programacionId) {
        return ResponseEntity.ok(programacionService.getCarreras(programacionId));
    }

    @GetMapping("/adminuser/programacion/listar/usuario")
    public ResponseEntity<ReqResProgramacion> listarUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long usuarioId = usersManagementService.getMyId(email);
        return ResponseEntity.ok(programacionService.listar(usuarioId));
    }

    @GetMapping("/adminuser/programaciones/listar/usuario")
    public ResponseEntity<ResponseProgramacion> listarProgramacionesUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long usuarioId = usersManagementService.getMyId(email);
        System.out.println("funcion controler");
        System.out.println("usuarioId: "+usuarioId);
        return ResponseEntity.ok(programacionService.listarProgramacionesUsuario(usuarioId));
    }

    @GetMapping("/adminuser/programacion/clasesDeHoy/usuario")
    public ResponseEntity<ResponseProgramacion> clasesHoy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long usuarioId = usersManagementService.getMyId(email);
        return ResponseEntity.ok(programacionService.clasesHoy(usuarioId));
    }

    @GetMapping("/adminuser/programaciones/usuario/{usuarioId}")
    public ResponseEntity<ResponseProgramacion> listarProgramacionesUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(programacionService.listarProgramacionesUsuario(usuarioId));
    }

    @GetMapping("/adminuser/programacion/clasesDeHoy")
    public ResponseEntity<ReqResProgramacion> listarClasesDeHoy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long usuarioId = usersManagementService.getMyId(email);
        ReqResProgramacion response = programacionService.listarClasesDeHoy(usuarioId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}