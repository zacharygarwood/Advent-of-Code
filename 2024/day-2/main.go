package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
	reports, err := parseInput("input.txt")
	if err != nil {
		fmt.Println("Error parsing input:", err)
		return
	}
	fmt.Printf("Part 1: %d\n", part1(reports))
	fmt.Printf("Part 2: %d\n", part2(reports))
	return
}

func part1(reports [][]int) int {
	return safeReportsWithTolerance(reports, 0)
}

func part2(reports [][]int) int {
	return safeReportsWithTolerance(reports, 1)
}

func safeReportsWithTolerance(reports [][]int, tolerance int) int {
	safeReports := 0
	for _, report := range reports {
		if isSafe(report) {
			safeReports++
		} else if isSafeWithTolerance(report, tolerance) {
			safeReports++
		}
	}
	return safeReports
}

func isSafe(levels []int) bool {
	if len(levels) < 2 {
		return true
	}
	increasing := true
	decreasing := true

	for i := 1; i < len(levels); i++ {
		diff := levels[i] - levels[i-1]
		if diff < -3 || diff > 3 {
			return false
		}
		if diff >= 0 {
			decreasing = false
		}
		if diff <= 0 {
			increasing = false
		}
	}
	return increasing || decreasing
}

func isSafeWithTolerance(levels []int, tolerance int) bool {
	for i := 0; i < len(levels); i++ {
		subLevel := append([]int{}, levels[:i]...)
		subLevel = append(subLevel, levels[i+tolerance:]...)
		if isSafe(subLevel) {
			return true
		}
	}
	return false
}

func parseInput(filename string) ([][]int, error) {
	file, err := os.Open(filename)
	if err != nil {
		fmt.Println("Error opening file:", err)
		return nil, err
	}
	defer file.Close()

	var result [][]int
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		fields := strings.Fields(line)
		var nums []int
		for _, field := range fields {
			num, err := strconv.Atoi(field)
			if err != nil {
				fmt.Println("Error parsing field:", field)
				return nil, err
			}
			nums = append(nums, num)
		}
		result = append(result, nums)
	}
	return result, scanner.Err()
}
