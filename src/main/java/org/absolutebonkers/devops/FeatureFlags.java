package org.absolutebonkers.devops;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class FeatureFlags {
    private final Map<String, Set<String>> enabledUsersByFlag;

    public FeatureFlags(Map<String, Set<String>> enabledUsersByFlag) {
        this.enabledUsersByFlag = Map.copyOf(enabledUsersByFlag);
    }

    public boolean enabledFor(String flag, String userId) {
        Objects.requireNonNull(flag, "flag");
        Objects.requireNonNull(userId, "userId");
        return enabledUsersByFlag.getOrDefault(flag, Set.of()).contains(userId);
    }
}
