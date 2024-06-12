package UniAttend.controller;

import UniAttend.request.ReqResCarrera;
import UniAttend.entity.Carrera;
import UniAttend.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @PostMapping("/carrera/guardar")
    public ResponseEntity<ReqResCarrera> guardar(@RequestBody ReqResCarrera reg){
        return ResponseEntity.ok(carreraService.guardar(reg));
    }

    @PutMapping("/carrera/actualizar/{carreraId}")
    public ResponseEntity<ReqResCarrera> actualizar(@PathVariable Long carreraId, @RequestBody ReqResCarrera reqres){
        return ResponseEntity.ok(carreraService.actualizar(carreraId, reqres));
    }

    @DeleteMapping("/carrera/eliminar/{carreraId}")
    public ResponseEntity<ReqResCarrera> eliminar(@PathVariable Long carreraId){
        return ResponseEntity.ok(carreraService.eliminar(carreraId));
    }

    @GetMapping("/carrera/listar")
    public ResponseEntity<ReqResCarrera> listar() {
        return ResponseEntity.ok(carreraService.listar());
    }

    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<ReqResCarrera> getCarreraByID(@PathVariable Long carreraId) {
        return ResponseEntity.ok(carreraService.getCarreraById(carreraId));
    }
}
