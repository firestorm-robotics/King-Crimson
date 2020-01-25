package firelib.auto;

import firelib.auto.actions.actionrunners.ActionRunnerBase;

public class AutoModeExecutor {
    private ActionRunnerBase mRunner;
    private Thread mThread = null;

    public AutoModeExecutor(ActionRunnerBase runner) {
        mRunner = runner;
        mThread = new Thread(new Runnable(){
        
            @Override
            public void run() {
                if(mRunner != null) {
                    mRunner.run();
                }
            }
        });
    }

    public void start() {
        mThread.start();
    }

    public void stop() {
        mRunner.stop();
        mThread = null;
    }
}