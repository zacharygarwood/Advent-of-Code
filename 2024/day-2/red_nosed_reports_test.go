package main

import (
	"testing"
)

func TestCase1(t *testing.T) {
	levels := []int{7, 6, 4, 2, 1}

	if !isSafeWithTolerance(levels, 0) {
		t.Fatalf("Levels: %v not safe with tolerance: %d", levels, 0)
	}

	if !isSafeWithTolerance(levels, 1) {
		t.Fatalf("Levels: %v not safe with tolerance: %d", levels, 1)
	}
}

func TestCase2(t *testing.T) {
	levels := []int{1, 2, 7, 8, 9}

	if isSafeWithTolerance(levels, 0) {
		t.Fatalf("Levels: %v safe with tolerance: %d", levels, 0)
	}

	if isSafeWithTolerance(levels, 1) {
		t.Fatalf("Levels: %v safe with tolerance: %d", levels, 1)
	}
}

func TestCase3(t *testing.T) {
	levels := []int{9, 7, 6, 2, 1}

	if isSafeWithTolerance(levels, 0) {
		t.Fatalf("Levels: %v safe with tolerance: %d", levels, 0)
	}

	if isSafeWithTolerance(levels, 1) {
		t.Fatalf("Levels: %v safe with tolerance: %d", levels, 1)
	}
}

func TestCase4(t *testing.T) {
	levels := []int{1, 3, 2, 4, 5}

	if isSafeWithTolerance(levels, 0) {
		t.Fatalf("Levels: %v safe with tolerance: %d", levels, 0)
	}

	if !isSafeWithTolerance(levels, 1) {
		t.Fatalf("Levels: %v not safe with tolerance: %d", levels, 1)
	}
}

func TestCase5(t *testing.T) {
	levels := []int{8, 6, 4, 4, 1}

	if isSafeWithTolerance(levels, 0) {
		t.Fatalf("Levels: %v safe with tolerance: %d", levels, 0)
	}

	if !isSafeWithTolerance(levels, 1) {
		t.Fatalf("Levels: %v not safe with tolerance: %d", levels, 1)
	}
}

func TestCase6(t *testing.T) {
	levels := []int{1, 3, 6, 7, 8}

	if !isSafeWithTolerance(levels, 0) {
		t.Fatalf("Levels: %v not safe with tolerance: %d", levels, 0)
	}

	if !isSafeWithTolerance(levels, 1) {
		t.Fatalf("Levels: %v not safe with tolerance: %d", levels, 1)
	}
}
