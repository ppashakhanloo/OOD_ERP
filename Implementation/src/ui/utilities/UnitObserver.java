package ui.utilities;

import ui.AddNewUnit;

public abstract class UnitObserver {
    protected AddNewUnit addNewUnit;

    abstract public void update();
}
