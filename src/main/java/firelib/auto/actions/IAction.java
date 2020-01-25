package firelib.auto.actions;

public interface IAction {
    public void init();

    public void run();

    public void stop();

    public boolean isFinished();
}