package model;

/**
 *
 * @author Niklas Sarup-Lytzen ID: 18036644
 *
 */
public abstract class Employee extends User {
    
    protected boolean fullTime;

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    @Override
    public String toString() {
        return "Employee{" + "fullTime=" + fullTime + '}';
    }
    
    
    
}
