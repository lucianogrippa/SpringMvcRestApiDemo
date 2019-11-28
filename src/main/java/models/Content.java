package models;

import java.io.Serializable;

public class Content implements Serializable {
	private static final long serialVersionUID = 2174947401248457687L;
	private long id;
	private String description;
	public Content() {}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
