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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(name = "cargo")
public class Cargo   implements Serializable  {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Cargo().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCargoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Cargo o", Long.class).getSingleResult();
    }

	public static List<Cargo> findAllCargoes() {
        return entityManager().createQuery("SELECT o FROM Cargo o", Cargo.class).getResultList();
    }

	public static Cargo findCargo(Integer idcargo) {
        if (idcargo == null) return null;
        return entityManager().find(Cargo.class, idcargo);
    }

	public static List<Cargo> findCargoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Cargo o", Cargo.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Cargo attached = Cargo.findCargo(this.idcargo);
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
    public Cargo merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Cargo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("accesoes", "usuarios", "idarea").toString();
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idcargo")
    private Integer idcargo;

	public Integer getIdcargo() {
        return this.idcargo;
    }

	public void setIdcargo(Integer id) {
        this.idcargo = id;
    }

	@OneToMany(mappedBy = "idcargo")
    private Set<Acceso> accesoes;

	@OneToMany(mappedBy = "idcargo")
    private Set<Usuario> usuarios;

	@ManyToOne
    @JoinColumn(name = "idarea", referencedColumnName = "idarea")
    private Area idarea;

	@Column(name = "nomcargo", length = 200)
    private String nomcargo;

	public Set<Acceso> getAccesoes() {
        return accesoes;
    }

	public void setAccesoes(Set<Acceso> accesoes) {
        this.accesoes = accesoes;
    }

	public Set<Usuario> getUsuarios() {
        return usuarios;
    }

	public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

	public Area getIdarea() {
        return idarea;
    }

	public void setIdarea(Area idarea) {
        this.idarea = idarea;
    }

	public String getNomcargo() {
        return nomcargo;
    }

	public void setNomcargo(String nomcargo) {
        this.nomcargo = nomcargo;
    }
}
