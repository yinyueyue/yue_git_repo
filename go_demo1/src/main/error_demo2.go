package main

import "fmt"

func main() {
	//test01()
	getCircleArea(-1)
}
func test01() {

	//捕获异常
	defer func() {
		if r := recover(); r != nil {
			fmt.Printf("捕获到的错误：%s\n", r)
		}
	}()
	a := [5]int{0, 1, 2, 3, 4}
	a[1] = 123
	fmt.Println(a)
	//a[10] = 11
	index := 10
	a[index] = 10
	fmt.Println(a)
}

func getCircleArea(radius float32) (area float32) {
	if radius < 0 {
		panic("半径不能是负数")
	}

	return radius * radius * 3.14

}
