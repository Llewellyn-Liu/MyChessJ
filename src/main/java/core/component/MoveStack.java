package core.component;

import java.util.Stack;

/**
 * Designed for Move storage and recovery.
 */
public class MoveStack {
    Stack<Move> moveStack = new Stack<>();


    public void put(Move m){
        moveStack.push(m);
    }

    public Move get(){
        if(moveStack.isEmpty()) return null;

        return moveStack.pop();
    }

    public boolean isEmpty(){
        return moveStack.isEmpty();
    }
}
