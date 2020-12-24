package main

import (
	"fmt"
	"utils"
)

//init 函数由go框架自动调用，会先于main方法执行，可用于资源初始化
//如果引入的包里面存在init函数，会先执行引入包文件里面的init函数
func init()  {
	fmt.Println("init() execute...")
}

func main() {

	//先执行utils包里面的init函数
	utils.Add(1,2)


	var x = 10
	var y = 20
	swap(&x, &y)
	fmt.Println(x, y)

	swap2(x, y)
	fmt.Println(x, y)

	slice := []int{1, 2, 3}
	swapSlice(&slice)
	fmt.Printf("slice after introduce swap: %v\n", slice)

	slice2 := []int{1, 2, 3}
	swapSlice2(slice2)
	fmt.Printf("slice after introduce swap: %v\n", slice2)

	str := concat("hello ", "2", "3")
	fmt.Println(str)

	sum, avg := calculate(100, 50)
	fmt.Println(sum, avg)

	fmt.Println(add3(3, 4))

	day := 10
	totalCount := getPeachCount(day)

	fmt.Printf("day: %v,pear count:%v ", day, totalCount)
}

func add(x, y int) int {
	return x + y
}

//引用传递
func swap(x, y *int) {
	var temp int
	temp = *x
	*x = *y
	*y = temp
}

//值传递
func swap2(x, y int) {
	var temp int
	temp = x
	x = y
	y = temp
}

//引用传递
func swapSlice(slice *[]int) {
	var temp []int

	for i, n := len(*slice), 0; i > n; i-- {
		temp = append(temp, (*slice)[i-1])
	}
	*slice = temp
}

//值传递
func swapSlice2(slice []int) {
	var temp []int

	for i, n := len(slice), 0; i > n; i-- {
		temp = append(temp, (slice)[i-1])
	}
	slice = temp
}

func concat(args ...string) string {
	var temp string
	for _, arg := range args {
		temp = temp + arg
	}
	return temp
}

// 多返回值
func calculate(a, b int) (sum, avg int) {
	return a + b, (a + b) / 2
}

func add3(x, y int) (z int) {
	//闭包，延迟调用，执行顺序: (z = x + y) -> (call defer) -> (return)
	defer func() { //类似于java的finally
		z += 100
	}()

	z = x + y
	return
}

// 假设后一天的桃子数量为前一天的数量的一半少1个，且第10天剩下1个
//递归 获取第n天的桃子数量
func getPeachCount(day int) int {
	if day > 10 {
		panic("输入的天数错误")
	}
	if day == 10 {
		return 1
	}
	return 2 * (getPeachCount(day+1) + 1)
}
