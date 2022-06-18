package com.frge.wlmrt.adv_ds;

/**
 * Interface for a generic MaxHeap
 * 
 * @author Anudeep Ghosh
 *
 * @param <T> Generic type of which the heap implementing class will define
 */
public interface IMaxHeap<T> {

	/**
	 * Method to insert a new node in the heap
	 * 
	 * @param newInput node of type T
	 */
	void insert(T newInput);

	/**
	 * Method to pop the root element
	 * 
	 * @return the max element in the MaxHeap
	 */
	int popMax(); // poll()

	/**
	 * Method to show the root node without removing it
	 * 
	 * @return the maximum element
	 */
	int peek();

	/**
	 * Method to re-arrange the heap when initializing it with an existing array
	 */
	void heapify();

	/**
	 * Method to re-arrange the heap after removing the root node
	 */
	void restoreDown();

	/**
	 * Method to re-arrange the heap after adding a new node
	 */
	void restoreUp();

	/**
	 * Method to display the elements of the heap
	 */
	void showHeap();
}
