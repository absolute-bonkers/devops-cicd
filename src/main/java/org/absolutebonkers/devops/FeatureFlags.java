package org.absolutebonkers.devops;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class FeatureFlags {
    private final Map<String, Set<String>> enabledUsersByFlag;

    public FeatureFlags(Map<String, Set<String>> enabledUsersByFlag) {
        Map<String, Set<String>> copiedUsersByFlag = new HashMap<>();
        enabledUsersByFlag.forEach((flag, users) -> copiedUsersByFlag.put(flag, Set.copyOf(users)));
        this.enabledUsersByFlag = Map.copyOf(copiedUsersByFlag);
    }

    public boolean enabledFor(String flag, String userId) {
        Objects.requireNonNull(flag, "flag");
        Objects.requireNonNull(userId, "userId");
        return enabledUsersByFlag.getOrDefault(flag, Set.of()).contains(userId);
    }
}
