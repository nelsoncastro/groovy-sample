package com.programmerinaction.groovy.domain.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BaseService<T,K> {

    T findById(K id)

    Page<T> findAll(Pageable pageRequest)

    T save(T entity)

    void delete(Long id)
}