package org.absolutebonkers.devops;

import java.util.ArrayList;
import java.util.List;

public final class CanaryRollout implements RolloutStrategy {
    @Override
    public List<Integer> trafficPercentages(int currentPercent, int stepPercent) {
        validate(currentPercent, stepPercent);
        List<Integer> steps = new ArrayList<>();
        for (int traffic = currentPercent; traffic < 100; traffic = Math.min(100, traffic + stepPercent)) {
            steps.add(traffic);
        }
        steps.add(100);
        return List.copyOf(steps);
    }

    private static void validate(int currentPercent, int stepPercent) {
        if (currentPercent < 0 || currentPercent > 100 || stepPercent <= 0) {
            throw new IllegalArgumentException("currentPercent must be 0..100 and stepPercent must be positive");
        }
    }
}
