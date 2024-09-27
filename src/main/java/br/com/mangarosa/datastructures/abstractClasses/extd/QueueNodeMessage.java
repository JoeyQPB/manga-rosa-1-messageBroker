package br.com.mangarosa.datastructures.abstractClasses.extd;

import br.com.mangarosa.datastructures.abstractClasses.QueueNode;
import br.com.mangarosa.messages.Message;

import java.time.LocalDateTime;

public class QueueNodeMessage extends QueueNode<Message> implements Comparable<QueueNodeMessage> {

    public QueueNodeMessage(Message value) {
        this.value = value;
    }

    public QueueNodeMessage(Message value, QueueNode<Message> next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public int compareTo(QueueNodeMessage node) {
        LocalDateTime actualNode = this.value.getCreatedAt();
        LocalDateTime nodeCreatedAt = node.getValue().getCreatedAt();

        if (actualNode.isBefore(nodeCreatedAt)) return -1; // node atual é menor que o passado no parametro
        else if (actualNode.isEqual(nodeCreatedAt)) return 0; // node são iguais
        return 1; // node atual é maior que o passado no parametro
    }
}
