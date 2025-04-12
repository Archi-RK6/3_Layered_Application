package API;
import io.nats.client.*;
import Business.MassageProcessor;

public class Subscriber {
    public static void sub(MassageProcessor processor) {
        try {
            Connection natsConnection = Nats.connect("nats://localhost:4222");
            System.out.println("Subscriber is running.");

            Dispatcher d = natsConnection.createDispatcher((msg) -> {
                String messageContent = new String(msg.getData());
                System.out.println("Received: " + messageContent);
                processor.processMessage(messageContent);
            });

            d.subscribe("updates");

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}