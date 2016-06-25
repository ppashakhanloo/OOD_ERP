package ui.project;

import ui.MainDialog;

import java.util.ArrayList;

public class EditProject extends MainDialog {

    private ArrayList<ProjectObserver> observers;

    public EditProject() {
        observers = new ArrayList<>();
    }

    private void notifyAllObservers() {
        observers.forEach(ProjectObserver::update);
    }

    public void attach(ProjectObserver observer) {
        observers.add(observer);
    }
    
}
