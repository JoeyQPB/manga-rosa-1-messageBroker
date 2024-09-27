package br.com.mangarosa.messages.producers;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.MessageBroker;
import br.com.mangarosa.messages.factory.MessageFactory;
import br.com.mangarosa.messages.interfaces.Producer;
import br.com.mangarosa.messages.interfaces.Topic;

import java.util.Objects;

public class ProducerBrokerImpl implements Producer {

    private final String name;
    private final MessageBroker messageBroker;
    private final String topicName;

    public ProducerBrokerImpl(String name, MessageBroker messageBroker, String topicName) {
        this.name = Objects.requireNonNull(name, "Topic name can`t be null");
        this.messageBroker = Objects.requireNonNull(messageBroker, "The producer must have a message broker");
        this.topicName = Objects.requireNonNull(topicName, "topicName name can`t be null");
    }

    @Override
    public void addTopic(Topic topic) {
        this.messageBroker.createTopic(topic);
    }

    @Override
    public void removeTopic(Topic topic) {
        this.messageBroker.removeTopic(topic.name());
    }

    @Override
    public void sendMessage(String message) {
        this.messageBroker.getTopicByName(this.topicName).addMessage(MessageFactory.build(this, message));
    }

    @Override
    public String name() {
        return this.name;
    }
}
