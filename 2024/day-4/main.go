package main

import (
	"fmt"
	"os"
	"strings"
)

var XMAS string = "XMAS"

type Location struct {
	x int
	y int
}

type Direction struct {
	x int
	y int
}

func main() {
	input, err := parseInput("input.txt")
	if err != nil {
		fmt.Println("Error parsing input:", err)
		return
	}

	fmt.Printf("Part 1: %d", part1(input))
	return
}

func part1(input [][]string) int {
	directions := [8]Direction{
		{x: 0, y: 1},
		{x: 0, y: -1},
		{x: -1, y: 0},
		{x: 1, y: 0},
		{x: -1, y: 1},
		{x: 1, y: 1},
		{x: -1, y: -1},
		{x: 1, y: -1},
	}

	result := 0
	rows := len(input)
	cols := len(input[0])
	for x := 0; x < cols; x++ {
		for y := 0; y < rows; y++ {
			if input[x][y] == "X" {
				result += checkForXmasInAllDirections(input, Location{x, y}, directions)
			}
		}
	}
	return result
}

func checkForXmasInAllDirections(input [][]string, location Location, directions [8]Direction) int {
	occurrences := 0
	for _, direction := range directions {
		if checkForXmas(input, location, direction) {
			occurrences++
		}
	}
	return occurrences
}

func checkForXmas(input [][]string, location Location, direction Direction) bool {
	rows := len(input)
	cols := len(input[0])
	for i := 0; i < len(XMAS); i++ {
		x, y := location.x+direction.x*i, location.y+direction.y*i
		inBounds := x >= 0 && x < cols && y >= 0 && y < rows
		if !inBounds || input[x][y] != string(XMAS[i]) {
			return false
		}
	}
	return true
}

func parseInput(filename string) ([][]string, error) {
	data, err := os.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	var input [][]string
	lines := strings.Split(string(data), "\n")
	for _, line := range lines {
		if line == "" {
			continue
		}
		input = append(input, strings.Split(line, ""))
	}
	return input, nil
}
