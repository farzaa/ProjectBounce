package com.mygdx.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by flynn on 11/16/16.
 */

//GSM controls all aspects of the game in terms of what the user sees.
public class GameStateManager {

    //States are stored as a stack to ensure that the order remains intact.
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    //Need to ensure that there is always one state in the stack.
    public void pop() {
        states.pop();
    }

    //Move to a new state.
    public void set(State state) {
        states.pop();
        states.push(state);

    }
    //Ensures we update correct state.
    public void update(float dt) {
        states.peek().update(dt);
    }

    //Ensures we render correct state.
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }




}
