package br.com.demo.groovy.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "BOOK")
class Book extends AuditEntitySupport {

    @Id
    @Column(name = "ID_BOOK")
    Long id

    String title
    String description
    String isbn
}
