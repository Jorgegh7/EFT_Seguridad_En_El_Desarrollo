package com.duoc.backend;

import com.duoc.backend.Appointment.Appointment;
import com.duoc.backend.Appointment.AppointmentController;
import com.duoc.backend.Appointment.AppointmentService;

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

class AppointmentControllerUnitTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getAllAppointments retorna lista de citas")
    void getAllAppointmentsRetornaLista() {
        Appointment a1 = new Appointment();
        Appointment a2 = new Appointment();
        when(appointmentService.getAllAppointments()).thenReturn(Arrays.asList(a1, a2));

        List<Appointment> resultado = appointmentController.getAllAppointments();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(appointmentService, times(1)).getAllAppointments();
    }

    @Test
    @DisplayName("getAllAppointments retorna lista vacia")
    void getAllAppointmentsVacia() {
        when(appointmentService.getAllAppointments()).thenReturn(Collections.emptyList());
        List<Appointment> resultado = appointmentController.getAllAppointments();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("getAppointmentById retorna cita")
    void getAppointmentById() {
        Appointment cita = new Appointment();
        cita.setId(1L);
        when(appointmentService.getAppointmentById(1L)).thenReturn(cita);

        Appointment resultado = appointmentController.getAppointmentById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(appointmentService).getAppointmentById(1L);
    }

    @Test
    @DisplayName("getAppointmentById retorna null cuando no existe")
    void getAppointmentByIdNoExiste() {
        when(appointmentService.getAppointmentById(99L)).thenReturn(null);

        Appointment resultado = appointmentController.getAppointmentById(99L);

        assertNull(resultado);
        verify(appointmentService).getAppointmentById(99L);
    }

    @Test
    @DisplayName("saveAppointment guarda correctamente")
    void saveAppointment() {
        Appointment cita = new Appointment();
        cita.setDate(LocalDate.of(2026, 5, 1));
        cita.setTime(LocalTime.of(10, 30));
        cita.setReason("Control general");
        cita.setVeterinarian("Dr. Lopez");
        when(appointmentService.saveAppointment(cita)).thenReturn(cita);

        Appointment resultado = appointmentController.saveAppointment(cita);

        assertNotNull(resultado);
        assertEquals("Control general", resultado.getReason());
        verify(appointmentService).saveAppointment(cita);
    }

    @Test
    @DisplayName("deleteAppointment llama al servicio")
    void deleteAppointment() {
        doNothing().when(appointmentService).deleteAppointment(1L);
        appointmentController.deleteAppointment(1L);
        verify(appointmentService, times(1)).deleteAppointment(1L);
    }
}
