package com.horarios;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(name = "disponibilidad")
public class Disponibilidad  implements Serializable {

	@ManyToOne
    @JoinColumn(name = "iddocente", referencedColumnName = "iddocente")
    private Docente iddocente;

	@ManyToOne
    @JoinColumn(name = "idhora", referencedColumnName = "idhora")
    private Hora idhora;

	@Column(name = "dia")
    private Integer dia;

	public Docente getIddocente() {
        return iddocente;
    }

	public void setIddocente(Docente iddocente) {
        this.iddocente = iddocente;
    }

	public Hora getIdhora() {
        return idhora;
    }

	public void setIdhora(Hora idhora) {
        this.idhora = idhora;
    }

	public Integer getDia() {
        return dia;
    }

	public void setDia(Integer dia) {
        this.dia = dia;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("iddocente", "idhora").toString();
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddisponibilidad")
    private Integer iddisponibilidad;

	public Integer getIddisponibilidad() {
        return this.iddisponibilidad;
    }

	public void setIddisponibilidad(Integer id) {
        this.iddisponibilidad = id;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Disponibilidad().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDisponibilidads() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Disponibilidad o", Long.class).getSingleResult();
    }

	public static List<Disponibilidad> findAllDisponibilidads() {
        return entityManager().createQuery("SELECT o FROM Disponibilidad o", Disponibilidad.class).getResultList();
    }

	public static Disponibilidad findDisponibilidad(Integer iddisponibilidad) {
        if (iddisponibilidad == null) return null;
        return entityManager().find(Disponibilidad.class, iddisponibilidad);
    }

	public static List<Disponibilidad> findDisponibilidadEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Disponibilidad o", Disponibilidad.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Disponibilidad attached = Disponibilidad.findDisponibilidad(this.iddisponibilidad);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Disponibilidad merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Disponibilidad merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
