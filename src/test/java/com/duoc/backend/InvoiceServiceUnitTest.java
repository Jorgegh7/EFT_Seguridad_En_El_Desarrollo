package com.duoc.backend;

import com.duoc.backend.Care.Care;
import com.duoc.backend.Care.CareRepository;
import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.Invoice.InvoiceRepository;
import com.duoc.backend.Invoice.InvoiceService;
import com.duoc.backend.Medication.Medication;
import com.duoc.backend.Medication.MedicationRepository;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceUnitTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private CareRepository careRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getAllInvoices retorna todas las facturas")
    void getAllInvoices() {
        Invoice i1 = new Invoice();
        Invoice i2 = new Invoice();
        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(i1, i2));

        Iterable<Invoice> resultado = invoiceService.getAllInvoices();

        assertNotNull(resultado);
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getInvoiceById retorna factura existente")
    void getInvoiceByIdExiste() {
        Invoice factura = new Invoice();
        factura.setId(1L);
        factura.setPatientName("Luna");
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(factura));

        Invoice resultado = invoiceService.getInvoiceById(1L);

        assertNotNull(resultado);
        assertEquals("Luna", resultado.getPatientName());
    }

    @Test
    @DisplayName("getInvoiceById retorna null cuando no existe")
    void getInvoiceByIdNoExiste() {
        when(invoiceRepository.findById(99L)).thenReturn(Optional.empty());

        Invoice resultado = invoiceService.getInvoiceById(99L);

        assertNull(resultado);
    }

    @Test
    @DisplayName("saveInvoice calcula costo total correctamente")
    void saveInvoiceCalculaCosto() {
        // Preparar medicamentos
        Medication med1 = new Medication();
        med1.setId(1L);
        med1.setName("Amoxicilina");
        med1.setCost(5000.0);

        Medication med2 = new Medication();
        med2.setId(2L);
        med2.setName("Ibuprofeno");
        med2.setCost(3000.0);

        // Preparar servicios
        Care care1 = new Care();
        care1.setId(1L);
        care1.setName("Consulta");
        care1.setCost(20000.0);

        // Preparar factura
        Invoice factura = new Invoice();
        factura.setPatientName("Max");
        factura.setDate(LocalDate.of(2026, 5, 1));
        factura.setTime(LocalTime.of(11, 0));
        factura.setMedications(Arrays.asList(med1, med2));
        factura.setCares(Arrays.asList(care1));

        // Mock de repositorios
        when(medicationRepository.findAllById(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(med1, med2));
        when(careRepository.findAllById(Arrays.asList(1L)))
                .thenReturn(Arrays.asList(care1));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(factura);

        Invoice resultado = invoiceService.saveInvoice(factura);

        assertNotNull(resultado);
        // Costo total = 20000 (care) + 5000 + 3000 (meds) = 28000
        assertEquals(28000.0, factura.getTotalCost());
        verify(invoiceRepository).save(factura);
    }

    @Test
    @DisplayName("saveInvoice lanza excepcion cuando medicamento no existe")
    void saveInvoiceMedicamentoInvalido() {
        Medication med1 = new Medication();
        med1.setId(1L);

        Care care1 = new Care();
        care1.setId(1L);

        Invoice factura = new Invoice();
        factura.setMedications(Arrays.asList(med1));
        factura.setCares(Arrays.asList(care1));

        // Solo retorna lista vacia (medicamento no encontrado)
        when(medicationRepository.findAllById(Arrays.asList(1L)))
                .thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.saveInvoice(factura);
        });
    }

    @Test
    @DisplayName("saveInvoice lanza excepcion cuando servicio no existe")
    void saveInvoiceCareInvalido() {
        Medication med1 = new Medication();
        med1.setId(1L);
        med1.setCost(5000.0);

        Care care1 = new Care();
        care1.setId(1L);

        Invoice factura = new Invoice();
        factura.setMedications(Arrays.asList(med1));
        factura.setCares(Arrays.asList(care1));

        // Medicamentos encontrados correctamente
        when(medicationRepository.findAllById(Arrays.asList(1L)))
                .thenReturn(Arrays.asList(med1));
        // Care no encontrado
        when(careRepository.findAllById(Arrays.asList(1L)))
                .thenReturn(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.saveInvoice(factura);
        });
    }

    @Test
    @DisplayName("deleteInvoice elimina correctamente")
    void deleteInvoice() {
        doNothing().when(invoiceRepository).deleteById(1L);
        invoiceService.deleteInvoice(1L);
        verify(invoiceRepository, times(1)).deleteById(1L);
    }
}
