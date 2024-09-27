package br.com.mangarosa.messages.repositories;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.MessageRepository;

import java.util.*;

public class InMemoryMessageRepositoryImpl implements MessageRepository {

    private final Map<String, List<Message>> topics;

    public InMemoryMessageRepositoryImpl() {
        this.topics = new HashMap<>();
    }

    @Override
    public void append(String topic, Message message) {
        if (this.topics.get(topic) == null) {
            this.topics.put(topic, new ArrayList<>());
        }
        topics.get(topic).add(message);
    }

    @Override
    public void consumeMessage(String topic, UUID messageId) {
        List<Message> messages = topics.get(topic);
        if (messages == null) throw new IllegalArgumentException(topic + "not found");

        for (Message message : messages) {
            String uuid = message.getId();
            if (Objects.equals(uuid, messageId.toString())) {
                message.setConsumed(true);
            }
        }
    }

    @Override
    public List<Message> getAllNotConsumedMessagesByTopic(String topic) {
        List<Message> messages = topics.get(topic);
        if (messages == null) throw new IllegalArgumentException(topic + "not found");

        for (Message message : messages) {
            if (message.isConsumed()) {
                messages.remove(message);
            }
        }

        return messages;
    }

    @Override
    public List<Message> getAllConsumedMessagesByTopic(String topic) {
        List<Message> messages = topics.get(topic);
        if (messages == null) throw new IllegalArgumentException(topic + "not found");

        return messages.stream().filter(Message::isConsumed).toList();
    }
}
