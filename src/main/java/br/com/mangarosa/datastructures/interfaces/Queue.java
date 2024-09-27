package br.com.mangarosa.datastructures.interfaces;

/**
 * A fila do tipo T
 * @param <T> um tipo de dados
 */
public interface Queue<T extends Comparable<T>> extends Iterable<T> {

    /**
     * Insere um valor no final da fila
     * @param value - um valor de qualquer tipo T que vai ser inserido na fila
     */
    void enqueue(T value);

    /***
     * Retorna o elemento no início da fila
     * @return o primeiro elemento da fila
     */
    T dequeue();

    /***
     * Retorna o primeiro elemento no início da fila, mas não
     * o remove da fila.
     * @return o primeiro elemento da fila
     */
    T peek();

    /**
     * Retorna se o a fila está vazia ou não
     * @return se a fila está vazia ou não
     */
    boolean isEmpty();

    /**
     * Retorna o tamanho da fila.
     * @return quantidade de elementos da fila
     */
    int size();

    /**
     * Retorna todos os elementos da fila em um array
     * @return todos os elementos da fila
     */
    T[] toArray();

    /***
     * Limpa a fila.
     */
    void clear();
}
