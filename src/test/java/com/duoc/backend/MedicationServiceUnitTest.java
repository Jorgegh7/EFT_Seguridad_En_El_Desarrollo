package com.duoc.backend;

import com.duoc.backend.Medication.Medication;
import com.duoc.backend.Medication.MedicationRepository;
import com.duoc.backend.Medication.MedicationService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicationServiceUnitTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getAllMedications retorna todos los medicamentos")
    void getAllMedications() {
        Medication m1 = new Medication();
        Medication m2 = new Medication();
        when(medicationRepository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Medication> resultado = medicationService.getAllMedications();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(medicationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllMedications retorna lista vacia")
    void getAllMedicationsVacia() {
        when(medicationRepository.findAll()).thenReturn(Collections.emptyList());
        List<Medication> resultado = medicationService.getAllMedications();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("getMedicationById retorna medicamento existente")
    void getMedicationByIdExiste() {
        Medication med = new Medication();
        med.setId(1L);
        med.setName("Paracetamol");
        med.setCost(2000.0);
        when(medicationRepository.findById(1L)).thenReturn(Optional.of(med));

        Medication resultado = medicationService.getMedicationById(1L);

        assertNotNull(resultado);
        assertEquals("Paracetamol", resultado.getName());
    }

    @Test
    @DisplayName("getMedicationById retorna null cuando no existe")
    void getMedicationByIdNoExiste() {
        when(medicationRepository.findById(99L)).thenReturn(Optional.empty());

        Medication resultado = medicationService.getMedicationById(99L);

        assertNull(resultado);
    }

    @Test
    @DisplayName("saveMedication guarda correctamente")
    void saveMedication() {
        Medication med = new Medication();
        med.setName("Antiparasitario");
        med.setCost(8000.0);
        when(medicationRepository.save(med)).thenReturn(med);

        Medication resultado = medicationService.saveMedication(med);

        assertNotNull(resultado);
        assertEquals("Antiparasitario", resultado.getName());
        verify(medicationRepository).save(med);
    }

    @Test
    @DisplayName("deleteMedication elimina correctamente")
    void deleteMedication() {
        doNothing().when(medicationRepository).deleteById(1L);
        medicationService.deleteMedication(1L);
        verify(medicationRepository, times(1)).deleteById(1L);
    }
}
