package com.applaudostudios.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * MovieAudLog is an entity mapping of movie_aud_log table.
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@Entity(name = "movie_aud_log")
@IdClass(MovieAudLogId.class)
@EntityListeners(AuditingEntityListener.class)
public class MovieAudLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Id
	private Long rev;

	@Id
	private Long revtype;

	private String title;

	@Column(name = "rental_price")
	private BigDecimal rentalPrice;

	@Column(name = "sale_price")
	private BigDecimal salePrice;

	public MovieAudLog() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRev() {
		return rev;
	}

	public void setRev(Long rev) {
		this.rev = rev;
	}

	public Long getRevtype() {
		return revtype;
	}

	public void setRevtype(Long revtype) {
		this.revtype = revtype;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getRentalPrice() {
		return rentalPrice;
	}

	public void setRentalPrice(BigDecimal rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

}
