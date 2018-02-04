package framework.player.factory;

public class PlayerOne extends Player {

    public PlayerOne(String name) {
        super(name);
        id = 1;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }

        if (!PlayerOne.class.isAssignableFrom(obj.getClass())){

        }
        return super.equals(obj);
    }
}
