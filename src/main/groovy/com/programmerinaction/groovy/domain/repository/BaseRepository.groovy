package com.programmerinaction.groovy.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository

@NoRepositoryBean
interface BaseRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

}