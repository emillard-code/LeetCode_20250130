package com.project;

import com.project.attempt.LeetCodeAttempt;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeetCodeAttemptTest {

    @Test
    public void divideNodesIntoTheMaximumNumberOfGroupsTest() {

        int[][] edges1 = new int[][]{{1,2},{1,4},{1,5},{2,6},{2,3},{4,6}};
        assertEquals(4, LeetCodeAttempt.divideNodesIntoTheMaximumNumberOfGroups(6, edges1));

        int[][] edges2 = new int[][]{{1,2},{2,3},{3,1}};
        assertEquals(-1, LeetCodeAttempt.divideNodesIntoTheMaximumNumberOfGroups(3, edges2));

    }

}
