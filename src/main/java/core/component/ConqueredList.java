package core.component;

import java.util.ArrayList;
import java.util.Stack;

/**
 * When a chess is conquered, it will be taken to the ConqueredStack instance located in the ChessBoardController
 */
public class ConqueredList {

    ArrayList<Chess> list = new ArrayList<>();

    public ArrayList<Chess> getList() {
        return list;
    }


    public void push(Chess c){
        list.add(c);
    }

    public int getSize(){
        return list.size();
    }
}
