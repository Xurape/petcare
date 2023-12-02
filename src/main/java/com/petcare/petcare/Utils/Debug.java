package com.petcare.petcare.Utils;

import java.util.concurrent.locks.ReentrantLock;

public class Debug {
    private static Debug debug = null;
    private boolean enabled = true;

    public Debug() {}

    public void debug(String text) {
        if(enabled)
            System.out.println(text);
    }

   public static Debug getDebug() {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        if (debug == null)
            debug = new Debug();
        lock.unlock();

        return debug;
    }
}
