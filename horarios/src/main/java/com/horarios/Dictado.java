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
@Table(name = "dictado")
public class Dictado implements Serializable  {

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idcurso", "iddocente").toString();
    }

	@ManyToOne
    @JoinColumn(name = "idcurso", referencedColumnName = "idcurso")
    private Curso idcurso;

	@ManyToOne
    @JoinColumn(name = "iddocente", referencedColumnName = "iddocente")
    private Docente iddocente;

	public Curso getIdcurso() {
        return idcurso;
    }

	public void setIdcurso(Curso idcurso) {
        this.idcurso = idcurso;
    }

	public Docente getIddocente() {
        return iddocente;
    }

	public void setIddocente(Docente iddocente) {
        this.iddocente = iddocente;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddictado")
    private Integer iddictado;

	public Integer getIddictado() {
        return this.iddictado;
    }

	public void setIddictado(Integer id) {
        this.iddictado = id;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Dictado().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDictadoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Dictado o", Long.class).getSingleResult();
    }

	public static List<Dictado> findAllDictadoes() {
        return entityManager().createQuery("SELECT o FROM Dictado o", Dictado.class).getResultList();
    }

	public static Dictado findDictado(Integer iddictado) {
        if (iddictado == null) return null;
        return entityManager().find(Dictado.class, iddictado);
    }

	public static List<Dictado> findDictadoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Dictado o", Dictado.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Dictado attached = Dictado.findDictado(this.iddictado);
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
    public Dictado merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Dictado merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
