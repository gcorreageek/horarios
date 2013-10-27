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
@Table(name = "acceso")
public class Acceso  implements Serializable  {

	@ManyToOne
    @JoinColumn(name = "idcargo", referencedColumnName = "idcargo")
    private Cargo idcargo;

	@ManyToOne
    @JoinColumn(name = "idmenu", referencedColumnName = "idmenu")
    private Menu idmenu;

	@Column(name = "habilitado", length = 200)
    private String habilitado;

	public Cargo getIdcargo() {
        return idcargo;
    }

	public void setIdcargo(Cargo idcargo) {
        this.idcargo = idcargo;
    }

	public Menu getIdmenu() {
        return idmenu;
    }

	public void setIdmenu(Menu idmenu) {
        this.idmenu = idmenu;
    }

	public String getHabilitado() {
        return habilitado;
    }

	public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idcargo", "idmenu").toString();
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idacceso")
    private Integer idacceso;

	public Integer getIdacceso() {
        return this.idacceso;
    }

	public void setIdacceso(Integer id) {
        this.idacceso = id;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Acceso().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countAccesoes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Acceso o", Long.class).getSingleResult();
    }

	public static List<Acceso> findAllAccesoes() {
        return entityManager().createQuery("SELECT o FROM Acceso o", Acceso.class).getResultList();
    }

	public static Acceso findAcceso(Integer idacceso) {
        if (idacceso == null) return null;
        return entityManager().find(Acceso.class, idacceso);
    }

	public static List<Acceso> findAccesoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Acceso o", Acceso.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Acceso attached = Acceso.findAcceso(this.idacceso);
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
    public Acceso merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Acceso merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
