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
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "usuario")
@Configurable
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idusuario")
    private Integer idusuario;

	public Integer getIdusuario() {
        return this.idusuario;
    }

	public void setIdusuario(Integer id) {
        this.idusuario = id;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("idcargo").toString();
    }

	@ManyToOne
    @JoinColumn(name = "idcargo", referencedColumnName = "idcargo")
    private Cargo idcargo;

	@Column(name = "nomusuario", length = 200)
    private String nomusuario;

	@Column(name = "emailusuario", length = 200)
    private String emailusuario;

	@Column(name = "loginusuario", length = 200)
    private String loginusuario;

	@Column(name = "passusuario", length = 200)
    private String passusuario;

	@Column(name = "habilitado", length = 200)
    private String habilitado;

	public Cargo getIdcargo() {
        return idcargo;
    }

	public void setIdcargo(Cargo idcargo) {
        this.idcargo = idcargo;
    }

	public String getNomusuario() {
        return nomusuario;
    }

	public void setNomusuario(String nomusuario) {
        this.nomusuario = nomusuario;
    }

	public String getEmailusuario() {
        return emailusuario;
    }

	public void setEmailusuario(String emailusuario) {
        this.emailusuario = emailusuario;
    }

	public String getLoginusuario() {
        return loginusuario;
    }

	public void setLoginusuario(String loginusuario) {
        this.loginusuario = loginusuario;
    }

	public String getPassusuario() {
        return passusuario;
    }

	public void setPassusuario(String passusuario) {
        this.passusuario = passusuario;
    }

	public String getHabilitado() {
        return habilitado;
    }

	public void setHabilitado(String habilitado) {
        this.habilitado = habilitado;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Usuario().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUsuarios() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Usuario o", Long.class).getSingleResult();
    }

	public static List<Usuario> findAllUsuarios() {
        return entityManager().createQuery("SELECT o FROM Usuario o", Usuario.class).getResultList();
    }

	public static Usuario findUsuario(Integer idusuario) {
        if (idusuario == null) return null;
        return entityManager().find(Usuario.class, idusuario);
    }

	public static List<Usuario> findUsuarioEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Usuario o", Usuario.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Usuario attached = Usuario.findUsuario(this.idusuario);
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
    public Usuario merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Usuario merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
	
	
	
	
	
	
	
	
	
	
	
	public static Usuario getUsuariosXLogin(Usuario u) {
		Query q = entityManager().createQuery("SELECT o FROM Usuario o where o.loginusuario = :login", Usuario.class);
		q.setParameter("login", u.getLoginusuario());
		return (Usuario) q.getResultList().get(0); 
    }
	
	
	
	
	
	
	
}
