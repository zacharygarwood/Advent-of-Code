package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.sound.midi.Soundbank;

import java.util.HashMap;

class Trebuchet {
    private static Map<String, Character> spelledOutDigits = new HashMap<String, Character>() {{
        put("one", '1');
        put("two", '2');
        put("three", '3');
        put("four", '4');
        put("five", '5');
        put("six", '6');
        put("seven", '7');
        put("eight", '8');
        put("nine", '9');
    }};

    public static void main(String[] args) {
        String filename = "day1/input.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            int calibrationValueTotal = 0;
            while ((line = br.readLine()) != null) {
                char firstDigit = findLeftmostDigit(line);
                char secondDigit = findRightmostDigit(line);

                calibrationValueTotal += Integer.parseInt(new String(firstDigit + "" + secondDigit));
            }

            System.out.println(calibrationValueTotal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static char findLeftmostDigit(String line) {
        char firstDigit = ' ';
        int digitIndex = Integer.MAX_VALUE;

        // Get leftmost digit
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                firstDigit = line.charAt(i);
                digitIndex = i;
                break;
            }
        }

        // Check for spelled out number before leftmost digit
        for (String digit : spelledOutDigits.keySet()) {
            int index = line.indexOf(digit);
            if (index != -1 && index < digitIndex) {
                firstDigit = spelledOutDigits.get(digit);
                digitIndex = index;
            }
        }

        return firstDigit;
    }

    public static char findRightmostDigit(String line) {
        char secondDigit = ' ';
        int digitIndex = Integer.MIN_VALUE;

        // Get rightmost digit
        for (int i = line.length() - 1; i >= 0; i--) {
            if (Character.isDigit(line.charAt(i))) {
                secondDigit = line.charAt(i);
                digitIndex = i;
                break;
            }
        }

        // Check for spelled out number before leftmost digit
        for (String digit : spelledOutDigits.keySet()) {
            int index = line.lastIndexOf(digit);
            if (index != -1 && index > digitIndex) {
                secondDigit = spelledOutDigits.get(digit);
                digitIndex = index;
            }
        }

        return secondDigit;
    }
}