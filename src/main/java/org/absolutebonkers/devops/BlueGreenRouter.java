package org.absolutebonkers.devops;

public final class BlueGreenRouter {
    public enum Environment { BLUE, GREEN }

    private Environment active = Environment.BLUE;

    public synchronized Environment activeEnvironment() {
        return active;
    }

    public synchronized Environment switchTraffic() {
        active = active == Environment.BLUE ? Environment.GREEN : Environment.BLUE;
        return active;
    }
}
