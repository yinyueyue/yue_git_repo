package main

import (
	"fmt"
)

func main() {
	test1()
}
//defer 特性
/*
1. 关键字 defer 用于注册延迟调用。
2. 这些调用直到 return 前才被执。因此，可以用来做资源清理。
3. 多个defer语句，按先进后出的方式执行。
4. defer语句中的变量，在defer声明时就决定了。
*/
func test1() {
	defer func() {
		fmt.Println(recover()) //有效
	}()
	defer recover()              //无效！ 因为异常而中断
	defer fmt.Println(recover()) //无效！

	//闭包   defer执行顺序 先入后出
	defer func() {
		func() {
			println("defer inner")
			recover() //无效！
		}()
	}()

	panic("test panic")
	/*  //抛出异常并捕捉
	Try(func() {
		panic("test2 panic")
	}, func(err interface{}) {
		fmt.Println(err)
	})

	*/
}

func test2() {

	defer func() {

		if r := recover(); r != nil {
			fmt.Printf("捕获到的错误：%s\n", r)
		}
	}()
	var a, b int
	a, b = 1, 1

	// 捕获运行时异常
	c := 3 / (a - b)
	fmt.Println(a, b, c)

	//捕获手动抛出的异常
	//panic("new error")

}
func Try(fun func(), handler func(interface{})) {
	defer func() {
		if err := recover(); err != nil {
			handler(err)
		}
	}()
	fun()
}
