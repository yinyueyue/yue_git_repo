package main

import (
	"fmt"
	"strings"
)

//闭包
func main() {
	suffixFunc := makeSuffix(".jpg")
	fmt.Println(suffixFunc("haha"))
	fmt.Println(suffixFunc("abc.jpg"))
}

//声明函数返回闭包，接收一个文件后缀名（比如.jpg）,返回一个闭包，
//调用闭包，可以传入一个文件名，如果后缀为.jpg，直接返回文件名，否则添加后缀名
func makeSuffix(suffix string) func(string) string {
	return func(str string) string {
		if strings.HasSuffix(str, suffix) {
			return str
		} else {
			return str + suffix
		}
	}
}
