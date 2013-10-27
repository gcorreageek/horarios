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
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "area")
@Configurable
public class Area  implements Serializable   {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Area().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAreas() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Area o", Long.class).getSingleResult();
    }

	public static List<Area> findAllAreas() {
        return entityManager().createQuery("SELECT o FROM Area o", Area.class).getResultList();
    }

	public static Area findArea(Integer idarea) {
        if (idarea == null) return null;
        return entityManager().find(Area.class, idarea);
    }

	public static List<Area> findAreaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Area o", Area.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Area attached = Area.findArea(this.idarea);
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
    public Area merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Area merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@OneToMany(mappedBy = "idarea")
    private Set<Cargo> cargoes;

	@Column(name = "nomarea", length = 200)
    private String nomarea;

	public Set<Cargo> getCargoes() {
        return cargoes;
    }

	public void setCargoes(Set<Cargo> cargoes) {
        this.cargoes = cargoes;
    }

	public String getNomarea() {
        return nomarea;
    }

	public void setNomarea(String nomarea) {
        this.nomarea = nomarea;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idarea")
    private Integer idarea;

	public Integer getIdarea() {
        return this.idarea;
    }

	public void setIdarea(Integer id) {
        this.idarea = id;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("cargoes").toString();
    }
}
