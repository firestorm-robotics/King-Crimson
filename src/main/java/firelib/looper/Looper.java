package firelib.looper;

import java.util.List;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

/**
 * totally stole this from 254 but poorly
 */
public class Looper implements ILooper {
    public static final double kDeltaTime = 0.005;
    private List<Loop> loops_;
    private Object runningLock_ = new Object();
    private boolean running_ = false;
    private Notifier notifier_;
    private double dt_ = 0;
    private double timestamp_ = 0;
    private Runnable runnable_ = new Runnable() {

        @Override
        public void run() {
            synchronized (runningLock_) {
                if (running_) {
                    double now = Timer.getFPGATimestamp();

                    for (Loop loop : loops_) {
                        loop.onLoop(now);
                    }

                    dt_ = now - timestamp_;
                    timestamp_ = now - timestamp_;

                }
            }

        }
    };

    public Looper() {
        notifier_ = new Notifier(runnable_);
        loops_ = new ArrayList<>();
    }

    @Override
    public synchronized void register(Loop loop) {
        synchronized (runningLock_) {
            loops_.add(loop);
        }
    }

    public synchronized void start() {
        if (!running_) {
            System.out.println("Starting loops");
            synchronized (runningLock_) {
                timestamp_ = Timer.getFPGATimestamp();
                for (Loop loop : loops_) {
                    loop.onStart(timestamp_);
                }
                running_ = true;
            }
        }
        notifier_.startPeriodic(kDeltaTime);
    }

    public synchronized void stop() {
        if (running_) {
            System.out.println("stopping loops");
            notifier_.stop();
            synchronized (runningLock_) {
                running_ = false;
                timestamp_ = Timer.getFPGATimestamp();
                for (Loop loop : loops_) {
                    System.out.println("stopping " + loop);
                    loop.onStop(timestamp_);
                }
            }
        }
    }
}