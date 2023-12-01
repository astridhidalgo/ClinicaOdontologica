package com.backend.clinicaodontologica.service.impl;


import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceTest {

    @Autowired
    private TurnoService turnoService;


    @Order(1)
    @Test
    void deberiaRegistrarUnTurnoYRetornarElId() {

        try{
            TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2023, 12, 24,10,00,00), 1L , 1L);

            TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

            assertNotNull(turnoSalidaDto.getId());

        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Order(2)
    @Test
    void deberiaDevolverUnaListaDeTurnos(){
        try{
            List<TurnoSalidaDto> turnosDto = turnoService.listarTurnos();

            assertFalse(turnosDto.isEmpty());
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Test
    @Order(3)
    void deberiaBuscarUnOdontologoYRetornarElId(){
        try{
            TurnoSalidaDto turnoSalidaDto = turnoService.buscarTurnoPorId(1L);
            assertNotNull(turnoSalidaDto.getId());

        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

}