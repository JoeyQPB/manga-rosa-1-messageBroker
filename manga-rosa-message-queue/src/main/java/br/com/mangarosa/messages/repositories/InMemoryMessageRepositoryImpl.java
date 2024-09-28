package br.com.mangarosa.messages.repositories;

import br.com.mangarosa.datastructures.abstractClasses.extd.QueueNodeMessage;
import br.com.mangarosa.datastructures.interfaces.impl.LinkedQueue;
import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.MessageRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMessageRepositoryImpl implements MessageRepository {

    private final Map<String, LinkedQueue> topics;

    public InMemoryMessageRepositoryImpl() {
        this.topics = new HashMap<>();
    }

    @Override
    public void append(String topic, Message message) {
        if (this.topics.get(topic) == null) {
            this.topics.put(topic, new LinkedQueue());
        }
        topics.get(topic).enqueue(new QueueNodeMessage(message));
    }

    @Override
    public void consumeMessage(String topic, UUID messageId) {
        LinkedQueue messagesQueue = topics.get(topic);
        if (messagesQueue == null) throw new IllegalArgumentException(topic + " not found");

        for (QueueNodeMessage node : messagesQueue) {
            String uuid = node.getValue().getId();
            if (Objects.equals(uuid, messageId.toString())) {
                node.getValue().setConsumed(true);
            }
        }
    }

    @Override
    public List<Message> getAllNotConsumedMessagesByTopic(String topic) {
        LinkedQueue messagesQueue = topics.get(topic);
        if (messagesQueue == null) throw new IllegalArgumentException(topic + " not found");
        List<Message> messageList = new ArrayList<>();

        for (QueueNodeMessage node : messagesQueue) {
            if (!node.getValue().isConsumed()) {
                messageList.add(node.getValue());
            }
        }
        return messageList;
    }

    @Override
    public List<Message> getAllConsumedMessagesByTopic(String topic) {
        Message[] messagesQueue = topics.get(topic).toArrayValues();
        if (messagesQueue == null) throw new IllegalArgumentException(topic + " not found");
        return Arrays.stream(messagesQueue).filter(Message::isConsumed).collect(Collectors.toList());
    }
}
