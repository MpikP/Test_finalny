package pl.kurs.magdalena_pikulska_test_finalny.controllers;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.magdalena_pikulska_test_finalny.dto.ImportStatusDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.ImportStatus;
import pl.kurs.magdalena_pikulska_test_finalny.services.ImportService;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ImportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportService importService;

    @MockBean
    private ModelMapper mapper;


    @Test
    public void testUploadFile_EmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "", "text/csv", new byte[0]);

        mockMvc.perform(multipart("/api/import/upload")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("There is no file to import."));
    }

    @Test
    public void testUploadFile_InvalidFileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "xxx".getBytes());

        mockMvc.perform(multipart("/api/import/upload")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Only CSV files are allowed."));
    }

    @Test
    public void testUploadFile_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "aaa,bbb".getBytes());

        ImportStatus status = new ImportStatus();
        status.setId(1L);
        status.setStatus("COMPLETED");
        ImportStatusDto statusDto = new ImportStatusDto();
        statusDto.setId(1L);
        statusDto.setStatus("COMPLETED");

        when(importService.initializeImportStatus()).thenReturn(status);

        doAnswer(invocation -> {
            MultipartFile fileArgument = invocation.getArgument(0);
            ImportStatus importStatusArgument = invocation.getArgument(1);
            importStatusArgument.setStatus("COMPLETED");
            return null;
        }).when(importService).savePersonFromCsvFile(any(MultipartFile.class), any(ImportStatus.class));

        when(mapper.map(any(ImportStatus.class), eq(ImportStatusDto.class))).thenReturn(statusDto);

        mockMvc.perform(multipart("/api/import/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    public void testGetImportStatus_Success() throws Exception {
        ImportStatus status = new ImportStatus();
        status.setId(1L);
        status.setStatus("COMPLETED");
        ImportStatusDto statusDto = new ImportStatusDto();
        statusDto.setId(1L);
        statusDto.setStatus("COMPLETED");

        when(importService.getById(eq(1L))).thenReturn(status);
        when(mapper.map(any(ImportStatus.class), eq(ImportStatusDto.class))).thenReturn(statusDto);

        mockMvc.perform(get("/api/import/status/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}