package main

import (
	"fmt"
)

type User struct {
	Id    int
	Name  string
	Age   int
	Email string
}

func (user User) Notify1() {
	fmt.Printf("%+v : %+v \n", user.Name, user.Email)
}
func (user User) changeEmail(newEmail string) User {
	user.Email = newEmail
	return user

}
func (user *User) Notify2() {
	fmt.Printf("%+v : %+v \n", user.Name, user.Email)
}

func main() {
	user1 := User{1, "Paul", 34, "abc@haha.cn"}
	user1.Notify1()
	user3 := user1.changeEmail("bbb@hh.com")
	fmt.Println("%V", user1)
	user2 := &user1
	user2.Notify2()
	user4 := user2.changeEmail("ccc@hh.com")

	fmt.Println("%V", user3)
	fmt.Println("%V", user4)
	fmt.Println("%V", user2)
	fmt.Println("%V", user1)
}
