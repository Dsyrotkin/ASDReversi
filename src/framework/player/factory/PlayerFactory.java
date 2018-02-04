package framework.player.factory;

public class PlayerFactory extends BasePlayerFactory {

    private static BasePlayerFactory playerFactory = new PlayerFactory();

    private PlayerFactory(){}

    public static BasePlayerFactory getFactory() {
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
