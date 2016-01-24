package com.tele2.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dell on 24.01.2016.
 */
@Scope("application")
@Component
public class AdminUsersCachingService {
    public ConcurrentHashMap<String, Object> cache;

    public AdminUsersCachingService() {
        cache = new ConcurrentHashMap<>();
    }

    public void put(String sessionId, Object user) {
        cache.putIfAbsent(sessionId, user);
    }

    public Object get(String sessionId) {
        return cache.get(sessionId);
    }

    public boolean containsKey(String sessionId) {
        return cache.containsKey(sessionId);
    }
}
