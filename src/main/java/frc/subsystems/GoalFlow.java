package frc.subsystems;

class Goal { // The integer numbers to represent different control panel functions
    static int TOCOLOR=1;
    static int INTROTATIONS=2;
}
// Sample "Goals" as in things for the robot to achieve
// The computer sets a goal to go to a color, or go a number of rotations, respectively.
public class GoalFlow { // Use the goals to do things with the robot here
    int currentGoal;
    Object[] args;
    public GoalFlow(){
        currentGoal=0;
    }
    void setGoal(int goal, Object... argv){
        this.args=argv;
        this.currentGoal=goal;
    }; // Setter for goals

    int getGoal(){
        return this.currentGoal;
    }; // Get the current goal

    void endGoal(){
        this.currentGoal=0;
    }; // Detach the goal

    void runGoal(Object... args){}; // Run code for goals
}