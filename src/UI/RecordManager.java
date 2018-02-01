package UI;

import java.io.*;
import java.util.List;

public class RecordManager {
    private List<GameState> savedStates;

    public void saveState(GameState state) {
        savedStates.add(state);
    }

    public GameState getLastState() {
        if(savedStates.size() > 0) {
            return savedStates.get(savedStates.size() - 1);
        }
        return null;
    }

    public void removeLastState() {
        if(savedStates.size() > 0) {
            savedStates.remove(savedStates.size() - 1);
        }
    }

    public void saveRecordToFile(GameState state) {
        try {
            FileOutputStream fos = new FileOutputStream(System.getProperty("java.io.tmpdir") + "save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(state);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameState getSavedRecord() {
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("java.io.tmpdir") + "save.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameState state = (GameState) ois.readObject();
            ois.close();
            return state;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
