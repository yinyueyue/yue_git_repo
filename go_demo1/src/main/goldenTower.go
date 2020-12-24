package main

import "fmt"

func main() {
	var level int = 15
	var totalLength int = level*2 - 1
	fmt.Println("")
	for i := 1; i <= level; i++ {
		currentWidth := 2*i - 1
		leftSpaceLen := (totalLength - currentWidth) / 2
		for j := 1; j <= totalLength; j++ {
			if j > leftSpaceLen && j <= leftSpaceLen+currentWidth {
				fmt.Print("*")
			} else {
				fmt.Print(" ")
			}
		}
		fmt.Print("\n")
	}
}
