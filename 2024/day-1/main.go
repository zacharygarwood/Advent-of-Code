package main

import (
	"bufio"
	"container/heap"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

func main() {
	startingLocations, endingLocations, err := parseInput("input.txt")
	if err != nil {
		fmt.Println("Error scanning file:", err)
		return
	}
	n := len(startingLocations)
	part1(n, startingLocations, endingLocations)
	part2(n, startingLocations, endingLocations)

	return
}

func part1(n int, startingLocations, endingLocations []int) {
	startingLocHeap, endingLocHeap := heapify(startingLocations), heapify(endingLocations)
	sum := 0
	for i := 0; i < n; i++ {
		difference := heap.Pop(startingLocHeap).(int) - heap.Pop(endingLocHeap).(int)
		sum += abs(difference)
	}
	fmt.Printf("Part 1: %d\n", sum)
}

func part2(n int, startingLocations, endingLocations []int) {
	counts := make(map[int]int)
	for _, value := range endingLocations {
		counts[value]++
	}

	sum := 0
	for i := 0; i < n; i++ {
		num := startingLocations[i]
		sum += num * counts[num]
	}
	fmt.Printf("Part 2: %d\n", sum)
}

func parseInput(filename string) ([]int, []int, error) {
	file, err := os.Open(filename)
	if err != nil {
		fmt.Println("Error opening file:", err)
		return nil, nil, err
	}
	defer file.Close()

	var nums1, nums2 []int
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()
		fields := strings.Fields(line)
		if len(fields) != 2 {
			fmt.Println("Invalid line:", line)
			return nil, nil, err
		}

		num1, err1 := strconv.Atoi(fields[0])
		num2, err2 := strconv.Atoi(fields[1])
		if err1 != nil || err2 != nil {
			fmt.Println("Error parsing line:", line)
			return nil, nil, err
		}

		nums1 = append(nums1, num1)
		nums2 = append(nums2, num2)
	}
	return nums1, nums2, scanner.Err()
}

func abs(num int) int {
	return int(math.Abs(float64(num)))
}
