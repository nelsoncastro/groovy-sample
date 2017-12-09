package com.programmerinaction.groovy.domain.entity

import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity implements Serializable {

    static final long serialVersionUID = 1L

    abstract Serializable getId()

    boolean equals(obj) {
        if (this.is(obj)) return true
        if (getClass() != obj.class) return false

        BaseEntity other = (BaseEntity) obj
        if (getId() != other.id) return false

        return true
    }

    int hashCode() {
        return getId() == null ? 0 : getId().hashCode()
    }
}
