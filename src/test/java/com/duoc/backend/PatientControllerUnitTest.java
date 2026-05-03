package com.duoc.backend;

import com.duoc.backend.Patient.Patient;
import com.duoc.backend.Patient.PatientController;
import com.duoc.backend.Patient.PatientService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientControllerUnitTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("greetings retorna saludo con nombre")
    void greetingsConNombre() {
        String resultado = patientController.greetings("Jorge");
        assertEquals("Hello {Jorge}", resultado);
    }

    @Test
    @DisplayName("getAllPatients retorna lista")
    void getAllPatientsRetornaLista() {
        Patient p1 = new Patient();
        Patient p2 = new Patient();
        when(patientService.getAllPatients()).thenReturn(Arrays.asList(p1, p2));

        List<Patient> resultado = patientController.getAllPatients();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    @DisplayName("getAllPatients retorna lista vacia")
    void getAllPatientsVacia() {
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());
        List<Patient> resultado = patientController.getAllPatients();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("getPatientById retorna paciente")
    void getPatientById() {
        Patient paciente = new Patient();
        when(patientService.getPatientById(1L)).thenReturn(paciente);

        Patient resultado = patientController.getPatientById(1L);

        assertNotNull(resultado);
        verify(patientService).getPatientById(1L);
    }

    @Test
    @DisplayName("savePatient guarda correctamente")
    void savePatient() {
        Patient paciente = new Patient();
        when(patientService.savePatient(paciente)).thenReturn(paciente);

        Patient resultado = patientController.savePatient(paciente);

        assertNotNull(resultado);
        verify(patientService).savePatient(paciente);
    }

    @Test
    @DisplayName("deletePatient llama al servicio")
    void deletePatient() {
        doNothing().when(patientService).deletePatient(1L);
        patientController.deletePatient(1L);
        verify(patientService, times(1)).deletePatient(1L);
    }
}