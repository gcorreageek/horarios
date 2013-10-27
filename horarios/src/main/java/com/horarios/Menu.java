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
@Table(name = "menu")
@Configurable
public class Menu implements Serializable  {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idmenu")
    private Integer idmenu;

	public Integer getIdmenu() {
        return this.idmenu;
    }

	public void setIdmenu(Integer id) {
        this.idmenu = id;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Menu().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countMenus() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Menu o", Long.class).getSingleResult();
    }

	public static List<Menu> findAllMenus() {
        return entityManager().createQuery("SELECT o FROM Menu o", Menu.class).getResultList();
    }

	public static Menu findMenu(Integer idmenu) {
        if (idmenu == null) return null;
        return entityManager().find(Menu.class, idmenu);
    }

	public static List<Menu> findMenuEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Menu o", Menu.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Menu attached = Menu.findMenu(this.idmenu);
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
    public Menu merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Menu merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("accesoes").toString();
    }

	@OneToMany(mappedBy = "idmenu")
    private Set<Acceso> accesoes;

	@Column(name = "nommenu", length = 200)
    private String nommenu;

	@Column(name = "urlmenu", length = 200)
    private String urlmenu;

	@Column(name = "icomenu", length = 200)
    private String icomenu;

	@Column(name = "tipomenu")
    private Integer tipomenu;

	@Column(name = "idsubmenu")
    private Integer idsubmenu;

	@Column(name = "ordenmenu")
    private Integer ordenmenu;

	@Column(name = "mastermenu")
    private Integer mastermenu;

	public Set<Acceso> getAccesoes() {
        return accesoes;
    }

	public void setAccesoes(Set<Acceso> accesoes) {
        this.accesoes = accesoes;
    }

	public String getNommenu() {
        return nommenu;
    }

	public void setNommenu(String nommenu) {
        this.nommenu = nommenu;
    }

	public String getUrlmenu() {
        return urlmenu;
    }

	public void setUrlmenu(String urlmenu) {
        this.urlmenu = urlmenu;
    }

	public String getIcomenu() {
        return icomenu;
    }

	public void setIcomenu(String icomenu) {
        this.icomenu = icomenu;
    }

	public Integer getTipomenu() {
        return tipomenu;
    }

	public void setTipomenu(Integer tipomenu) {
        this.tipomenu = tipomenu;
    }

	public Integer getIdsubmenu() {
        return idsubmenu;
    }

	public void setIdsubmenu(Integer idsubmenu) {
        this.idsubmenu = idsubmenu;
    }

	public Integer getOrdenmenu() {
        return ordenmenu;
    }

	public void setOrdenmenu(Integer ordenmenu) {
        this.ordenmenu = ordenmenu;
    }

	public Integer getMastermenu() {
        return mastermenu;
    }

	public void setMastermenu(Integer mastermenu) {
        this.mastermenu = mastermenu;
    }
}
