package br.com.mangarosa.messages.factory;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.Producer;

import java.util.UUID;

public class MessageFactory {

    public static Message build (Producer producer, String messageText) {
        Message message = new Message(producer, messageText);
        message.setId(UUID.randomUUID().toString());
        message.setConsumed(false);
        return  message;
    }
}
