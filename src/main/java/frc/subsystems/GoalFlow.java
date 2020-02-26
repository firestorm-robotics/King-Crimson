package frc.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

enum Goal { // The integer numbers to represent different control panel functions
    NONE,
    TOCOLOR,
    INTROTATIONS;
}
// Sample "Goals" as in things for the robot to achieve
// The computer sets a goal to go to a color, or go a number of rotations, respectively.
public class GoalFlow { // Use the goals to do things with the robot here
    protected Goal currentGoal;
    private Object[] args;
    protected int tick=0;
    public GoalFlow(){
        currentGoal=Goal.NONE;
    }
    public void setGoal(Goal goal, Object... argv){
        this.args=argv;
        this.currentGoal=goal;
    } // Setter for goals

    public Goal getGoal() {
        return this.currentGoal;
    } // Get the current goal

    public void endGoal(){
        SmartDashboard.putString("GOALFLOW_EVT", "Terminated the goal flow. This is only a sample: If you are seeing this, someone did something wrong.");
        this.currentGoal=Goal.NONE;
    } // Detach the goal

    public void runGoal(Object... args){
        this.tick++;
    } // Run code for goals
}