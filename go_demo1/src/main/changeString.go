package main

import (
	"fmt"
)

func main() {
	changeString()
	traversalString()

	//使用反引号输出字符串，字符串会以原生形式输出（包括换行符和特殊字符），可以防止攻击，输出源码等

	str := `func traversalString() {
	s := "pprof.cn博客"
	for i := 0; i < len(s); i++ { //byte
		fmt.Printf("%v(%c) \n", s[i], s[i])
	}
	fmt.Println("")
	for i := range s {
		fmt.Printf("%v(%c) \n", s[i], s[i])
	}
	fmt.Println("")
	for _, r := range s { //rune
		fmt.Printf("%v(%c) ", r, r)
	}
	fmt.Println()
}`
	fmt.Println(str)
}

/**
 * 修改字符串
 */
func changeString() {

	s1 := "hello"
	//    uint8类型，或者叫 byte 型，代表了ASCII码的一个字符。
	// byte类似java的char
	//强制类型转换，转换为byte数组
	byteS1 := []byte(s1)

	//替换数组的值
	byteS1[0] = 'H'

	// byte数组转字符串
	fmt.Println(string(byteS1))

	s2 := "Go,Hello!"
	byteS2 := []byte(s2)
	byteS2[0] = 'g'

	fmt.Println(string(byteS2))

	//对于中文汉字或其他复合语言，需要使用rune来处理
	s3 := "今天天气真好啊"

	runeS3 := []rune(s3)
	runeS3[0] = '明'
	fmt.Println(string(runeS3))
}

// 遍历字符串
func traversalString() {
	s := "pprof.cn博客"
	for i := 0; i < len(s); i++ { //byte
		fmt.Printf("%v(%c) \n", s[i], s[i])
	}
	fmt.Println("")
	for i := range s {
		fmt.Printf("%v(%c) \n", s[i], s[i])
	}
	fmt.Println("")
	for _, r := range s { //rune
		fmt.Printf("%v(%c) ", r, r)
	}
	fmt.Println()
}
