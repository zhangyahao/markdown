####Go有什么优势
 1. 可直接编译成机器码，不依赖其他库，glibc的版本有一定要求，部署就是扔一个文件上去就完成了。
 2. 静态类型语言，但是有动态语言的感觉，静态类型的语言就是可以在编译的时候检查出来隐藏的大多数问题，
    动态语言的感觉就是有很多的包可以使用，写起来的效率很高。
 3. 语言层面支持并发，这个就是Go最大的特色，天生的支持并发.   
 4. 内置runtime，支持垃圾回收
 5. 内置强大的工具，Go语言里面内置了很多工具链，最好的应该是gofmt工具，自动化格式化代码，
     能够让团队review变得如此的简单，代码格式一模一样，想不一样都很困难。
 6. 跨平台编译，如果你写的Go代码不包含cgo，那么就可以做到window系统编译linux的应用，如何做到的呢？Go引用了plan9的代码，
     这就是不依赖系统的信息。
 7. 内嵌C支持    