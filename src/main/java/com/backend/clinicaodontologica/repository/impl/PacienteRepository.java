package com.backend.clinicaodontologica.repository.impl;

import com.backend.clinicaodontologica.model.Domicilio;
import com.backend.clinicaodontologica.model.Paciente;
import com.backend.clinicaodontologica.repository.H2Connection;
import com.backend.clinicaodontologica.repository.IRepository;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

public class PacienteRepository implements IRepository<Paciente> {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteRepository.class);
    private DomicilioRepository domicilioRepository;


    @Override
    public Paciente registrar(Paciente paciente) {
        Connection connection = null;
        Paciente pacienteRegistrado = null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            domicilioRepository = new DomicilioRepository();

            Domicilio domicilioRegistrado = domicilioRepository.registrar(paciente.getDomicilio());

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PACIENTES (NOMBRE, APELLIDO, DNI, FECHA, DOMICILIO_ID) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setInt(3, paciente.getDni());
            preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            preparedStatement.setInt(5, domicilioRegistrado.getId());
            preparedStatement.execute();

            pacienteRegistrado = new Paciente(paciente.getNombre(), paciente.getApellido(), paciente.getDni(), paciente.getFechaIngreso(), domicilioRegistrado);

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while(resultSet.next()) {
                pacienteRegistrado.setId(resultSet.getInt("id"));
            }

            connection.commit();
            LOGGER.info("Se ha registrado el paciente: " + pacienteRegistrado);

        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("Tuvimos un problema");
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                } catch (SQLException exception) {
                    LOGGER.error(exception.getMessage());
                    exception.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("No se pudo cerrar la conexion: " + ex.getMessage());
            }
        }


        return pacienteRegistrado;
    }

    @Override
    public List<Paciente> listarTodos() {
        Connection connection = null;
        List<Paciente> pacientes = new ArrayList<>();

        try{

            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PACIENTES");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Paciente paciente = crearObjetoPaciente(resultSet);
                pacientes.add(paciente);
            }

            LOGGER.info("Listado de todos los pacientes: " + pacientes);


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        return pacientes;
    }

    @Override
    public Paciente buscarPorId(int id) {
        Connection connection = null;
        Paciente paciente = null;

        try{
            connection = H2Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM PACIENTES WHERE ID = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                paciente = crearObjetoPaciente(rs);
            }

            if(paciente == null) LOGGER.error("No se ha encontrado el paciente con id: " + id);
            else LOGGER.info("Se ha encontrado el paciente: " + paciente);



        } catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }


        return paciente;
    }


    public Paciente actualizar(Paciente pacienteModificado) {

        Connection connection = null;
        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement("UPDATE PACIENTES SET NOMBRE = ?, APELLIDO = ?, DNI = ?, FECHA = ?, DOMICILIO_ID = ? WHERE ID = ?");
            ps.setString(1,pacienteModificado.getNombre());
            ps.setString(2, pacienteModificado.getApellido());
            ps.setInt(3, pacienteModificado.getDni());
            ps.setDate(4, Date.valueOf(pacienteModificado.getFechaIngreso()));
            ps.setInt(5, pacienteModificado.getDomicilio().getId());
            ps.setInt(6, pacienteModificado.getId());
            ps.execute();

            connection.commit();
            LOGGER.warn("El paciente con id " + pacienteModificado.getId() + "ha sido modificado: " + pacienteModificado);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                LOGGER.error("Ha ocurrido un error al intentar cerrar la bdd. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return pacienteModificado;
    }



    private Paciente crearObjetoPaciente(ResultSet resultSet) throws SQLException {

        Domicilio domicilio = new DomicilioRepository().buscarPorId(resultSet.getInt("domicilio_id"));

        return new Paciente(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("apellido"), resultSet.getInt("dni"), resultSet.getDate("fecha").toLocalDate(), domicilio);
    }
}
