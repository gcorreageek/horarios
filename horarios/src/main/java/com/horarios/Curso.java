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
@Table(name = "curso")
public class Curso  implements Serializable {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Curso().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCursoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Curso o", Long.class).getSingleResult();
    }

	public static List<Curso> findAllCursoes() {
        return entityManager().createQuery("SELECT o FROM Curso o", Curso.class).getResultList();
    }

	public static Curso findCurso(Integer idcurso) {
        if (idcurso == null) return null;
        return entityManager().find(Curso.class, idcurso);
    }

	public static List<Curso> findCursoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Curso o", Curso.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Curso attached = Curso.findCurso(this.idcurso);
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
    public Curso merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Curso merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("dictadoes").toString();
    }

	@OneToMany(mappedBy = "idcurso")
    private Set<Dictado> dictadoes;

	@Column(name = "nomcurso", length = 200)
    private String nomcurso;

	public Set<Dictado> getDictadoes() {
        return dictadoes;
    }

	public void setDictadoes(Set<Dictado> dictadoes) {
        this.dictadoes = dictadoes;
    }

	public String getNomcurso() {
        return nomcurso;
    }

	public void setNomcurso(String nomcurso) {
        this.nomcurso = nomcurso;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idcurso")
    private Integer idcurso;

	public Integer getIdcurso() {
        return this.idcurso;
    }

	public void setIdcurso(Integer id) {
        this.idcurso = id;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static Curso getCursoXNombre(Curso c) {
		Query q = entityManager().createQuery("SELECT o FROM Curso o where o.nomcurso = :nomm", Curso.class);
		q.setParameter("nomm", c.getNomcurso());
		return (Curso) q.getResultList().get(0); 
    }
	
	
	
}
