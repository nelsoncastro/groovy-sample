package com.programmerinaction.groovy.domain.entity

import org.hibernate.envers.NotAudited

import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
abstract class EntitySupport extends BaseEntity {

    static final long serialVersionUID = 1L

    @NotAudited
    @Version
    @Column(name = "NR_VERSAO")
    Integer versao

}
