package ru.aston.collection;


import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;

public class CustomCollection<T> extends AbstractCollection<T> {
	CustomNode<T> head;
	CustomNode<T> tail;
	int size;

	public CustomCollection() {
	}

	@Override
	public @NotNull CustomIterator<T> iterator() {
		return new CustomIterator<T>(this, head);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(T item) {
		CustomNode<T> tailBefore = tail;
		CustomNode<T> newNode = new CustomNode<>(item, tailBefore, null);

		tail = newNode;
		if (tailBefore == null) {
			head = newNode;
		}
		else {
			tailBefore.next = newNode;
		}

		++size;

		return true;
	}
}
