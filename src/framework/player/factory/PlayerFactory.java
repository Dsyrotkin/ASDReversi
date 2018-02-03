package framework.player.factory;

public class PlayerFactory extends AbstractPlayerFactory{

    private static AbstractPlayerFactory playerFactory = new PlayerFactory();

    private PlayerFactory(){}

    public static AbstractPlayerFactory getFactory() {
        return playerFactory;
    }

    @Override
    public Player createPlayer(PlayerType playerType) {
        if (playerType == PlayerType.FIRST){
            return new PlayerOne("Player one");
        }else if (playerType == PlayerType.SECOND){
            return new PlayerTwo("Player two");
        } else {
            return new PlayerZero("Player zero");
        }
    }
}
