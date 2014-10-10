package com.kenneth.todo.model;

/**
 * Basic representation of a Model.
 *   
 * NOTE: Uses String as the type of <i>id</i>
 */
public interface Model {
	/**
	 * Retrieve model identifier.
	 * @return identifier
	 */
	public String getId();
	
	/**
	 * Set the model identifier.
	 * @param id identifier to set
	 */
	public void setId(String id);
}
