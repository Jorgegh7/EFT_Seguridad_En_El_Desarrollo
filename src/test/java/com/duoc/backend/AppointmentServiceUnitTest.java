package com.duoc.backend;

import com.duoc.backend.Appointment.Appointment;
import com.duoc.backend.Appointment.AppointmentRepository;
import com.duoc.backend.Appointment.AppointmentService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceUnitTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("getAllAppointments retorna todas las citas")
    void getAllAppointments() {
        Appointment a1 = new Appointment();
        Appointment a2 = new Appointment();
        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        Iterable<Appointment> resultado = appointmentService.getAllAppointments();

        assertNotNull(resultado);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAppointmentById retorna cita existente")
    void getAppointmentByIdExiste() {
        Appointment cita = new Appointment();
        cita.setId(1L);
        cita.setReason("Vacunacion");
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(cita));

        Appointment resultado = appointmentService.getAppointmentById(1L);

        assertNotNull(resultado);
        assertEquals("Vacunacion", resultado.getReason());
    }

    @Test
    @DisplayName("getAppointmentById retorna null cuando no existe")
    void getAppointmentByIdNoExiste() {
        when(appointmentRepository.findById(99L)).thenReturn(Optional.empty());

        Appointment resultado = appointmentService.getAppointmentById(99L);

        assertNull(resultado);
    }

    @Test
    @DisplayName("saveAppointment guarda correctamente")
    void saveAppointment() {
        Appointment cita = new Appointment();
        cita.setDate(LocalDate.of(2026, 5, 1));
        cita.setTime(LocalTime.of(14, 0));
        cita.setReason("Desparasitacion");
        cita.setVeterinarian("Dra. Martinez");
        when(appointmentRepository.save(cita)).thenReturn(cita);

        Appointment resultado = appointmentService.saveAppointment(cita);

        assertNotNull(resultado);
        assertEquals("Desparasitacion", resultado.getReason());
        assertEquals("Dra. Martinez", resultado.getVeterinarian());
        verify(appointmentRepository).save(cita);
    }

    @Test
    @DisplayName("deleteAppointment elimina correctamente")
    void deleteAppointment() {
        doNothing().when(appointmentRepository).deleteById(1L);
        appointmentService.deleteAppointment(1L);
        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}
