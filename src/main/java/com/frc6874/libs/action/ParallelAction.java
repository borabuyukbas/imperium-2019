package com.frc6874.libs.action;

import com.frc6874.libs.reporters.ConsoleReporter;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite action, running all sub-action at the same time All action are started then updated until all action
 * report being done.
 * 
 *            List of Action objects
 */
public class ParallelAction implements Action {

    private final ArrayList<Action> mActions;

    public ParallelAction(List<Action> actions) {
        mActions = new ArrayList<>(actions.size());
        for (Action action : actions) {
            mActions.add(action);
        }
    }

    @Override
    public boolean isFinished() {
        boolean all_finished = true;
        for (Action action : mActions) {
            if (!action.isFinished()) {
                all_finished = false;
            }
        }
        return all_finished;
    }

    @Override
    public void update() {
        for (Action action : mActions) {
            if (!action.isFinished()) {
                action.update();
                ConsoleReporter.report(action.getClass().getName());
            }
        }
    }

    @Override
    public void done() {
        for (Action action : mActions) {
            action.done();
        }
    }

    @Override
    public void start() {
        for (Action action : mActions) {
            action.start();
        }
    }
}