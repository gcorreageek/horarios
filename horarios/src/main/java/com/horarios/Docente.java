package com.horarios;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(name = "docente")
public class Docente  implements Serializable {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Docente().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countDocentes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Docente o", Long.class).getSingleResult();
    }

	public static List<Docente> findAllDocentes() {
        return entityManager().createQuery("SELECT o FROM Docente o", Docente.class).getResultList();
    }

	public static Docente findDocente(Integer iddocente) {
        if (iddocente == null) return null;
        return entityManager().find(Docente.class, iddocente);
    }

	public static List<Docente> findDocenteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Docente o", Docente.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Docente attached = Docente.findDocente(this.iddocente);
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
    public Docente merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Docente merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@OneToMany(mappedBy = "iddocente")
    private Set<Dictado> dictadoes;

	@OneToMany(mappedBy = "iddocente")
    private Set<Disponibilidad> disponibilidads;

	@Column(name = "nomdocente", length = 200)
    private String nomdocente;

	public Set<Dictado> getDictadoes() {
        return dictadoes;
    }

	public void setDictadoes(Set<Dictado> dictadoes) {
        this.dictadoes = dictadoes;
    }

	public Set<Disponibilidad> getDisponibilidads() {
        return disponibilidads;
    }

	public void setDisponibilidads(Set<Disponibilidad> disponibilidads) {
        this.disponibilidads = disponibilidads;
    }

	public String getNomdocente() {
        return nomdocente;
    }

	public void setNomdocente(String nomdocente) {
        this.nomdocente = nomdocente;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddocente")
    private Integer iddocente;

	public Integer getIddocente() {
        return this.iddocente;
    }

	public void setIddocente(Integer id) {
        this.iddocente = id;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("dictadoes", "disponibilidads").toString();
    }
	
	
	
	
	
	
	
	
	
	
	public static Docente getDocenteXNombre(Docente d) {
		Query q = entityManager().createQuery("SELECT o FROM Docente o where o.nomdocente = :nomm", Docente.class);
		q.setParameter("nomm", d.getNomdocente());
		return (Docente) q.getResultList().get(0); 
    }
	
	
	
	
}
