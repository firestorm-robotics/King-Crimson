package frc.subsystems;

enum Goal { // The integer numbers to represent different control panel functions
    NONE,
    TOCOLOR,
    INTROTATIONS;
}
// Sample "Goals" as in things for the robot to achieve
// The computer sets a goal to go to a color, or go a number of rotations, respectively.
public class GoalFlow { // Use the goals to do things with the robot here
    Goal currentGoal;
    Object[] args;
    public GoalFlow(){
        currentGoal=Goal.NONE;
    }
    void setGoal(Goal goal, Object... argv){
        this.args=argv;
        this.currentGoal=goal;
    }; // Setter for goals

    Goal getGoal() {
        return this.currentGoal;
    }; // Get the current goal

    void endGoal(){
        this.currentGoal=Goal.NONE;
    }; // Detach the goal

    void runGoal(Object... args){}; // Run code for goals
}