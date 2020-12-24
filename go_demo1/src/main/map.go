package main

import (
	"fmt"
	"strconv"
)

func main()  {
	//map 使用make来创建，初始化容量未8，key类型string ，value类型int16
	scoreMap := make(map[string]int,8)
	scoreMap["张三"] = 100
	scoreMap["李四"] = 99
	scoreMap["老王"] = 98
	fmt.Println(scoreMap)
	fmt.Println(scoreMap["张三"])
	fmt.Printf("type of a:%T\n", scoreMap)

	//声明时设置初始值
	userInfo := map[string]string{
		"username": "pprof.cn",
		"password": "123456",
	}
	fmt.Println(userInfo)

	//判断键值是否存在
	value,ok := scoreMap["张三"]
	if ok {
		fmt.Println(value)
	}

	//遍历map
	for k,v  :=range scoreMap{
		fmt.Println("key:="+k +" ,value:"+strconv.Itoa(v)) //int转string 工具类
	}

	//删除map
    delete(scoreMap,"老王")
	fmt.Println(scoreMap)


}