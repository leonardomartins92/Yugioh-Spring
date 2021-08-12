package com.stefanini.yugioh.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface IService<T> {
    Page<T> getAll(Pageable pageable);

    Optional<T> getOne(Long id);

    T save(T var);

    void delete(Long id);
}
