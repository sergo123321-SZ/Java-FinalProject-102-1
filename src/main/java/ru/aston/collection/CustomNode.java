package ru.aston.collection;


public class CustomNode<T> {
	T item;
	CustomNode<T> next;
	CustomNode<T> prev;

	CustomNode(T item, CustomNode<T> prev, CustomNode<T> next) {
		this.item = item;
		this.prev = prev;
		this.next = next;
	}
}
