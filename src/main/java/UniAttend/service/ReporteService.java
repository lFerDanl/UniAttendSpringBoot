package UniAttend.service;

import UniAttend.dto.AsistenciaDTO;
import UniAttend.dto.AulaDTO;
import UniAttend.dto.ProgramacionDTO;
import UniAttend.entity.Asistencia;
import UniAttend.entity.ProgramacionAcademica;
import UniAttend.repository.AsistenciaRepository;
import UniAttend.repository.ProgramacionRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private ProgramacionRepository programacionAcademicaRepository;

    // Método para generar un reporte PDF de asistencias
    public byte[] generarReporteAsistenciasPDF() throws DocumentException {
        List<Asistencia> asistencias = (List<Asistencia>) asistenciaRepository.findAll(); // Obtener todas las asistencias
        List<AsistenciaDTO> asistenciaDTOS = asistencias.stream().map(AsistenciaDTO::new).collect(Collectors.toList());
        return generarReportePDF(asistenciaDTOS);
    }

    // Método para generar un reporte PDF de programaciones académicas
    public byte[] generarReporteProgramacionesPDF() throws DocumentException {
        List<ProgramacionAcademica> programaciones = (List<ProgramacionAcademica>) programacionAcademicaRepository.findAll(); // Obtener todas las programaciones académicas
        List<ProgramacionDTO> programacionDTOS = programaciones.stream().map(ProgramacionDTO::new).collect(Collectors.toList());
        return generarReportePDF(programacionDTOS);
    }

    // Método para generar un reporte Excel de asistencias
    public byte[] generarReporteAsistenciasExcel() throws IOException {
        List<Asistencia> asistencias = (List<Asistencia>) asistenciaRepository.findAll(); // Obtener todas las asistencias
        List<AsistenciaDTO> asistenciaDTOS = asistencias.stream().map(AsistenciaDTO::new).collect(Collectors.toList());
        return generarReporteExcel(asistenciaDTOS);
    }

    // Método para generar un reporte Excel de programaciones académicas
    public byte[] generarReporteProgramacionesExcel() throws IOException {
        List<ProgramacionAcademica> programaciones = (List<ProgramacionAcademica>) programacionAcademicaRepository.findAll(); // Obtener todas las programaciones académicas
        List<ProgramacionDTO> programacionDTOS = programaciones.stream().map(ProgramacionDTO::new).collect(Collectors.toList());
        return generarReporteExcel(programacionDTOS);
    }

    // Método para generar un reporte PDF o Excel genérico
    private byte[] generarReportePDF(List<?> datos) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();
        for (Object dato : datos) {
            document.add(new Paragraph(dato.toString())); // Ajusta esto según el formato deseado para cada entidad
        }
        document.close();

        return baos.toByteArray();
    }

    private byte[] generarReporteExcel(List<?> datos) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte");

        int rowNum = 0;
        for (Object dato : datos) {
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(0);
            cell.setCellValue(dato.toString()); // Ajusta esto según el formato deseado para cada entidad
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }
}
