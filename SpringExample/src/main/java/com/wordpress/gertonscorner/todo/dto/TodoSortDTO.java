/**
 * 
 */
package com.wordpress.gertonscorner.todo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Todo sort transfer object. Contains the sorted list of todos
 * 
 * @author Gerton
 *
 */
public class TodoSortDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3110872060364853835L;
	
	private Collection<String> sortedIds = new ArrayList<String>(0);

	/**
	 * @param sortedIds the sortedIds to set
	 */
	public void setSortedIds(Collection<String> sortedIds) {
		this.sortedIds = sortedIds;
	}

	/**
	 * @return the sortedIds
	 */
	public Collection<String> getSortedIds() {
		return sortedIds;
	}
}
