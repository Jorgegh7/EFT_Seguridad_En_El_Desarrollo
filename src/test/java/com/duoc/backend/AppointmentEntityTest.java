package com.duoc.backend;

import com.duoc.backend.Appointment.Appointment;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentEntityTest {

    @Test
    @DisplayName("Appointment getters y setters funcionan correctamente")
    void appointmentGettersSetters() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDate(LocalDate.of(2026, 5, 1));
        appointment.setTime(LocalTime.of(10, 30));
        appointment.setReason("Vacunacion");
        appointment.setVeterinarian("Dr. Perez");

        assertEquals(1L, appointment.getId());
        assertEquals(LocalDate.of(2026, 5, 1), appointment.getDate());
        assertEquals(LocalTime.of(10, 30), appointment.getTime());
        assertEquals("Vacunacion", appointment.getReason());
        assertEquals("Dr. Perez", appointment.getVeterinarian());
    }
}
