import controllers.ClanController;
import controllers.LoggingController;
import controllers.PlayerAddGoldController;
import controllers.PlayerController;

public class Main {

    public static void main(String... args) {

        ClanController.getInstance().initEndpoints();
        PlayerController.getInstance().initEndpoints();
        PlayerAddGoldController.getInstance().initEndpoints();
        LoggingController.getInstance().initEndpoint();
    }

}
