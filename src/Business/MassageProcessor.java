package Business;
import Data.MassageKeeper;

public class MassageProcessor {
    private MassageKeeper mk;


    public MassageProcessor(MassageKeeper mk){
        this.mk = mk;
    }

    public void processMessage(String content) {
        if(content == null){
            System.out.println("Empty message.");
            return;
        }
        mk.savingMessage(content);
    }
}
