package main

import "fmt"

type person struct {
	id   int32
	name string
	age  int8
}
/**
  字段名称以小写开头 为可以全局访问 如果以小写开头表示私有 只当前文件内可以访问
 */
func main() {
	var p1 person //结构体初始化
	p1.name = "abc"
	p1.id = 1
	p1.age = 20
	fmt.Println(p1)

	var arr [5] struct{}
	fmt.Println("%V",arr)


	//匿名结构体

	var user struct {
		Name string
		Age  int
	}
	user.Name = "pprof.cn"
	user.Age = 18
	fmt.Printf("%#v\n", user)

	var p2 = new(person) //创建指针类型结构体
	p2.id = 2
	p2.name = "aaa"
	p2.age = 22
	fmt.Printf("p2=%#v\n", p2)

	p3 := &person{} // 使用&对结构体进行取地址操作相当于对该结构体类型进行了一次new实例化操作
	p3.id = 3
	p3.name = "bbb"
	p3.age = 24
	fmt.Printf("p3=%#v\n", p3)

	//使用键值对进行初始化
	p4 := person{
		id:   4,
		name: "ccc",
		age:  21,
	}
	fmt.Printf("p4=%#v\n", p4)

	//使用值的列表初始化
	//顺序需要与定义的时候对应
	p5 := &person{
		5,
		"jack",
		18,
	}
	fmt.Printf("p8=%#v\n", p5)

	//使用自定义构造函数初始化
	p6 := newPerson(6, "tony", 33)
	fmt.Printf("p8=%#v\n", p6)

	p6.setAge(22)
	fmt.Printf("p8=%#v\n", p6)  //22

	p6.setAge2(28)

	var a NewInt

	var b MyInt
	fmt.Printf("type of a:%T\n", a) //type of a:main.NewInt
	fmt.Printf("type of b:%T\n", b) //type of b:int
}

//定义类型
type NewInt = int

//给int取别名，类型别名
type MyInt int

//自定义构造函数，go没有默认的构造
func newPerson(id int32, name string, age int8) *person {
	return &person{
		id:   id,
		name: name,
		age:  age,
	}
}
//给person对象添加方法，使用指针接收者，调用会影响原对象
func(p *person) setAge(age int8){
	p.age = age
}
//给person对象添加方法，使用置对象接收者，运行时将接收者的值复制一份，指挥影响对象副本，不会影响原对象
func(p person) setAge2(age int8){
	p.age = age
}
