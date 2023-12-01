package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.OdontologoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologoConMatricula0123456789YRetornarId() throws BadRequestException {
        OdontologoEntradaDto odontologo = new OdontologoEntradaDto("0123456789", "Fernando", "Mercy");
        OdontologoSalidaDto odontologoSalida = odontologoService.registrarOdontologo(odontologo);
        assertNotNull(odontologoSalida.getId());
        assertEquals("0123456789",odontologoSalida.getMatricula());
    }

    @Test
    @Order(2)
    public void nodeberiaRegistrarOdontologoConMatriculaDuplicada_deberiaLanzarUnBadRequestException() throws BadRequestException {
        OdontologoEntradaDto odontologoExistenteDto = new OdontologoEntradaDto("0123456789", "Laura", "Mora");
        assertThrows(BadRequestException.class,()->odontologoService.registrarOdontologo(odontologoExistenteDto));
    }

    @Test
    @Transactional
    public void deberiaActualizarNombreDelOdontologoFernandoPorJuan() throws ResourceNotFoundException, BadRequestException {
        OdontologoEntradaDto odontologoaCrear = new OdontologoEntradaDto("0123456788", "Fernando", "Mercy");
        odontologoService.registrarOdontologo(odontologoaCrear);
        OdontologoModificacionEntradaDto odontologo = new OdontologoModificacionEntradaDto(1L,"0123456789", "Juan", "Mercy");
        OdontologoSalidaDto resultado = odontologoService.actualizarOdontologo(odontologo);

        assertNotNull(resultado);
        assertEquals("Juan",resultado.getNombre());
    }

    }




