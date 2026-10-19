package org.absolutebonkers.devops;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolloutTest {
    @Test
    void canaryIncreasesTrafficAndEndsAtFullRollout() {
        assertEquals(java.util.List.of(0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100),
                new CanaryRollout().trafficPercentages(0, 10));
    }

    @Test
    void canaryRejectsInvalidPercentages() {
        assertThrows(IllegalArgumentException.class,
                () -> new CanaryRollout().trafficPercentages(101, 10));
        assertThrows(IllegalArgumentException.class,
                () -> new CanaryRollout().trafficPercentages(0, 0));
    }

    @Test
    void blueGreenSwitchCanBeReversedForRollback() {
        var router = new BlueGreenRouter();
        assertEquals(BlueGreenRouter.Environment.GREEN, router.switchTraffic());
        assertEquals(BlueGreenRouter.Environment.BLUE, router.switchTraffic());
    }

    @Test
    void featureFlagDefaultsOffAndCanTargetAnExplicitUser() {
        var flags = new FeatureFlags(java.util.Map.of("new-checkout", java.util.Set.of("user-7")));
        assertTrue(flags.enabledFor("new-checkout", "user-7"));
        assertFalse(flags.enabledFor("new-checkout", "user-8"));
        assertFalse(flags.enabledFor("unknown", "user-7"));
    }
}
