package org.absolutebonkers.devops;

import java.util.List;

public interface RolloutStrategy {
    List<Integer> trafficPercentages(int currentPercent, int stepPercent);
}
