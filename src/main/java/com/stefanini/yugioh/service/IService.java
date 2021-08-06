package com.stefanini.yugioh.service;


import java.util.List;
import java.util.Optional;

public interface IService<T> {
    List<T> getAll();

    Optional<T> getOne(Long id);

    T save(T var);

    void delete(Long id);
}
