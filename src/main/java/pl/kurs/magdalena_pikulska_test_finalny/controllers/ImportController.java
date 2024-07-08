package pl.kurs.magdalena_pikulska_test_finalny.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.magdalena_pikulska_test_finalny.dto.ImportStatusDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.ImportStatus;
import pl.kurs.magdalena_pikulska_test_finalny.services.ImportService;


@ComponentScan
@RestController
//@PreAuthorize("hasRole('ADMIN')"
@RequestMapping("/api/import")
public class ImportController {

    private ModelMapper mapper;
    private final ImportService importService;

    public ImportController(ModelMapper mapper, ImportService importService) {
        this.mapper = mapper;
        this.importService = importService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("There is no file to import.");
        }
        if (!file.getContentType().equals("text/csv")) {
            return ResponseEntity.badRequest().body("Only CSV files are allowed.");
        }
        String importId = ImportService.initiateImport(file);
        return ResponseEntity.ok("Import initiated with ID: " + importId);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<ImportStatusDto> getImportStatus(@PathVariable Long id) {
        ImportStatus status = importService.getById(id);
        ImportStatusDto statusDto = mapper.map(status, ImportStatusDto.class);
        return ResponseEntity.ok().body(statusDto);
    }
}
