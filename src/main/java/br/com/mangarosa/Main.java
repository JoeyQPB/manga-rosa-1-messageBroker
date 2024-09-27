package br.com.mangarosa;

import br.com.mangarosa.messages.MessageBroker;
import br.com.mangarosa.messages.consumers.ConsumerBrokerImpl;
import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.MessageRepository;
import br.com.mangarosa.messages.interfaces.Producer;
import br.com.mangarosa.messages.interfaces.Topic;
import br.com.mangarosa.messages.producers.ProducerBrokerImpl;
import br.com.mangarosa.messages.repositories.InMemoryMessageRepositoryImpl;
import br.com.mangarosa.messages.topics.TopicBrokerImpl;

import java.util.Scanner;

// IN <\src\main\java> RUN = javac br\com\mangarosa\*.java
// IN <\src\main\java> RUN = java br.com.mangarosa.Main
public class Main {

    public static MessageRepository repository;
    public static MessageBroker messageBroker;

    public static Topic fastDeliveryItems;
    public static Topic longDistanceItems;

    public static Producer foodDeliveryProducer;
    public static Producer physicPersonDeliveryProducer;
    public static Producer pyMarketPlaceProducer;
    public static Producer fastDeliveryProducer;

    public static Consumer fastDeliveryItemsConsumer;
    public static Consumer longDistanceItemsConsumer;

    public static void main(String[] args) throws InterruptedException {
        setUp();
        interaction();
    }

    private static void setUp() {
        repository = new InMemoryMessageRepositoryImpl();
        messageBroker = new MessageBroker(repository);

        fastDeliveryItems = new TopicBrokerImpl("fast-delivery-items", repository);
        longDistanceItems = new TopicBrokerImpl("long-distance-items", repository);

        foodDeliveryProducer =
                new ProducerBrokerImpl("FoodDeliveryProducer", messageBroker, fastDeliveryItems.name());
        physicPersonDeliveryProducer =
                new ProducerBrokerImpl("PhysicPersonDeliveryProducer", messageBroker, fastDeliveryItems.name());

        pyMarketPlaceProducer =
                new ProducerBrokerImpl("PyMarketPlaceProducer", messageBroker, longDistanceItems.name());
        fastDeliveryProducer =
                new ProducerBrokerImpl("FastDeliveryProducer", messageBroker, longDistanceItems.name());

        fastDeliveryItemsConsumer =
                new ConsumerBrokerImpl("FastDeliveryItemsConsumer", fastDeliveryItems.name(), repository);

        longDistanceItemsConsumer =
                new ConsumerBrokerImpl("LongDistanceItemsConsumer", longDistanceItems.name(), repository);

        foodDeliveryProducer.addTopic(fastDeliveryItems);
        pyMarketPlaceProducer.addTopic(longDistanceItems);

        messageBroker.subscribe(fastDeliveryItems.name(), fastDeliveryItemsConsumer);
        messageBroker.subscribe(longDistanceItems.name(), longDistanceItemsConsumer);
    }

    private static void interaction() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Boolean alive = true;


        while (alive) {
            int topicNumber;
            int producerNumber;
            String messageText;
            String aliveModify;

            System.out.println("Bem vindo ao Message Broker!");
            System.out.println("Selecione um tópico para sua mensagem: \n" +
                                "\t 1 - fastDeliveryItems\n" +
                                "\t 2 - longDistanceItems");
            topicNumber = sc.nextInt();
            sc.nextLine();

            if (topicNumber == 1) {
                System.out.println("\nTopic: fast-delivery-items");
                System.out.println("Selecione um producer para sua mensagem: \n" +
                        "\t 1 - FoodDeliveryProducer\n" +
                        "\t 2 - PhysicPersonDeliveryProducer");
                producerNumber = sc.nextInt();
                sc.nextLine();

                if (producerNumber == 1) {
                    System.out.println("\nProducer: FoodDeliveryProducer");
                    System.out.println("Digite sua mensagem: ");
                    messageText = sc.nextLine();

                    foodDeliveryProducer.sendMessage(messageText);
                } else {
                    System.out.println("\nProducer: PhysicPersonDeliveryProducer");
                    System.out.println("Digite sua mensagem: ");
                    messageText = sc.nextLine();

                    physicPersonDeliveryProducer.sendMessage(messageText);
                }

            } else {
                System.out.println("Topic: long-distance-items");
                System.out.println("Selecione um producer para sua mensagem: \n" +
                        "\t 1 - PyMarketPlaceProducer\n" +
                        "\t 2 - FastDeliveryProducer");
                producerNumber = sc.nextInt();
                sc.nextLine();

                if (producerNumber == 1) {
                    System.out.println("\nProducer: PyMarketPlaceProducer");
                    System.out.println("Digite sua mensagem: ");
                    messageText = sc.nextLine();

                    pyMarketPlaceProducer.sendMessage(messageText);
                } else {
                    System.out.println("\nProducer: FastDeliveryProducer");
                    System.out.println("Digite sua mensagem: ");
                    messageText = sc.nextLine();

                    fastDeliveryProducer.sendMessage(messageText);
                }
            }
            Thread.sleep(5000);

            do {
                System.out.println("\nDeseja continuar no message broker (y or n)");
                aliveModify = sc.nextLine();
            } while (!aliveModify.equalsIgnoreCase("Y") && !aliveModify.equalsIgnoreCase("N"));

            alive = aliveModify.equalsIgnoreCase("Y") ? true : false;
        }

        sc.close();
    }
}