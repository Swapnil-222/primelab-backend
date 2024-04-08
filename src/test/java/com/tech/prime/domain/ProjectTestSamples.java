package com.tech.prime.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Project getProjectSample1() {
        return new Project().id(1L).projectName("projectName1").location("location1").status("status1");
    }

    public static Project getProjectSample2() {
        return new Project().id(2L).projectName("projectName2").location("location2").status("status2");
    }

    public static Project getProjectRandomSampleGenerator() {
        return new Project()
            .id(longCount.incrementAndGet())
            .projectName(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
