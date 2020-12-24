package main

import (
	"demo"
	"fmt"
)

var arr0 [5]int = [5]int{1, 2, 3}
var arr1 = [5]int{1, 2, 3, 4, 5}
var arr2 = [...]int{1, 2, 3, 4, 5, 6}
var str = [5]string{3: "hello world", 4: "tom"}

var arr3 [5][3]int
var arr4 [2][3]int = [...][3]int{{1, 2, 3}, {7, 8, 9}}

func main() {
	sumAdd := demo.TestAdd(12, 3)
	fmt.Println(sumAdd)
	a := [3]int{1, 2}           // 未初始化元素值为 0。
	b := [...]int{1, 2, 3, 4}   // 通过初始化值确定数组长度。
	c := [5]int{2: 100, 4: 200} // 使用索引号初始化元素
	d := [...]struct {
		name string
		age  uint8
	}{
		{"user1", 10}, // 可省略元素类型。
		{"user2", 20}, // 别忘了最后一行的逗号。
	}
	fmt.Println(arr0, arr1, arr2, str)
	fmt.Println(a, b, c, d)
	abc := [3]int{1, 2, 3}
	fmt.Println(abc)

	//多维数组
	e := [2][3]int{{1, 2, 3}, {4, 5, 6}}
	f := [...][2]int{{1, 1}, {2, 2}, {3, 3}} // 第 2 纬度不能用 "..."。
	fmt.Println(arr3, arr4)
	fmt.Println(e, f)

	//数组遍历
	for v1, v2 := range f {

		for k1, k2 := range v2 {
			fmt.Printf("(%d,%d)=%d ", v1, k1, k2)
		}
		fmt.Println()

	}
	var sum int
	for v := range a {
		sum += v
		fmt.Println(v)
	}
	fmt.Printf("%d", sum)

}
