package com.backend.clinicaodontologica.repository;

import java.util.List;

public interface IRepository<T> {
    T registrar(T t);
    List<T> listarTodos();
    T buscarPorId(int id);
}
