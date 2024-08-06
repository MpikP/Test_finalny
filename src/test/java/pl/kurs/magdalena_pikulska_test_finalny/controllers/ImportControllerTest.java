package pl.kurs.magdalena_pikulska_test_finalny.controllers;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;
import pl.kurs.magdalena_pikulska_test_finalny.dto.ImportStatusDto;
import pl.kurs.magdalena_pikulska_test_finalny.models.ImportStatus;
import pl.kurs.magdalena_pikulska_test_finalny.services.ImportService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.doThrow;

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
    public void shouldNotUploadFileBecauseOfEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "", "text/csv", new byte[0]);

        mockMvc.perform(multipart("/api/import/upload")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("There is no file to import."));
    }

    @Test
    public void shouldNotUploadFileBecauseOfInvalidFileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "xxx".getBytes());

        mockMvc.perform(multipart("/api/import/upload")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Only CSV files are allowed."));
    }

    @Test
    public void shouldUploadFileSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "aaa,bbb".getBytes());

        ImportStatus initialStatus = new ImportStatus();
        initialStatus.setId(1L);
        initialStatus.setStatus("IN_PROGRESS");
        ImportStatus status = new ImportStatus();
        status.setId(1L);
        status.setStatus("COMPLETED");
        ImportStatusDto statusDto = new ImportStatusDto();
        statusDto.setId(1L);
        statusDto.setStatus("COMPLETED");

        when(importService.initializeImportStatus()).thenReturn(initialStatus);

        doAnswer(invocation -> {
            MultipartFile fileArgument = invocation.getArgument(0);
            ImportStatus importStatusArgument = invocation.getArgument(1);
            importStatusArgument.setStatus("COMPLETED");
            return null;
        }).when(importService).savePersonFromCsvFile(any(MultipartFile.class), any(ImportStatus.class));

        when(mapper.map(any(ImportStatus.class), eq(ImportStatusDto.class))).thenReturn(statusDto);

        mockMvc.perform(multipart("/api/import/upload")
                        .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }


    @Test
    public void shouldGetImportStatusWhereId1() throws Exception {
        ImportStatus status = new ImportStatus();
        status.setId(1L);
        status.setStatus("COMPLETED");
        ImportStatusDto statusDto = new ImportStatusDto();
        statusDto.setId(1L);
        statusDto.setStatus("COMPLETED");

        when(importService.getById(eq(1L))).thenReturn(status);
        when(mapper.map(any(ImportStatus.class), eq(ImportStatusDto.class))).thenReturn(statusDto);

        mockMvc.perform(get("/api/import/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void shouldHandleImportFailureAndReturnError() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "aaa,bbb".getBytes());

        doThrow(new Exception("Failed to save people from CSV file")).when(importService).savePersonFromCsvFile(any(), any());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/people/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/people/upload")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("BAD_REQUEST"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorsMessages[0].message").value("An unexpected error occurred: No static resource api/people/upload."));
    }

}