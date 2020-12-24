package main

import (
	"encoding/json"
	"fmt"
	"sort"
)

func main() {
	class := &Class{
		Tittle:   "102",
		Students: make([]*Student, 0, 200),
	}

	for i := 0; i < 10; i++ {
		student := &Student{
			Id:     i,
			Name:   "abc",
			Gender: i % 2,
			age:    20,
		}
		class.Students = append(class.Students, student)
	}

	//json 序列化
	data, err := json.Marshal(class)
	if err != nil {
		fmt.Println("json marshal failed", err)
	}
	fmt.Printf("json:%s\n", data)

	class2 := &Class{}
	err2 := json.Unmarshal(data, class2)
	if err2 != nil {
		fmt.Println("json unmarshal failed", err)
	}
	fmt.Printf("%#v\n", class2)

	var students  []Student
	students = []Student{
		Student{1, "xiaoming", 22,18},
		Student{2, "xiaozhang", 33,20},
	}
	fmt.Println(students)
	change(students)
	fmt.Println(students)

	map1 := make(map[int]string, 5)
	map1[1] = "www.topgoer.com"
	map1[2] = "rpc.topgoer.com"
	map1[5] = "ceshi"
	map1[3] = "xiaohong"
	map1[4] = "xiaohuang"
	var sli []int
	for k, _ := range map1 {
		sli = append(sli, k)
	}
	sort.Ints(sli)
	for i := 0; i < len(map1); i++ {
		fmt.Println(map1[sli[i]])
	}




}

type Student struct {
	Id     int  `json:"id"` //通过指定tag实现json序列化该字段时的key
	Name   string
	Gender int
	age    int // 小写开头，序列化时会不能访问，私有， 会被忽略
}

type Class struct {
	Tittle   string
	Students []*Student
}

func change(ce []Student) {
	//切片是引用传递，是可以改变值的
	ce[1].age = 999
	// ce = append(ce, student{3, "xiaowang", 56})
	// return ce
}