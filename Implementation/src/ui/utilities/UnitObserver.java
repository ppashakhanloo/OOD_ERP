package ui.utilities;

import ui.AddNewUnit;

/**
 * Created by ppash on 6/25/2016.
 */
public abstract class UnitObserver {
    protected AddNewUnit addNewUnit;
    abstract public void update();
}
