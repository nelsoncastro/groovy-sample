package com.programmerinaction.groovy.domain.entity

import org.hibernate.envers.NotAudited

import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@MappedSuperclass
abstract class AuditEntitySupport extends EntitySupport {

    static final long serialVersionUID = 1L

    @NotAudited
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INCLUSAO", nullable = false)
    Date dataInclusao

    @NotAudited
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_ALTERACAO", nullable = false)
    Date dataAlteracao

    @NotAudited
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "COD_USUARIO_ALTERACAO", nullable = false, length = 50)
    String codUsuarioAlteracao


    @PrePersist
    protected final void prePersist() {
    }

    @PreUpdate
    protected final void preUpdate() {
    }
}
