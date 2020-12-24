package main

import "fmt"

func main() {

	//&（取地址）和*（根据地址取值）。
	a := 10
	b := &a
	fmt.Printf("a:%d ptr:%p\n", a, &a) // a:10 ptr:0xc00001a078
	fmt.Printf("b:%p type:%T\n", b, b) // b:0xc00001a078 type:*int
	fmt.Println(&b)                    // 0xc00000e018
	var ab = 32
	aa(&ab)

	var c int16= 10
	d := modify1(c)

	fmt.Println(c) // 10
	fmt.Println(d) // 100
	modify2(&c)
	fmt.Println(c) // 100
	fmt.Printf("b=%v\n",*b)

	// *b 获取b指向的值
	var i int = *b
	fmt.Printf("b=%v\n",i)

	//引用类型内存分配
	var aa *int // 引用类型声明
	aa = new(int) //分配内存，如果没有这一行，会报panic错误
	*aa = 10 //赋值
	fmt.Println(*aa)
}

func aa(a *int) {
	fmt.Println(a)
}

//值传递，不会改变原值
func modify1(x int16) int16 {
	x = 100
	return x
}

//传递的对象地址，修改会影响原来的值
func modify2(x *int16) {
	*x = 100
	fmt.Printf("x:%p type:%T\n", x, x)

}
