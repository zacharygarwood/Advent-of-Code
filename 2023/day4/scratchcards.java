package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

class Scratchcards {
    private static int CARD_COUNT = 192;
    public static void main(String[] args) {
        String filename = "day4/input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            int pointTotal = 0;
            int[] winnersPerCard = new int[CARD_COUNT];
            int cardNum = 0;

            while ((line = br.readLine()) != null) {
                String cards = line.split(":", 0)[1];
                List<Integer> winningCards = parseWinningCards(cards);
                List<Integer> playerCards = parsePlayerCards(cards);

                // Part 1
                pointTotal += getPoints(winningCards, playerCards);

                // Part 2
                winnersPerCard[cardNum] = getWinners(winningCards, playerCards);
                cardNum++;
            }

            System.out.println(pointTotal);
            System.out.println(getTotalSratchcards(winnersPerCard));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getTotalSratchcards(int[] winnersPerCard) {
        int[] scratchcards = new int[CARD_COUNT];
        Arrays.fill(scratchcards, 1);

        for (int cardNum = 0; cardNum < winnersPerCard.length; cardNum++) {
            int numCopies = winnersPerCard[cardNum];
            for (int i = 1; i <= numCopies; i++) {
                scratchcards[cardNum + i] += scratchcards[cardNum];
            }
        }

        return Arrays.stream(scratchcards).sum();
    }

    public static int getWinners(List<Integer> winningCards, List<Integer> playerCards) {
        int winningNumbers = 0;

        for (int winningCard : winningCards) {
            if (playerCards.contains(winningCard)) {
                winningNumbers++;
            }
        }

        return winningNumbers;
    }

    public static int getPoints(List<Integer> winningCards, List<Integer> playerCards) {
        int winningNumbers = 0;

        for (int winningCard : winningCards) {
            if (playerCards.contains(winningCard)) {
                winningNumbers++;
            }
        }

        return (winningNumbers > 0) ? (int) Math.pow(2, winningNumbers - 1) : 0;
    }

    private static List<Integer> parseWinningCards(String cards) {
        String[] winningCards = cards.split("\\|")[0].trim().split("\\s+");

        List<Integer> result = new ArrayList<Integer>();
        for (String card : winningCards) {
            result.add(Integer.valueOf(card));
        }

        return result;
    }

    private static List<Integer> parsePlayerCards(String cards) {
        String[] playerCards = cards.split("\\|")[1].trim().split("\\s+");

        List<Integer> result = new ArrayList<Integer>();
        for (String card : playerCards) {
            result.add(Integer.valueOf(card));
        }

        return result;
    }
}