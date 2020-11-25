import gateways.FirebaseGateway;

public class Main {

    public static void main(String[] args) {

        FirebaseGateway fbg = new FirebaseGateway();
        fbg.start();

        ConferenceSystem system = new ConferenceSystem();

        system.run();
    }
}
