package br.com.mangarosa.datastructures.abstractClasses;

public abstract class QueueNode<E> {

    public QueueNode<E> next;
    public E value;

    public QueueNode<E> getNext() {
        return next;
    }

    public void setNext(QueueNode<E> next) {
        this.next = next;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
