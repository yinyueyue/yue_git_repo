package main

import (
	"demo"
	"fmt"
)

func main() {
	var animal demo.Animal

	var dog = Dog{}
	animal = dog
	animal.Say()

	var cat = Cat{}
	animal = cat
	animal.Say()

	// 使用值接收者实现接口后，不论是结构体cat还是指针类型*cat都可以赋值给接口变量，
	//因为Go语言中有对指针类型变量求值的语法糖，cat指针cat内部会自动求值*cat
	var cat2 = &Cat{}
	animal = cat2
	animal.Say()


	var bird = &Bird{}
	animal = bird
	bird.Say()

	var bird2 = Bird{}
	bird2.Say()

	show(123)

	//使用空接口作为map的值
	var studentInfo = make(map[string]interface{})
	studentInfo["name"] = "李白"
	studentInfo["age"] = 18
	studentInfo["married"] = false
	fmt.Println(studentInfo)


}

type Dog struct {
}


// 实现sayer接口
func (d Dog) Say() {
	fmt.Println("汪汪汪……")
}

type Cat struct {
}

func (c Cat) Say() {
	fmt.Println("喵喵喵……")
}
type Bird struct {
}

func (bird *Bird) Say() {
	fmt.Println("叽叽叽……")
}
//空接口是指没有定义任何方法的接口。因此任何类型都实现了空接口。
// 空接口作为函数参数,相当于object类型，可以接收任意类型的参数
func show(a interface{}) {
	fmt.Printf("type:%T value:%v\n", a, a)
}


