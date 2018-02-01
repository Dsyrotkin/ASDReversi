package framework.player.factory;

public class PlayerFactory extends AbstractPlayerFactory{

    static int playerId = 0;

    private static AbstractPlayerFactory playerFactory = new PlayerFactory();

    private PlayerFactory(){}

    public static AbstractPlayerFactory getFactory() {
        return playerFactory;
    }

    @Override
    public Player createPlayer() {
        if (playerId == 0){
            playerId++;
            return new PlayerOne("Player one");
        }else {
            playerId--;
            return new PlayerTwo("Player two");
        }
    }
}
