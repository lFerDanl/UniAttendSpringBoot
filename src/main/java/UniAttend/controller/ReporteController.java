package UniAttend.controller;

import UniAttend.service.ReporteService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Endpoint para generar y descargar un reporte de asistencias en formato PDF
    @GetMapping("/asistencias/pdf")
    public ResponseEntity<byte[]> generarReporteAsistenciasPDF() {
        System.out.println("reporte");
        byte[] pdfBytes;
        try {
            pdfBytes = reporteService.generarReporteAsistenciasPDF();
        } catch (DocumentException e) {
            // Manejo de la excepción DocumentException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error al generar el reporte de asistencias en PDF: " + e.getMessage()).getBytes());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte-asistencias.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    // Endpoint para generar y descargar un reporte de programaciones académicas en formato PDF
    @GetMapping("/programaciones/pdf")
    public ResponseEntity<byte[]> generarReporteProgramacionesPDF() {
        byte[] pdfBytes;
        try {
            pdfBytes = reporteService.generarReporteProgramacionesPDF();
        } catch (DocumentException e) {
            // Manejo de la excepción DocumentException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error al generar el reporte de programaciones en PDF: " + e.getMessage()).getBytes());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte-programaciones.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    // Endpoint para generar y descargar un reporte de asistencias en formato Excel
    @GetMapping("/asistencias/excel")
    public ResponseEntity<byte[]> generarReporteAsistenciasExcel() throws IOException {
        byte[] excelBytes = reporteService.generarReporteAsistenciasExcel(); // Llama al método del servicio

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "reporte-asistencias.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

    // Endpoint para generar y descargar un reporte de programaciones académicas en formato Excel
    @GetMapping("/programaciones/excel")
    public ResponseEntity<byte[]> generarReporteProgramacionesExcel() throws IOException {
        byte[] excelBytes = reporteService.generarReporteProgramacionesExcel(); // Llama al método del servicio

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "reporte-programaciones.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }
}