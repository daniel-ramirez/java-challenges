package com.applaudostudios.entities;

import java.io.Serializable;

/**
 * MovieAudLogId mapped ID of MovieAudLog entity.
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
public class MovieAudLogId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long rev;

	private Long revtype;

	public MovieAudLogId() {
		super();
	}

	public MovieAudLogId(Long id, Long rev, Long revtype) {
		super();
		this.id = id;
		this.rev = rev;
		this.revtype = revtype;
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

}
