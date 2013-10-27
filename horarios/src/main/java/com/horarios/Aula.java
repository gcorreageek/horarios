package com.horarios;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "aula")
@Configurable
public class Aula implements Serializable   {

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Column(name = "nomaula", length = 200)
    private String nomaula;

	public String getNomaula() {
        return nomaula;
    }

	public void setNomaula(String nomaula) {
        this.nomaula = nomaula;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idaula")
    private Integer idaula;

	public Integer getIdaula() {
        return this.idaula;
    }

	public void setIdaula(Integer id) {
        this.idaula = id;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Aula().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAulas() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Aula o", Long.class).getSingleResult();
    }

	public static List<Aula> findAllAulas() {
        return entityManager().createQuery("SELECT o FROM Aula o", Aula.class).getResultList();
    }

	public static Aula findAula(Integer idaula) {
        if (idaula == null) return null;
        return entityManager().find(Aula.class, idaula);
    }

	public static List<Aula> findAulaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Aula o", Aula.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Aula attached = Aula.findAula(this.idaula);
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
    public Aula merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Aula merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
