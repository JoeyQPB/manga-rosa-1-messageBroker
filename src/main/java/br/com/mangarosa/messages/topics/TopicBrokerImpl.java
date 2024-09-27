package br.com.mangarosa.messages.topics;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.MessageRepository;
import br.com.mangarosa.messages.interfaces.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TopicBrokerImpl implements Topic {

    private final String name;
    private final List<Consumer> consumers;
    private final MessageRepository messageRepository;

    public TopicBrokerImpl (String name, MessageRepository messageRepository) {
        this.name = Objects.requireNonNull(name, "Topic name can`t be null");
        this.messageRepository = Objects.requireNonNull(messageRepository, "The topic must have a repository");
        this.consumers = new ArrayList<>();
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void addMessage(Message message) {
        if (message.getMessage().isBlank() || message.getMessage().isEmpty() ||
                message.getMessage() == null) {
            throw new IllegalArgumentException("The message can`t be null");
        }

        this.messageRepository.append(this.name, message);
        this.notifyConsumers(message);
    }

    @Override
    public void subscribe(Consumer consumer) {
        if (consumers.contains(consumer)) {
            throw new IllegalArgumentException("the consumer: " + consumer.name() + " is already in the topic");
        }
        consumers.add(consumer);
    }

    @Override
    public void unsubscribe(Consumer consumer) {
        if (consumers.contains(consumer)) {
            consumers.remove(consumer);
        }
        throw new IllegalArgumentException("the consumer: " + consumer.name() + " ins`t in the topic");
    }

    @Override
    public List<Consumer> consumers() {
        return this.consumers;
    }

    @Override
    public MessageRepository getRepository() {
        return this.messageRepository;
    }
}
