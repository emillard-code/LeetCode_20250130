package com.project.attempt;

import java.util.LinkedList;

public class LeetCodeAttempt {

    public static void main(String[] args) {

        int[][] edges1 = new int[][]{{1,2},{1,4},{1,5},{2,6},{2,3},{4,6}};
        System.out.println(divideNodesIntoTheMaximumNumberOfGroups(6, edges1));

        int[][] edges2 = new int[][]{{1,2},{2,3},{3,1}};
        System.out.println(divideNodesIntoTheMaximumNumberOfGroups(3, edges2));

    }

    // This method returns the maximum number of groups that the nodes
    // can be divided into, following the challenge specifications.
    public static int divideNodesIntoTheMaximumNumberOfGroups(int n, int[][] edges) {

        // We will store the answer in int solution.
        int solution = -1;

        // We will loop through and test all node values (basically 1 to n) as possible starting
        // positions (i.e. the leftmost group). We will call a recursive function to test the
        // maximum number of groups that can be formed with that node placed in the leftmost group.
        // By doing this, we should be able to test all possible group combinations (if any exist).
        for (int i = 1; i <= n; i++) {

            // We create a LinkedList version of the 2D array edges.
            LinkedList<LinkedList<Integer>> edgesList = daCopy(edges);

            // We then create a 2D LinkedList of the groups that
            // can exist with int i as a node in the leftmost group.
            // We add the current int i to the leftmost group to start things off.
            LinkedList<LinkedList<Integer>> groups = new LinkedList<>();
            groups.add(new LinkedList<Integer>());
            groups.get(0).add(i);

            // We then call a recursive function that will calculate the maximum
            // number of groups that can be formed if we place the node with the
            // value of int i in the leftmost group. We will then update int solution
            // with this value if it is larger than what int solution currently is.
            int maximumGroups = branchingPaths(edgesList, groups);
            if (maximumGroups > solution) { solution = maximumGroups; }

        }

        // The value of int solution after all the loops is ultimately
        // the largest number of groups that can be formed from this array.
        return solution;

    }

    // This recursive function will take a 2D array of edges and 2D array of groups that has
    // at least one node added, and will return the maximum number of groups that can be
    // created with the existing nodes being in the positions they currently are.
    private static int branchingPaths(LinkedList<LinkedList<Integer>> edges,
                                      LinkedList<LinkedList<Integer>> groups) {

        // If the size of LinkedList edges is 0, return the number of groups as it is.
        if (edges.isEmpty()) { return groups.size(); }

        // This recursive function will gradually go through and remove the nodes in
        // LinkedList edges while making sure that those same nodes are placed in
        // appropriate positions within LinkedList groups that follow the relations
        // established by the LinkedList edges.
        while (!edges.isEmpty()) {

            // We iterate through all the nodes that have already been added to LinkedList groups.
            // We will then look for any element in LinkedList edges that contains a node that's
            // the same value as the one in LinkedList groups.get(i).get(j). We will then create
            // two modified versions of LinkedList groups to reflect the relation shown in that edge,
            // with one version having the node that's not LinkedList groups.get(i).get(j) placed in
            // the left group, with the other version being placed in the right group, as these are
            // the two possible options for where they can be placed, and we want to test all viable
            // possibilities. We only deviate from this if we're already at the leftmost group or
            // if the node we want to place down already exists in LinkedList groups.
            for (int i = 0; i < groups.size(); i++) {
                for (int j = 0; j < groups.get(i).size(); j++) {

                    // For simplicity's sake, we will refer to the current node from
                    // LinkedList group that we're iterating through as int currentNode.
                    int currentNode = groups.get(i).get(j);

                    // We will then look for any element in LinkedList edges that contains a node
                    // that's the same value as int currentNode. We use a helper method to do this.
                    int currentNodeIndex = daIndex(edges, currentNode);

                    // If a match is found, then we need to depict the relationship of the edge
                    // within LinkedList groups, and then delete it from LinkedList edges once
                    // we have done that. If no match is found, then int currentNodeIndex defaults
                    // to a value of -1, and we can skip this if-statement entirely.
                    if (currentNodeIndex != -1) {

                        // int newNode will refer to the value of the node in the edge that is -not-
                        // int currentNode. We need to obtain this value to depict its relation with
                        // int currentNode in LinkedList groups properly.
                        int newNode = -1;
                        if (edges.get(currentNodeIndex).get(0) != currentNode) { newNode = edges.get(currentNodeIndex).get(0); }
                        if (edges.get(currentNodeIndex).get(1) != currentNode) { newNode = edges.get(currentNodeIndex).get(1); }

                        // Once we have our needed values from LinkedList edges, we can remove the
                        // current edge that we're looking at from the list so that further recursive
                        // calls of this method will not look at it again and can finally stop when
                        // all edges have been looked at and removed from the LinkedList.
                        edges.remove(currentNodeIndex);

                        // We will first check to see if int newNode already exists in LinkedList groups
                        // or not. The helper method returns a -1 if it does not, otherwise it returns
                        // the index of the element that contains the node.
                        int newNodeIndex = daIndex(groups, newNode);

                        // If the node -does- already exist in LinkedList groups, then we must make sure
                        // that it is placed only 1 group away from the int currentNode node. If it
                        // already exists and is more than 1 group away, then we have run into a
                        // contradiction of the challenge specifications. So we abandon the recursion
                        // here and simply return -1 to signify that this 'path' is not a valid grouping.
                        if (newNodeIndex != -1 && Math.abs(newNodeIndex - i) != 1) { return -1; }

                        // If node already exists -and- is exactly 1 group away, we simply continue
                        // to the next iteration of the loop as no logic needs to be performed
                        // besides deleting the edge from LinkedList edges.
                        if (newNodeIndex != -1 && Math.abs(newNodeIndex - i) == 1) { continue; }

                        // Otherwise, we will perform recursion here. We will test placing the new
                        // node in both the left and right groups (except when it can't go further
                        // left). We will test the number of groups that can be achieved by attempting
                        // both options, and whichever option ends up yielding the higher value, will
                        // be what we will overwrite int maximumGroup with.
                        int maximumGroup = -1;

                        // If the new node doesn't already exist in the list, and we aren't already at
                        // the leftmost node, we place the new node in the group that's to the left of
                        // the group that int currentNode is in.
                        if (i >= 1 && newNodeIndex == -1) {

                            LinkedList<LinkedList<Integer>> newEdges = daCopy(edges);
                            LinkedList<LinkedList<Integer>> newGroups = daCopy(groups);
                            newGroups.get(i-1).add(newNode);

                            int group = branchingPaths(newEdges, newGroups);

                            if (group > maximumGroup) { maximumGroup = group; }

                        }

                        // If the new node doesn't already exist in the list, we place the new node in
                        // the group that's to the right of the group that int currentNode is in. If
                        // such a group doesn't already exist (in other words, int currentNode is
                        // situated in the current rightmost group) then we create a new group to
                        // account for this fact.
                        if (i < groups.size() && newNodeIndex == -1) {

                            LinkedList<LinkedList<Integer>> newEdges = daCopy(edges);
                            LinkedList<LinkedList<Integer>> newGroups = daCopy(groups);
                            if (i + 1 == groups.size()) { newGroups.add(new LinkedList<>()); }
                            newGroups.get(i+1).add(newNode);

                            int group = branchingPaths(newEdges, newGroups);

                            if (group > maximumGroup) { maximumGroup = group; }

                        }

                        // int maximumGroup should hold the highest number of groups that can be
                        // created between the options of placing the new node in the left group
                        // and placing it in the right group.
                        return maximumGroup;

                    }

                }
            }

        }

        // If the method reaches this point, something is probably wrong,
        // and we will return -1 as a default negative solution.
        return -1;

    }

    // This is a helper method to help copy and create new 2D LinkedLists. Mostly used to help avoid
    // issues with recursion that could occur if we don't create new copies of objects between
    // recursions. The 'da' in daCopy stands for double array.
    private static LinkedList<LinkedList<Integer>> daCopy(LinkedList<LinkedList<Integer>> array) {

        LinkedList<LinkedList<Integer>> newDoubleArray = new LinkedList<>();

        for (int i = 0; i < array.size(); i++) {

            newDoubleArray.add(new LinkedList<>());

            for (int j = 0; j < array.get(i).size(); j++) {

                newDoubleArray.get(i).add(array.get(i).get(j));

            }

        }

        return newDoubleArray;

    }

    // This is a helper method to help copy and create new 2D LinkedLists. Mostly used to help avoid
    // issues with recursion that could occur if we don't create new copies of objects between
    // recursions. The 'da' in daCopy stands for double array.
    private static LinkedList<LinkedList<Integer>> daCopy(int[][] array) {

        LinkedList<LinkedList<Integer>> newDoubleArray = new LinkedList<>();

        for (int i = 0; i < array.length; i++) {

            newDoubleArray.add(new LinkedList<>());

            for (int j = 0; j < array[i].length; j++) {

                newDoubleArray.get(i).add(array[i][j]);

            }

        }

        return newDoubleArray;

    }

    // This is a helper method to help locate the index of a certain value in a 2D LinkedList object.
    // It only returns the first-dimension index and also returns -1 if the value cannot be found.
    private static int daIndex(LinkedList<LinkedList<Integer>> array, int value) {

        for (int i = 0; i < array.size(); i++) {

            for (int j = 0; j < array.get(i).size(); j++) {

                if (array.get(i).get(j) == value) { return i; }

            }

        }

        return -1;

    }

}
