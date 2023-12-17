package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

class GearRatios {
    private static int INPUT_SIZE = 140;
    public static void main(String[] args) {
        String filename = "day3/input.txt";

        int partNumberTotal = 0;
        int gearRatioTotal = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            // Store input string into easier to work with 2d array
            char[][] grid = new char[INPUT_SIZE][INPUT_SIZE]; 
            int idx = 0;
            while ((line = br.readLine()) != null) {
                grid[idx] = line.toCharArray();
                idx++;
            }

            Map<String, List<Integer>> symbolIdxToParts = new HashMap<String, List<Integer>>();

            for (int row = 0; row < INPUT_SIZE; row++) {
                String num = "";
                for (int col = 0; col < INPUT_SIZE; col++) {
                    if (Character.isDigit(grid[row][col])) {
                        num += grid[row][col];

                        // Check if it is the end of the number
                        if (col + 1 >= INPUT_SIZE || !Character.isDigit(grid[row][col + 1])) {
                            List<String> numberSymbols = getNumberSymbols(grid, row, col - num.length() + 1, col);

                            // Part 1 (check if num is a part number)
                            if (!numberSymbols.isEmpty()) {
                                partNumberTotal += Integer.valueOf(num);
                            }

                            // Part 2 (create a mapping between each symbol index and all the numbers it touches)
                            for (String symbolIdx : numberSymbols) {
                                symbolIdxToParts.computeIfAbsent(symbolIdx, k -> new ArrayList<>()).add(Integer.valueOf(num));
                            }
                            
                            num = "";
                        }
                    }
                }
            }  

            // Part 2 continued (go through the map, if a symbol is * and only has two numbers touching then multiply them and add to total)
            for (Map.Entry<String, List<Integer>> entry : symbolIdxToParts.entrySet()) {
                int[] symbolIdx = idxStringToRowCol(entry.getKey());
                List<Integer> numbers = entry.getValue();
                
                if (grid[symbolIdx[0]][symbolIdx[1]] == '*' && numbers.size() == 2) {
                    gearRatioTotal += numbers.get(0) * numbers.get(1);
                }
            }

            System.out.println(partNumberTotal);
            System.out.println(gearRatioTotal);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Return the indexes of the symbols that the number touches as strings like "1,3"
    public static List<String> getNumberSymbols(char[][] grid, int row, int startIdx, int endIdx) {
        List<String> result = new ArrayList<>();

        // Diagonals
        if (row > 0 && startIdx > 0 && isSymbol(grid[row - 1][startIdx - 1]))  result.add(toIdxString(row-1, startIdx-1));
        if (row > 0 && endIdx < INPUT_SIZE - 1 && isSymbol(grid[row - 1][endIdx + 1])) result.add(toIdxString(row-1, endIdx+1));
        if (row < INPUT_SIZE - 1 && startIdx > 0 && isSymbol(grid[row + 1][startIdx - 1])) result.add(toIdxString(row+1, startIdx-1));
        if (row < INPUT_SIZE - 1 && endIdx < INPUT_SIZE - 1 && isSymbol(grid[row + 1][endIdx + 1])) result.add(toIdxString(row+1, endIdx+1));

        // Left Right
        if (startIdx > 0 && isSymbol(grid[row][startIdx - 1])) result.add(toIdxString(row, startIdx-1));
        if (endIdx < INPUT_SIZE - 1 && isSymbol(grid[row][endIdx + 1])) result.add(toIdxString(row, endIdx+1));

        // Top
        if (row > 0) {
            for (int i = startIdx; i <= endIdx; i++) {
                if (isSymbol(grid[row - 1][i])) result.add(toIdxString(row-1, i));
            }
        }

        // Bottom
        if (row < INPUT_SIZE - 1) {
            for (int i = startIdx; i <= endIdx; i++) {
                if (isSymbol(grid[row + 1][i])) result.add(toIdxString(row+1, i));
            }
        }

        return result;
    }

    private static boolean isSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    // Converts a row, col to a string like "row,col"
    private static String toIdxString(int row, int col) {
        return String.valueOf(row) + "," + String.valueOf(col);
    }

    // Converts a string like "row, col" to an array like [row, col]
    private static int[] idxStringToRowCol(String str) {
        String[] parts = str.split(",", 0);
        int[] result = new int[]{Integer.valueOf(parts[0]), Integer.valueOf(parts[1])};

        return result;
    }
}