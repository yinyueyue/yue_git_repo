package main

import (
	"fmt"
	"strconv"
)

//基础数据类型转换为string
func main() {

	//int 2 string
	var a int = 10

	intString := fmt.Sprintf("%d", a)
	fmt.Printf("%q\n", intString)

	// float 2 string
	var b float64 = 12.888
	floatString := fmt.Sprintf("%f", b)
	fmt.Printf("%q\n", floatString)

	var c bool = true

	boolString := fmt.Sprintf("%t", c)
	fmt.Printf("%q\n", boolString)

	var myChar byte = 'x'
	charString := fmt.Sprintf("%c", myChar)
	fmt.Printf("%q\n", charString)

	//转换方式2，使用strconv包函数
	formatInt := strconv.FormatInt(int64(a), 10)
	fmt.Printf("%q\n", formatInt)

	// 'f' fmt表示格式：'f'（-ddd.dddd）、'b'（-ddddp±ddd，指数为二进制）、'e'（-d.dddde±dd，十进制指数）、
	//'E'（-d.ddddE±dd，十进制指数）、'g'（指数很大时用'e'格式，否则'f'格式）、'G'（指数很大时用'E'格式，否则'f'格式
	// prec 保留精度
	// bitSize 传入的float类型 64为float64
	formatFloat := strconv.FormatFloat(b, 'f', 4, 64)
	fmt.Printf("%q\n", formatFloat)

	parseInt, _ := strconv.ParseInt("10", 10, 64)
	fmt.Printf("%q\n", parseInt)

}
