package framework.playerFactory;

public abstract class Player {
    protected int id;
    protected String name;

    public Player(String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}