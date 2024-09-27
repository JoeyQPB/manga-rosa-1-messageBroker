package br.com.mangarosa.datastructures.interfaces.impl;

import br.com.mangarosa.datastructures.abstractClasses.extd.QueueNodeMessage;
import br.com.mangarosa.datastructures.interfaces.Queue;
import br.com.mangarosa.messages.Message;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueue implements Queue<QueueNodeMessage> {

    private QueueNodeMessage firstNode;
    private QueueNodeMessage lastNode;
    private Integer size;

    public LinkedQueue() {
        this.firstNode = null;
        this.lastNode = null;
        this.size = 0;
    }

    @Override
    public void enqueue(QueueNodeMessage newNode) {

        if (this.size.equals(0)){
            this.firstNode = newNode;
            this.lastNode = newNode;
        } else {
            QueueNodeMessage pointer;
            pointer = this.lastNode;
            this.lastNode = newNode;
            pointer.setNext(newNode);
        }

        this.size = this.size + 1;
    }

    @Override
    public QueueNodeMessage dequeue() {

        if (this.firstNode == null) {
            throw new IllegalStateException("A fila está vazia.");
        }

        QueueNodeMessage node = this.firstNode;
        this.firstNode = (QueueNodeMessage) this.firstNode.getNext();

        if (this.firstNode == null) {
            this.lastNode = null;
        }
        this.size--;
        return node;
    }

    // if necessarily
    public Message dequeueValue() {

        if (this.firstNode == null) {
            throw new IllegalStateException("A fila está vazia.");
        }

        QueueNodeMessage node = this.firstNode;
        this.firstNode = (QueueNodeMessage) this.firstNode.getNext();

        if (this.firstNode == null) {
            this.lastNode = null;
        }
        this.size--;
        return node.getValue(); // Retorna o valor removido
    }

    @Override
    public QueueNodeMessage peek() {
        if (this.firstNode == null) {
            throw new IllegalStateException("A fila está vazia.");
        }

        return this.firstNode;
    }

    @Override
    public boolean isEmpty() {
        return this.firstNode == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public QueueNodeMessage[] toArray() {
        QueueNodeMessage[] arr = new QueueNodeMessage[this.size()];
        QueueNodeMessage current = this.firstNode;
        int index = 0;

        while (current != null) {
            arr[index] = current;
            current = (QueueNodeMessage) current.getNext();
            index++;
        }

        return arr;
    }

//    @Override
//    public QueueNodeMessage[] toArray() {
//        QueueNodeMessage[] arr = new QueueNodeMessage[this.size()];
//        Iterator<QueueNodeMessage> iterator = this.iterator();
//        int index = 0;
//
//        while (iterator.hasNext()) {
//            arr[index] = iterator.next();
//            index++;
//        }
//
//        return arr;
//    }

    // if necessarily
    public Message[] toArrayValues() {
        Message[] arr = new Message[this.size];
        QueueNodeMessage current = this.firstNode;
        int index = 0;

        while (current != null) {
            arr[index] = current.getValue();
            current = (QueueNodeMessage) current.getNext();
            index++;
        }

        return arr;
    }

    @Override
    public void clear() {
        QueueNodeMessage current = this.firstNode;

        while (current != null) {
            QueueNodeMessage next = (QueueNodeMessage) current.getNext();
            current.setNext(null);
            current.setValue(null);
            current = next;
        }

        this.firstNode = null;
        this.lastNode = null;
        this.size = 0;
    }

//    @Override
//    public void clear() {
//        this.firstNode = null;
//        this.lastNode = null;
//        this.size = 0;
//    }
//    O Que Acontece com as Referências de Memória?
//    Quando você define os nós firstNode e lastNode como null, você remove as referências da sua estrutura de dados
//    para os objetos que estavam armazenados na fila. No entanto, os objetos não são imediatamente removidos da memória.
//    Eles se tornam candidatos à coleta de lixo (garbage collection), uma vez que não há mais referências ativas para eles.
//
//    Em Java, o Garbage Collector (GC) é responsável por liberar a memória de objetos que não têm mais referências.
//    Assim, ao definir os nós como null, o GC eventualmente irá limpar a memória que estava sendo ocupada pelos nós
//    antigos da fila, mas isso não acontece imediatamente.
//
//    Referências de Memória e Coleta de Lixo
//    Após o método clear() ser executado:
//
//    Referências de nós são removidas: Ao definir firstNode e lastNode como null, os nós que estavam sendo referenciados
//    pela sua lista encadeada não têm mais referências "ativas". Como resultado, os nós e os objetos dentro deles se
//    tornam elegíveis para coleta de lixo.
//
//    Coletor de Lixo: O coletor de lixo do Java eventualmente liberará a memória associada a esses nós e seus valores,
//    desde que não haja outras referências a eles em algum outro local do programa.
//
//    Isso significa que, do ponto de vista da fila, não há mais referências ativas a esses nós, então o GC fará o
//    trabalho de liberar a memória de forma automática e eficiente.

    @Override
    public Iterator<QueueNodeMessage> iterator() {
        return new Iterator<QueueNodeMessage>() {
            private QueueNodeMessage current = firstNode;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public QueueNodeMessage next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                QueueNodeMessage temp = current;
                current = (QueueNodeMessage) current.getNext();
                return temp;
            }
        };
    }

}
