package com.duoc.backend;

import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.Invoice.InvoiceController;
import com.duoc.backend.Invoice.InvoiceService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceControllerUnitTest {

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getAllInvoices retorna lista de facturas")
    void getAllInvoicesRetornaLista() {
        Invoice i1 = new Invoice();
        Invoice i2 = new Invoice();
        when(invoiceService.getAllInvoices()).thenReturn(Arrays.asList(i1, i2));

        List<Invoice> resultado = invoiceController.getAllInvoices();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(invoiceService, times(1)).getAllInvoices();
    }

    @Test
    @DisplayName("getAllInvoices retorna lista vacia")
    void getAllInvoicesVacia() {
        when(invoiceService.getAllInvoices()).thenReturn(Collections.emptyList());
        List<Invoice> resultado = invoiceController.getAllInvoices();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("getInvoiceById retorna factura")
    void getInvoiceById() {
        Invoice factura = new Invoice();
        factura.setId(1L);
        factura.setPatientName("Firulais");
        when(invoiceService.getInvoiceById(1L)).thenReturn(factura);

        Invoice resultado = invoiceController.getInvoiceById(1L);

        assertNotNull(resultado);
        assertEquals("Firulais", resultado.getPatientName());
    }

    @Test
    @DisplayName("getInvoiceById retorna null cuando no existe")
    void getInvoiceByIdNoExiste() {
        when(invoiceService.getInvoiceById(99L)).thenReturn(null);

        Invoice resultado = invoiceController.getInvoiceById(99L);

        assertNull(resultado);
    }

    @Test
    @DisplayName("saveInvoice guarda correctamente")
    void saveInvoice() {
        Invoice factura = new Invoice();
        factura.setPatientName("Rex");
        factura.setDate(LocalDate.of(2026, 5, 1));
        factura.setTime(LocalTime.of(10, 0));
        factura.setTotalCost(50000.0);
        when(invoiceService.saveInvoice(factura)).thenReturn(factura);

        Invoice resultado = invoiceController.saveInvoice(factura);

        assertNotNull(resultado);
        assertEquals("Rex", resultado.getPatientName());
        assertEquals(50000.0, resultado.getTotalCost());
        verify(invoiceService).saveInvoice(factura);
    }

    @Test
    @DisplayName("deleteInvoice llama al servicio")
    void deleteInvoice() {
        doNothing().when(invoiceService).deleteInvoice(1L);
        invoiceController.deleteInvoice(1L);
        verify(invoiceService, times(1)).deleteInvoice(1L);
    }
}
