package demo


// 自定义函数，如果需要提供给其他包调用，函数名称首字母需要大写，否则为私有
func TestAdd(a, b int) int {
	return a + b
}
