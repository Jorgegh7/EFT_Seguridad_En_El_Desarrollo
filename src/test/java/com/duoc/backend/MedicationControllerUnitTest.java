package com.duoc.backend;

import com.duoc.backend.Medication.Medication;
import com.duoc.backend.Medication.MedicationController;
import com.duoc.backend.Medication.MedicationService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicationControllerUnitTest {

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private MedicationController medicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getAllMedications retorna lista de medicamentos")
    void getAllMedicationsRetornaLista() {
        Medication m1 = new Medication();
        Medication m2 = new Medication();
        when(medicationService.getAllMedications()).thenReturn(Arrays.asList(m1, m2));

        List<Medication> resultado = medicationController.getAllMedications();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(medicationService, times(1)).getAllMedications();
    }

    @Test
    @DisplayName("getAllMedications retorna lista vacia")
    void getAllMedicationsVacia() {
        when(medicationService.getAllMedications()).thenReturn(Collections.emptyList());
        List<Medication> resultado = medicationController.getAllMedications();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("getMedicationById retorna medicamento")
    void getMedicationById() {
        Medication med = new Medication();
        med.setId(1L);
        med.setName("Amoxicilina");
        med.setCost(5000.0);
        when(medicationService.getMedicationById(1L)).thenReturn(med);

        Medication resultado = medicationController.getMedicationById(1L);

        assertNotNull(resultado);
        assertEquals("Amoxicilina", resultado.getName());
        assertEquals(5000.0, resultado.getCost());
    }

    @Test
    @DisplayName("getMedicationById retorna null cuando no existe")
    void getMedicationByIdNoExiste() {
        when(medicationService.getMedicationById(99L)).thenReturn(null);

        Medication resultado = medicationController.getMedicationById(99L);

        assertNull(resultado);
    }

    @Test
    @DisplayName("saveMedication guarda correctamente")
    void saveMedication() {
        Medication med = new Medication();
        med.setName("Ibuprofeno");
        med.setCost(3000.0);
        when(medicationService.saveMedication(med)).thenReturn(med);

        Medication resultado = medicationController.saveMedication(med);

        assertNotNull(resultado);
        assertEquals("Ibuprofeno", resultado.getName());
        verify(medicationService).saveMedication(med);
    }

    @Test
    @DisplayName("deleteMedication llama al servicio")
    void deleteMedication() {
        doNothing().when(medicationService).deleteMedication(1L);
        medicationController.deleteMedication(1L);
        verify(medicationService, times(1)).deleteMedication(1L);
    }
}
