package com.hr_nexus.Security;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBlockService {

    private final Set<String> blockListToken =
            ConcurrentHashMap.newKeySet();

    public void killingToken(String token) {
        blockListToken.add(token);
    }

    public boolean iskillingToken(String token) {
        return blockListToken.contains(token);
    }
}
