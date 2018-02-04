package framework.board;

import framework.piece.Piece;

import java.io.*;
import java.util.Stack;

public class RecordManager {
    //private List<GameState> savedStates = new ArrayList<>();
    final private Stack<GameState> savedStates = new Stack<>();
    private static RecordManager recordManager;

    private RecordManager() { }

    public static RecordManager getInstance() {
        if(recordManager == null) recordManager = new RecordManager();
        return recordManager;
    }

    public void saveState(GameState state) {
        savedStates.push(state);
    }

    public void clearStoredStates() {
        savedStates.clear();
    }

    public GameState getPreviousState() {
        if (savedStates.size() > 1){
            GameState state = savedStates.pop();
            GameState previousState = savedStates.peek();
            saveState(state);
            return previousState;
        }
        return null;
    }

    private void print(Piece[][] pieces){
        System.out.println("saved state");
        for (Piece[] pieces1 : pieces){
            for (Piece piece : pieces1){
                System.out.print(piece.getPlayerId());
            }
            System.out.println();
        }
    }
    public void removeLastState() {

        if (savedStates.size() > 1){
            savedStates.pop();
        }
        /*if(savedStates.size() > 0 && savedStates.size() - 1 > 0) {
            savedStates.remove(savedStates.size() - 1);
        }*/
    }

    public boolean saveRecordToFile(GameState state) {
        try {
            FileOutputStream fos = new FileOutputStream(System.getProperty("java.io.tmpdir") + "save.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(state);
            oos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
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
