package com.project.attempt;

import java.util.LinkedList;

public class LeetCodeAttempt {

    public static void main(String[] args) {

    }

    public static int divideNodesIntoTheMaximumNumberOfGroups(int n, int[][] edges) {

        for (int i = 1; i <= n; i++) {

            LinkedList<LinkedList<Integer>> edgesList = new LinkedList<>();
            LinkedList<Integer> nodesList = new LinkedList<>();

            for (int j = 0; j < edges.length; j++) {

                edgesList.add(new LinkedList<Integer>());
                edgesList.get(j).add(edges[j][0]);
                edgesList.get(j).add(edges[j][1]);

            }

            for (int j = 1; j <= n; j++) {

                nodesList.add(j);

            }

            LinkedList<LinkedList<Integer>> groups = new LinkedList<>();
            groups.add(new LinkedList<Integer>());
            groups.add(new LinkedList<Integer>());
            groups.get(0).add(i);
            nodesList.removeFirstOccurrence(i);

            for (int j = 1; j < edgesList.size(); j++) {

                if (edgesList.get(j).contains(i)) {

                    if (edgesList.get(j).get(0) != i) { groups.get(1).add(edgesList.get(j).get(0)); }
                    if (edgesList.get(j).get(1) != i) { groups.get(1).add(edgesList.get(j).get(1)); }
                    edgesList.get(j).remove(j);
                    j--;

                }

            }



        }

        return -1;

    }

    private static int branchingPaths(LinkedList<Integer> nodesList, LinkedList<LinkedList<Integer>> edgesList,
                                      LinkedList<LinkedList<Integer>> groups) {

        while (!edgesList.isEmpty()) {

            for (int i = 0; i < groups.size(); i++) {
                for (int j = 0; j < groups.get(i).size(); j++) {

                    int currentNode = groups.get(i).get(j);

                    if (daContains(edgesList, currentNode)){

                        for (int k = 0; k < edgesList.size(); k++) {
                            if (edgesList.get(k).contains(currentNode)) {

                                int newNode = -1;
                                if (edgesList.get(k).get(0) != currentNode) { newNode = edgesList.get(k).get(0); }
                                if (edgesList.get(k).get(1) != currentNode) { newNode = edgesList.get(k).get(1); }

                                edgesList.remove(k);
                                k--;

                                int newNodeIndex = daIndex(groups, newNode);
                                if (newNodeIndex != -1 && Math.abs(newNodeIndex - i) != 1) {
                                    return -1;
                                }

                                if (i > 1 && newNodeIndex == -1) {
                                    groups.get(i-1).add(newNode);
                                    // Insert recursive here
                                }

                                if (i < groups.size() - 1 && newNodeIndex == -1) {
                                    groups.get(i+1).add(newNode);
                                    // Insert recursive here
                                } else if (i + 1 == groups.size() && newNodeIndex == -1) {
                                    groups.add(new LinkedList<Integer>());
                                    groups.get(i+1).add(newNode);
                                    // Insert recursive here
                                }

                            }
                        }

                    }

                }
            }

        }


        return -1;

    }

    private static boolean daContains(LinkedList<LinkedList<Integer>> array, int value) {

        for (LinkedList<Integer> integers : array) {

            for (Integer integer : integers) {

                if (integer == value) { return true; }

            }

        }

        return false;

    }

    private static int daIndex(LinkedList<LinkedList<Integer>> array, int value) {

        for (int i = 0; i < array.size(); i++) {

            for (int j = 0; j < array.get(i).size(); j++) {

                if (array.get(i).get(j) == value) { return i; }

            }

        }

        return -1;

    }

}
