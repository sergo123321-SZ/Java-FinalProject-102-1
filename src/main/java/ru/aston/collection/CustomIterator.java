package ru.aston.collection;


import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CustomIterator<T> implements ListIterator<T> {
	CustomCollection<T> collection;
	CustomNode<T> next;
	CustomNode<T> prev;
	CustomNode<T> lastReturned;
	int nextIndex = 0;

	/**
	 * The special state, indicates the last call 1 - next() or -1 - previous() 0 -
	 * nothing
	 */
	private int lastCall = 0;

	CustomIterator(CustomCollection<T> collection, CustomNode<T> prev, CustomNode<T> next, int nextIndex) {
		this.collection = collection;
		this.next = next;
		this.prev = prev;
		this.nextIndex = nextIndex;
	}

	CustomIterator(CustomCollection<T> collection, CustomNode<T> next) {
		this.collection = collection;
		this.next = next;
	}

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		lastReturned = next;

		prev = next;
		next = next.next;

		nextIndex++;

		lastCall = 1;

		return lastReturned.item;
	}

	@Override
	public boolean hasPrevious() {
		return prev != null;
	}

	@Override
	public T previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		}

		lastReturned = prev;

		next = prev;
		prev = prev.prev;

		nextIndex--;

		lastCall = -1;

		return lastReturned.item;
	}

	@Override
	public int nextIndex() {
		return nextIndex;
	}

	@Override
	public int previousIndex() {
		return nextIndex - 1;
	}

	@Override
	public void remove() {
		assert collection.size <= 0;

		if (lastReturned == null) {
			throw new IllegalStateException("Unable to remove item. Call next() or previous() before calling remove()");
		}


		lastReturned = null;
		--collection.size;
	}

	@Override
	public void set(T item) {
		if (lastReturned == null) {
			throw new IllegalStateException("Unable to set item. Call next() or previous() before calling remove()");
		}
		lastReturned.item = item;
	}

	@Override
	public void add(T item) {
		CustomNode<T> newNode = new CustomNode<>(item, prev, next);
		if (prev != null) {
			prev.next = newNode;
		}
		else {
			collection.head = newNode;
		}

		if (next != null) {
			next.prev = newNode;
		}
		else {
			collection.tail = newNode;
		}

		prev = newNode;
		++nextIndex;
		++collection.size;

		lastReturned = null;
		lastCall = 0;
	}
}
