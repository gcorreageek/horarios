package com.horarios;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "hora")
@Configurable
public class Hora implements Serializable {

	@OneToMany(mappedBy = "idhora")
    private Set<Disponibilidad> disponibilidads;

	@Column(name = "horainicio")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "M-")
    private Date horainicio;

	@Column(name = "horafin")
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "M-")
    private Date horafin;

	public Set<Disponibilidad> getDisponibilidads() {
        return disponibilidads;
    }

	public void setDisponibilidads(Set<Disponibilidad> disponibilidads) {
        this.disponibilidads = disponibilidads;
    }

	public Date getHorainicio() {
        return horainicio;
    }

	public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }

	public Date getHorafin() {
        return horafin;
    }

	public void setHorafin(Date horafin) {
        this.horafin = horafin;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Hora().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countHoras() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Hora o", Long.class).getSingleResult();
    }

	public static List<Hora> findAllHoras() {
        return entityManager().createQuery("SELECT o FROM Hora o", Hora.class).getResultList();
    }

	public static Hora findHora(Integer idhora) {
        if (idhora == null) return null;
        return entityManager().find(Hora.class, idhora);
    }

	public static List<Hora> findHoraEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Hora o", Hora.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Hora attached = Hora.findHora(this.idhora);
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
    public Hora merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Hora merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idhora")
    private Integer idhora;

	public Integer getIdhora() {
        return this.idhora;
    }

	public void setIdhora(Integer id) {
        this.idhora = id;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("disponibilidads").toString();
    }
}
