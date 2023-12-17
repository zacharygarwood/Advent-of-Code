package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class CubeConundrum {
    public static void main(String[] args) {
        String filename = "day2/input.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            int gameIdTotal = 0;
            int powerSetSum = 0;
            while ((line = br.readLine()) != null) {
                String[] content = line.split(":", 2);
                int gameId = Integer.valueOf(content[0].split(" ", 2)[1]);
                String[] games = content[1].split(";", 0);

                // Part 1
                boolean invalidSeries = false;
                for (String game : games) {
                    if (!isValidGame(game)) {
                        invalidSeries = true;
                    }
                }

                if (!invalidSeries) {
                    gameIdTotal += gameId;
                }

                // Part 2
                powerSetSum += gamesPowerSet(games);
            }

            System.out.println(gameIdTotal);
            System.out.println(powerSetSum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidGame(String game) {
        int MAX_RED = 12;
        int MAX_GREEN = 13;
        int MAX_BLUE = 14;

        Map<String, Integer> cubeCounts = new HashMap<String, Integer>() {{
            put("red", 0);
            put("green", 0);
            put("blue", 0);
        }};

        String[] cubeStrings = game.split(",", 0);
        for (String str : cubeStrings) {
            int amount = Integer.valueOf(str.split(" ", 0)[1]);
            String color = str.split(" ", 0)[2];
            cubeCounts.put(color, amount);
        }

        return cubeCounts.get("red") <= MAX_RED && cubeCounts.get("green") <= MAX_GREEN && cubeCounts.get("blue") <= MAX_BLUE;
    }

    public static int gamesPowerSet(String[] games) {
        Map<String, Integer> cubeCounts = new HashMap<String, Integer>() {{
            put("red", Integer.MIN_VALUE);
            put("green", Integer.MIN_VALUE);
            put("blue", Integer.MIN_VALUE);
        }};

        for (String game : games) {
            String[] cubeStrings = game.split(",", 0);
            for (String str : cubeStrings) {
                int amount = Integer.valueOf(str.split(" ", 0)[1]);
                String color = str.split(" ", 0)[2];

                if (amount > cubeCounts.get(color)) {
                    cubeCounts.put(color, amount);
                }
            }
        }

        return cubeCounts.get("red") * cubeCounts.get("green") * cubeCounts.get("blue");
    }
}