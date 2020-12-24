package main

import (
	"demo"
	"fmt"
	"unsafe"
)

func main()  {
	fmt.Println("hello world!")
	fmt.Println(addFunc(1,2))
	demo.TestAdd(1,2)
	var (
		a string
		b bool
		c float32
		d int32
	)
	fmt.Println(a)
	fmt.Println(b)
	fmt.Println(c)
	fmt.Println(d)

	b1:= true
	fmt.Println(b1)
	//打印对象占用的空间
	fmt.Println(unsafe.Sizeof(b1))

	//类型转换，需要进行显示转换，而已类型不能溢出，否则按溢出处理，转换结果会不可控
	var i1 int8 = 5
	i2 := int32(i1)  // 值传递

	fmt.Printf("%v,%T,%T",i1,i1,i2)



}
func addFunc(a, b int32) int32 {
	return a + b
}


