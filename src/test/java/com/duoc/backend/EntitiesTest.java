package com.duoc.backend;

import com.duoc.backend.Care.Care;
import com.duoc.backend.Invoice.Invoice;
import com.duoc.backend.Medication.Medication;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntitiesTest {

    @Test
    @DisplayName("Care getters y setters funcionan correctamente")
    void careGettersSetters() {
        Care care = new Care();
        care.setId(1L);
        care.setName("Consulta general");
        care.setCost(25000.0);

        assertEquals(1L, care.getId());
        assertEquals("Consulta general", care.getName());
        assertEquals(25000.0, care.getCost());
    }

    @Test
    @DisplayName("Medication getters y setters funcionan correctamente")
    void medicationGettersSetters() {
        Medication med = new Medication();
        med.setId(1L);
        med.setName("Amoxicilina");
        med.setCost(5000.0);

        assertEquals(1L, med.getId());
        assertEquals("Amoxicilina", med.getName());
        assertEquals(5000.0, med.getCost());
    }

    @Test
    @DisplayName("Invoice getters y setters funcionan correctamente")
    void invoiceGettersSetters() {
        Care care = new Care();
        care.setId(1L);
        care.setName("Baño");
        care.setCost(15000.0);

        Medication med = new Medication();
        med.setId(1L);
        med.setName("Paracetamol");
        med.setCost(2000.0);

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setPatientName("Firulais");
        invoice.setDate(LocalDate.of(2026, 5, 1));
        invoice.setTime(LocalTime.of(14, 30));
        invoice.setCares(Arrays.asList(care));
        invoice.setMedications(Arrays.asList(med));
        invoice.setTotalCost(17000.0);

        assertEquals(1L, invoice.getId());
        assertEquals("Firulais", invoice.getPatientName());
        assertEquals(LocalDate.of(2026, 5, 1), invoice.getDate());
        assertEquals(LocalTime.of(14, 30), invoice.getTime());
        assertEquals(1, invoice.getCares().size());
        assertEquals(1, invoice.getMedications().size());
        assertEquals(17000.0, invoice.getTotalCost());
    }
}
