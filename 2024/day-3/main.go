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
	part1Multiplications, err := parseInput("input.txt", `mul\((\d{1,3}),(\d{1,3})\)`)
	if err != nil {
		fmt.Println("Error parsing input file:", err)
		return
	}
	part2Multiplications, err := parseInput("input.txt", `mul\((\d{1,3}),(\d{1,3})\)|don't\(\)|do\(\)`)
	if err != nil {
		fmt.Println("Error parsing input file:", err)
	}

	fmt.Printf("Part 1: %v\n", compute(part1Multiplications))
	fmt.Printf("Part 2: %v\n", compute(part2Multiplications))
	return
}

func compute(multiplications []MulOperation) int {
	sum := 0
	for _, mul := range multiplications {
		sum += mul.Left * mul.Right
	}
	return sum
}

// Returns the multiplications in format [[mul(123,456), 123, 456], [mul(111,222), 111, 222]
// If there is a do() operation then it includes the muls else if don't() then they are excluded
func parseInput(filename string, pattern string) ([]MulOperation, error) {
	data, err := os.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	input := string(data)
	regex := regexp.MustCompile(pattern)
	matches := regex.FindAllStringSubmatch(input, -1)

	shouldInclude := true
	var operations []MulOperation
	for _, match := range matches {
		value := match[0]
		if value == "do()" {
			shouldInclude = true
			continue
		}
		if value == "don't()" {
			shouldInclude = false
			continue
		}

		if shouldInclude {
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
	}
	return operations, nil
}
