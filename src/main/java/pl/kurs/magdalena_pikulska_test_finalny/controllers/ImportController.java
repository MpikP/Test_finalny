package pl.kurs.magdalena_pikulska_test_finalny.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.magdalena_pikulska_test_finalny.dto.ImportStatusDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.ImportStatus;
import pl.kurs.magdalena_pikulska_test_finalny.services.ImportService;

import java.util.concurrent.CompletableFuture;


@ComponentScan
@RestController
@RequestMapping("/api/import")
public class ImportController {

    private ModelMapper mapper;
    private final ImportService importService;

    public ImportController(ModelMapper mapper, ImportService importService) {
        this.mapper = mapper;
        this.importService = importService;
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    //@PreAuthorize("hasRole('IMPORTER')")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("There is no file to import.");
        }
        if (!file.getContentType().equals("text/csv")) {
            return ResponseEntity.badRequest().body("Only CSV files are allowed.");
        }
        CompletableFuture<Long> importIdFuture  = importService.savePersonFromCsvFile(file);
        Long importId;
        try {
            importId = importIdFuture.get();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the import.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("ImportId: " + importId);
    }

    @GetMapping("/status/{id}")
    //@PreAuthorize("hasRole('IMPORTER')")
    public ResponseEntity<ImportStatusDto> getImportStatus(@PathVariable Long id) {
        ImportStatus status = importService.getById(id);
        ImportStatusDto statusDto = mapper.map(status, ImportStatusDto.class);
        return ResponseEntity.ok().body(statusDto);
    }
}
