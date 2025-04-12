import API.Subscriber;
import Business.MassageProcessor;
import Data.MassageKeeper;

public class Start {
    public static void main(String[] args) throws Exception {
        MassageKeeper repo = new MassageKeeper();
        MassageProcessor service = new MassageProcessor(repo);
        Subscriber.sub(service);
    }
}
