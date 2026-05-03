package com.duoc.backend;

import com.duoc.backend.Care.Care;
import com.duoc.backend.Care.CareRepository;
import com.duoc.backend.Care.CareService;

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

class CareServiceUnitTest {

    @Mock
    private CareRepository careRepository;

    @InjectMocks
    private CareService careService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getAllCares retorna todos los servicios")
    void getAllCares() {
        Care c1 = new Care();
        Care c2 = new Care();
        when(careRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Care> resultado = careService.getAllCares();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(careRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllCares retorna lista vacia")
    void getAllCaresVacia() {
        when(careRepository.findAll()).thenReturn(Collections.emptyList());
        List<Care> resultado = careService.getAllCares();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("getCareById retorna servicio existente")
    void getCareByIdExiste() {
        Care care = new Care();
        care.setId(1L);
        care.setName("Consulta");
        care.setCost(20000.0);
        when(careRepository.findById(1L)).thenReturn(Optional.of(care));

        Care resultado = careService.getCareById(1L);

        assertNotNull(resultado);
        assertEquals("Consulta", resultado.getName());
        assertEquals(20000.0, resultado.getCost());
    }

    @Test
    @DisplayName("getCareById retorna null cuando no existe")
    void getCareByIdNoExiste() {
        when(careRepository.findById(99L)).thenReturn(Optional.empty());

        Care resultado = careService.getCareById(99L);

        assertNull(resultado);
    }

    @Test
    @DisplayName("saveCare guarda correctamente")
    void saveCare() {
        Care care = new Care();
        care.setName("Vacunacion");
        care.setCost(25000.0);
        when(careRepository.save(care)).thenReturn(care);

        Care resultado = careService.saveCare(care);

        assertNotNull(resultado);
        assertEquals("Vacunacion", resultado.getName());
        verify(careRepository).save(care);
    }

    @Test
    @DisplayName("deleteCare elimina correctamente")
    void deleteCare() {
        doNothing().when(careRepository).deleteById(1L);
        careService.deleteCare(1L);
        verify(careRepository, times(1)).deleteById(1L);
    }
}
