package main

import (
	"fmt"
	"os"
	"regexp"
	"strconv"
)

type MulOperation struct {
	Value string
	Left  int
	Right int
}

func main() {
	multiplications, err := parseInput("input.txt")
	if err != nil {
		fmt.Println("Error parsing input file:", err)
		return
	}
	fmt.Printf("Part 1: %v\n", part1(multiplications))
	return
}

func part1(multiplications []MulOperation) int {
	sum := 0
	for _, mul := range multiplications {
		sum += mul.Left * mul.Right
	}
	return sum
}

// Returns the multiplications in format [[mul(123,456), 123, 456], [mul(111,222), 111, 222]
func parseInput(filename string) ([]MulOperation, error) {
	data, err := os.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	input := string(data)
	pattern := `mul\((\d{1,3}),(\d{1,3})\)`
	regex := regexp.MustCompile(pattern)
	matches := regex.FindAllStringSubmatch(input, -1)

	var operations []MulOperation
	for _, match := range matches {
		left, err1 := strconv.Atoi(match[1])
		right, err2 := strconv.Atoi(match[2])
		if err1 != nil || err2 != nil {
			fmt.Printf("Error converting mul values to ints left: %s, right: %s", match[1], match[2])
			return nil, err1
		}
		operation := MulOperation{
			Value: match[0],
			Left:  left,
			Right: right,
		}
		operations = append(operations, operation)
	}
	return operations, nil
}
