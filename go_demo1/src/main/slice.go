package main

import "fmt"

//slice 切片
/*  slice 并不是数组或数组指针。它通过内部指针和相关属性引用数组片段，以实现变长方案。
   切片相当于是一个数组的部分切分，关联原数组的部分元素的起止角标，并不复制数据，改变原数组同样也会改变切片的值
1. 切片：切片是数组的一个引用，因此切片是引用类型。但自身是结构体，值拷贝传递。
2. 切片的长度可以改变，因此，切片是一个可变的数组。
3. 切片遍历方式和数组一样，可以用len()求长度。表示可用元素数量，读写操作不能超过该限制。
4. cap可以求出slice最大扩张容量，不能超出数组限制。0 <= len(slice) <= len(array)，其中array是slice引用的数组。
5. 切片的定义：var 变量名 []类型，比如 var str []string  var arr []int。
6. 如果 slice == nil，那么 len、cap 结果都等于 0
*/
func main() {
	//1.声明切片
	var s1 []int
	if s1 == nil {
		fmt.Println("是空")
	} else {
		fmt.Println("不是空")
	}
	// 2.:=
	s2 := []int{}
	// 3.make()
	var s3 []int = make([]int, 0)
	fmt.Println(s1, s2, s3)
	// 4.初始化赋值
	var s4 []int = make([]int, 0, 0)
	fmt.Println(s4)
	s5 := []int{1, 2, 3}
	fmt.Println(s5)
	// 5.从数组切片
	arr := [5]int{1, 2, 3, 4, 5}
	var s6 []int
	// 前包后不包
	s6 = arr[1:4]
	fmt.Println(s6)
	var arr1 = [3]int{1, 2, 3}
	fmt.Println(&arr1) //将&arr1获取对象地址值
	fmt.Printf("arrayA : %p ", &arr1)

	/**
			%v  ：打印相应值的默认格式  Printf("%v", a) 123
			%+v : 打印结构体时会添加字段名称 Printf("%+v", user)  {Name:zhangsan}
	 		%p  : 打印地址（指针地址）的，是十六进制的形式，但是会全部打完，即有多少位打印多少位
			%x  : 无符号十六进制整数(字母小写，不像上面指针地址那样补零)
			%X  : 无符号十六进制整数(字母大写，不像上面指针那样补零)
	*/
	var aa = [5]int{1, 2, 3, 4, 5}
	var sliceA1 = aa[2:cap(aa)]
	fmt.Println()
	fmt.Printf("%v", sliceA1)
	aa[2] = 6
	fmt.Println()
	fmt.Printf("%v", sliceA1)

	var sliceA2 = aa[1:]
	fmt.Println()
	fmt.Printf("%v", sliceA2)

	var sliceA3 = aa[:]
	fmt.Println()
	fmt.Printf("%v", sliceA3)
	
	var sliceA4 = aa[:cap(aa)]
	fmt.Println()
	fmt.Printf("%v", sliceA4)

	//所有索引号初始化，参考数组
	s11 := []int{0, 1, 2, 3, 8: 100} // 通过初始化表达式构造，可使用索引号。
	fmt.Println(s11, len(s11), cap(s11))

	s22 := make([]int, 6, 8) // 使用 make 创建，指定 len 和 cap 值。
	fmt.Printf("%v", s22)

	// 角标越界，切片长度为6，容量为8，容量表示底层数组的大小，长度是你可以使用的大小
	/*s22[6] = 10
	fmt.Printf("%v", s22)*/

	data := [...]int{0, 1, 2, 3, 4, 10: 0}
	//建立从data 角标0开始，长度为2，容量为3的切片
	s := data[:2:3]

	s = append(s, 100, 200) // 一次 append 两个值，超出 s.cap 限制。

	fmt.Println(s, data)         // 重新分配底层数组，与原数组无关。
	fmt.Println(&s[0], &data[0]) // 比对底层数组起始指针。

	//遍历，类似java for(i=0 ;i<length;i++) 方式
	for i := range data {
		fmt.Println("v%", data[i])
	}

	//类似java的增强for循环，同时可以得到数据的index
	for index ,value:=range data{
		fmt.Printf("index : %v , value : %v\n", index, value)
	}

	a1 := []int{1, 2, 3, 4, 5}
	fmt.Printf("slice s1 : %v\n", a1)
	a2 := make([]int, 10)
	fmt.Printf("slice s2 : %v\n", a2)
	copy(a2, a1)
	fmt.Printf("copied slice s1 : %v\n", a1)
	fmt.Printf("copied slice s2 : %v\n", a2)
	a3 := []int{1, 2, 3}
	fmt.Printf("slice s3 : %v\n", a3)
	s3 = append(a3, a2...)
	fmt.Printf("appended slice s3 : %v\n", a3)
	a3 = append(a3, 4, 5, 6)
	fmt.Printf("last slice s3 : %v\n", a3)

	arrayA := [2]int{100, 200}
	var arrayB [2]int

	//在 Go 中，与 C 数组变量隐式作为指针使用不同，Go 数组是值类型，赋值和函数传参操作都会复制整个数组数据。


	arrayB = arrayA

	fmt.Printf("arrayA : %p , %v\n", &arrayA, arrayA)
	fmt.Printf("arrayB : %p , %v\n", &arrayB, arrayB)

}

func testArrayPoint(x *[]int) {
	fmt.Printf("func Array : %p , %v\n", x, *x)
	(*x)[1] += 100

}
