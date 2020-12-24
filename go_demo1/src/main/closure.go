package main

import (
	"fmt"
)

func main()  {
	c := a()
	c()
	c()
	c()
	c2:= a()
	a()
	c2()
	c2()
	c2()
	c2()

	tmp1 := addOne(10)
	fmt.Println(tmp1(1), tmp1(2))
	// 此时tmp1和tmp2不是一个实体了
	tmp2 := addOne(100)
	fmt.Println(tmp2(1), tmp2(2))
}

//闭包

// 定义函数a()，返回值为类型为int的函数
func a() func() int{
	i := 0
	b := func() int {
		i++
		fmt.Println(i)
		return i
	}
	return b
}
// 外部引用函数参数局部变量
func addOne(base int) func(int) int {
	return func(i int) int {
		base += i
		return base
	}
}