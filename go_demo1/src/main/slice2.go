package main

import "fmt"

func main()  {

	var array1 = [5]int{1,2,3,4,5}
	s2 := array1[:3]
	fmt.Printf("s2 before: %v\n",s2)

	// 超出原 slice.cap 限制，就会重新分配底层数组，即便原数组并未填满。
	s2 = append(s2,6,7,8)
	fmt.Printf("s2 after: %v\n",s2)
	fmt.Printf("s1 after: %v\n",array1)

	//因为重新分配数组，改变切片不会对原数组产生影响
	s2[0] = 0
	fmt.Printf("s2 after2: %v\n",s2)
	fmt.Printf("s1 after2: %v\n",array1)
}