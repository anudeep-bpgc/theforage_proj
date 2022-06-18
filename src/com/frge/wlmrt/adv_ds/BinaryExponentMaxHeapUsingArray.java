package com.frge.wlmrt.adv_ds;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class that implements a maxHeap 'heap' with each node having k children,
 * where k is a power of 2 which is provided by the user, default value of n is
 * 2
 * 
 * This heap implementation is done using array
 * 
 * @author Anudeep Ghosh
 *
 */
public class BinaryExponentMaxHeapUsingArray implements IMaxHeap<Integer> {

	/**
	 * Maximum children that a parent node can have
	 */
	private int maxChildren;

	/**
	 * Gives the size of the heap at any instance
	 */
	private int size = 0;

	/**
	 * Initial capacity of heap
	 */
	private int cap = 16;

	/**
	 * Array used for the heap implementation
	 */
	private int[] heap = new int[cap];

	/**
	 * Default constructor which defines maximum children a parent can have as 2
	 */
	public BinaryExponentMaxHeapUsingArray() {
		this.maxChildren = (int) Math.pow(2, 2);
	}

	/**
	 * Parameterized constructor to initialize the MaxHeap with custom exponent
	 * of two as the max number of children
	 * 
	 * @param exponentOfTwo Takes the power of 2 which is the number of max
	 *                      children a parent node can have
	 */
	public BinaryExponentMaxHeapUsingArray(int exponentOfTwo) {
		this.maxChildren = (int) Math.pow(2, exponentOfTwo);
	}

	// **************************************************************************************
	// Get the index of a node - parent/child

	/**
	 * Method to get the array index of the nth child node of a given parent
	 * index
	 * 
	 * @param  parentIndex index of parent whose child index is required
	 * @param  childNumber nth child whose index is required. Is 1-indexed
	 * @return             nth child index
	 */
	public int getNthChildNodeIndex(int parentIndex, int childNumber) {
		if (childNumber < 1 && childNumber > maxChildren)
			throw new RuntimeException(
					"Invalid Child Number.\nchildNumber can have values between 1 and exponentOfTwo, "
							+ "\nvalue used to initialize the constructor. "
							+ "\nexponentOfTwo is 2 for default constructor");
		return maxChildren * parentIndex + childNumber;
	}

	/**
	 * Method to return index of parent node in the array
	 * 
	 * @param  childNodeIndex Index of the child node in the array
	 * @return                index of the parent node in the array
	 */
	public int getParentNodeIndex(int childNodeIndex) {
		return (childNodeIndex - 1) / maxChildren;
	}

	/**
	 * Method to find index of the largest child node
	 * 
	 * @param  parentIndex index of the parent node whose largest child node is
	 *                     required
	 * @return             index of the largest child node
	 */
	private int getLargestChildIndex(int parentIndex) {
		int findElm = Collections.max(getAllChildren(parentIndex));
		return IntStream.range(0, heap.length).filter(i -> findElm == heap[i]).findAny()
				.orElseThrow(() -> new RuntimeException("Expected element not found"));
	}

	// **************************************************************************************
	// Check the presence of a node - parent/child

	/**
	 * Method to check if a parent has a certain child
	 * 
	 * @param  parentNodeIndex index of the parent node whose child is to be
	 *                         checked
	 * @param  childNumber     the nth child of all the possible children, one
	 *                         indexed left to right
	 * @return                 boolean value given if nth child is present or
	 *                         not
	 */
	public boolean hasNthChild(int parentNodeIndex, int childNumber) {
		return getNthChildNodeIndex(parentNodeIndex, childNumber) < size;
	}

	/**
	 * Method to check if a parent has any child or not
	 * 
	 * @param  parentNodeIndex index of the parent node whose child is to be
	 *                         checked
	 * @return                 boolean value given if any child is present or
	 *                         not
	 */
	public boolean hasChildren(int parentNodeIndex) {
		return getNthChildNodeIndex(parentNodeIndex, 1) < size;
	}

	/**
	 * Method to check if a child node has parent or not
	 * 
	 * @param  childNodeIndex index of the child node whose parent is to be
	 *                        checked
	 * @return                boolean value given if parent is present or not
	 */
	public boolean hasParent(int childNodeIndex) {
		return getParentNodeIndex(childNodeIndex) >= 0;
	}

	// **************************************************************************************
	// Get a particular node - parent/child

	/**
	 * Get the nth child node
	 * 
	 * @param  parentIndex index of the parent whose child node is required
	 * @param  childNumber nth child of the parent
	 * @return             the nth child node for the given parent node index
	 */
	public int getNthChild(int parentIndex, int childNumber) {
		return heap[getNthChildNodeIndex(parentIndex, childNumber)];
	}

	/**
	 * Get all the children of the given parent node as a list
	 * 
	 * @param  parentIndex index of the parent whose child node is required
	 * @return             list of children for the given parent node index
	 */
	public List<Integer> getAllChildren(int parentIndex) {
		int firstChildIndex = getNthChildNodeIndex(parentIndex, 1);
		if ((firstChildIndex - 1) / maxChildren == (size - 1) / maxChildren)
			return Arrays.stream(Arrays.copyOfRange(heap, firstChildIndex, size)).boxed().collect(Collectors.toList());

		return Arrays.stream(Arrays.copyOfRange(heap, firstChildIndex, firstChildIndex + maxChildren)).boxed()
				.collect(Collectors.toList());
	}

	/**
	 * Get the parent node
	 * 
	 * @param  childIndex index of the child whose parent node is required
	 * @return            the parent node for the given child node index
	 */
	public int getParent(int childIndex) {
		return heap[getParentNodeIndex(childIndex)];
	}

	// **************************************************************************************
	// Utility methods

	/**
	 * Method to check if the array has enough capacity to store new nodes else
	 * create a new array double the size of existing one
	 */
	private void checkCapacity() {
		if (size == cap) {
			heap = Arrays.copyOf(heap, cap * 2);
			cap *= 2;
		}
	}

	/**
	 * Method for swapping values in the array
	 * 
	 * @param node1 value, to swap node2 with
	 * @param node2 value, to swap node1 with
	 */
	private void swap(int node1, int node2) {
		int tmp = heap[node1];
		heap[node1] = heap[node2];
		heap[node2] = tmp;
	}

	@Override
	public int peek() {
		if (heap.length == 0)
			throw new RuntimeException("Heap is empty, cannot be queried");
		return heap[0];
	}

	@Override
	public int popMax() {
		if (heap.length == 0)
			throw new RuntimeException("Heap is empty, cannot be queried");
		int maxNode = heap[0];
		heap[0] = heap[size - 1];
		heap[size - 1] = 0;
		size--;
		restoreDown();
		return maxNode;
	}

	@Override
	public void insert(Integer newInput) {
		checkCapacity();
		heap[size] = newInput;
		size++;
		restoreUp();
	}

	@Override
	public void heapify() {

	}

	@Override
	public void restoreUp() {
		int index = size - 1;
		while (hasParent(index) && getParent(index) < heap[index]) {
			swap(getParentNodeIndex(index), index);
			index = getParentNodeIndex(index);
		}
	}

	@Override
	public void restoreDown() {
		int index = 0;
		while (hasChildren(index)) {
			int largestChildNodeIndex = getLargestChildIndex(index);
			if (heap[index] > heap[largestChildNodeIndex]) {
				break;
			}
			else {
				swap(index, largestChildNodeIndex);
			}
			index = largestChildNodeIndex;
		}
	}

	@Override
	public void showHeap() {
		System.out.println("Heap");
		for (int i : heap)
			System.out.print(i + " ");
		System.out.println();
	}

	public static void main(String[] args) {
		BinaryExponentMaxHeapUsingArray bEMH = new BinaryExponentMaxHeapUsingArray(2);
		bEMH.insert(1);
		bEMH.showHeap();
		int size = 100;
		for (int i = 0; i < size; i++) {
			bEMH.insert((int) (1000 * Math.random()));
			if (i % 10 == 0 && i > 0)
				bEMH.showHeap();
			if (i % 20 == 0 && i > 0) {
				System.out.println("Popped element - " + bEMH.popMax());
				// System.out.println("Popped element - " + bEMH.popMax());
				System.out.println("After popping");
				bEMH.showHeap();
				System.out.println("\n");
			}
		}
	}
}
