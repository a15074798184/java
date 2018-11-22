## java-标签语法和使用

在JAVA中，标签是后面带有冒号的标识符，

	label1：

在JAVA中，标签起作用的唯一的地方刚好是迭代语句之前。“刚好之前”的意思是，在标签和迭代之间置入任何语句都不好。而在迭代之前设置标签的唯一理由是：我们希望在其中嵌套另一个迭代或者一个开关。这是由于break和continue关键词通常只中断当前循环，但若随同标签一起使用，它们就会中断循环，直到标签所在的地方。

	label1:
	for{
 		for{  
			break;  //(1)
			continue; //(2)
			continue label; //(3)
			continue label; //(4)
		}
	}
	(1)break中断内部迭代，回到外部迭代。
	(2)continue使执行点移回内部迭代的起始处。
	(3)continue label同时中断内部迭代及外部迭代，直接转到label处，随后，它实际上是继续迭代过程，但却从外部迭代开始。
	(4)break label也会中断所有迭代，回到label处，但并不从新进入循环，即实际上是完全中止了两个迭代。
	
	package test;

	public class CodeTemplate {

		public static void main(String[] args) {
			int i = 0;
			outer: for (; true;) {
				inner: for (; i < 10; i++) {
					System.out.println("i = " + i);
					if (i == 2) {
						System.out.println("continue");
						continue;
					}
					if (i == 3) {
						System.out.println("break");
						i++; 
						break;
					}
					if (i == 7) {
						System.out.println("continue outer");
						i++; 
						continue outer;
					}
					if (i == 8) {
						System.out.println("break outer");
						break outer;
					}
					for (int k = 0; k < 5; k++) {
						if (k == 3) {
							System.out.println("continue inner");
							continue inner;
						}
					}
				}
			}
		}
	}
	/*
	 * Output: 
	 * i = 0 
	 * continue inner 
	 * i = 1 
	 * continue inner 
	 * i = 2 
	 * continue 
	 * i = 3 
	 * break
	 * i = 4 
	 * continue inner 
	 * i = 5 
	 * continue inner 
	 * i = 6 
	 * continue inner 
	 * i = 7 
	 * continue outer 
	 * i = 8 
	 * break outer
	 */// :~

如CodeTemlplate中break语句中断了for循环，而在抵达for循环的末尾前，递增表达式i++是不会被执行的。由于break跳过了递增表达式，所以在 i==3 时要加入语句 i++ 对 i 直接进行递增操作，在 i==7 时 continue outer 语句会跳到循环顶部，所以这里也要直接对 i 进行递增操作。如果没有 break outer 语句，就没有办法从内部循环里跳出外部循环。这是由于 break 和continue 本身只能中断最内层的循环

同样的规则也适用于 while(如CodeTemplat1):

	class CodeTemplate1 {
		public static void main(String[] args) {
			int i = 0;
			outer: while (true) {
				System.out.println("Outer while loop");
				while (true) {
					i++;
					System.out.println("i = " + i);
					if (i == 1) {
						System.out.println("continue");
						continue;
					}
					if (i == 3) {
						System.out.println("continue outer");
						continue outer;
					}
					if (i == 5) {
						System.out.println("break");
						break;
					}
					if (i == 7) {
						System.out.println("break outer");
						break outer;
					}
				}
			}
		}
		/*
		 * Output: 
		 * Outer while loop
		 * i = 1
		 * continue 
		 * i = 2 
		 * i = 3 
		 * continue outer 
		 * Outer while loop 
		 * i = 4 
		 * i = 5 
		 * break 
		 * Outer while loop 
		 * i = 6 
		 * i = 7 
		 * break outer
		 */// :~
	}
	(1)一般的 continue 会退回最内层循环的开头（顶部），并继续执行。
	(2)带标签的 continue 会到达标签的位置，并重新进入紧接在那个标签后面的循环。
	(3)一般的 break 会中断并跳出当前循环。
	(4)带标签的 break 会中断并跳出标签所指的循环。
重点是：在 JAVA 中需要使用标签的唯一理由就是因为有循环嵌套存在，而且想从多层嵌套中 break 或 continue 。

## java -初始化与清理
* 对象可能不被垃圾回收。
* 垃圾回收并不等于"析构"(析构函数与构造函数相反，当对象结束其生命周期，如对象所在的函数已调用完毕时，系统自动执行析构函数。析构函数往往用来做“清理善后” 的工作（例如在建立对象时用new开辟了一片内存空间，delete会自动调用析构函数后释放内存）)。
* 垃圾回收只与内存有关：使用垃圾回收器的唯一原因是为了回收程序不再使用的内存。
<br>

	class Book{
	boolean checkedOut = false;
	Book(boolean checkOut){
		checkedOut = checkOut;
	}
	void checkIn(){
		checkedOut = false;
	}
	
	protected void finalize(){
		if(checkedOut){
			System.out.println("Error : checked out");
		}
		else{
			System.out.println("Success : checked in");
		}
	}
	
	
	public static void main(String[] args) {
		Book book = new Book(true);
		book.checkIn();
		new Book(true);
		System.gc();    //用于强制进行终结动作
	}
	}
在本例中所有Book对象在被当作垃圾会收前都应该被签入(check in)。

#### java垃圾回收
垃圾回收技术：

* 引用记数：每个对象都含有一个引用计数器，当有引用连接至对象时，引用计数加1。当引用离开作用域或被置为null时，引用计数减1。虽然管理引用记数的开销不大，但这项开销在整个程序生命周期中将持续发生。垃圾回收器会在含有全部对象的列表上遍历，当发现某个对象的引用计数为0时，就释放其占有的空间(但是，引用记数模式可能会出现"对象应该被回收，但引用计数却不能零"的情况)。对垃圾回收器来说，定位这样的交互自引用的对象组所需工作量极大。引用记数常用来说明垃圾回收收集的工作方式，但似乎从未被应用于任何一种java虚拟机实现中。
* "自适应"技术："自适应的、分代数记录的、停止-复制、标记-清扫"式垃圾回收器。
	
#### java虚拟机
java虚拟机附加技术-JIT("即时"编译器)：把程序全部或部分翻译成本地机器码，程序运行速度得到提升。当需要加载类时，编译器寻找其.class文件，将该类的字节码装入内存。两种方案供选择：

* JIT编译所有代码，有缺点因加载动作散落在整个程序的生命周期内，累计时间较长，并且会增加可执行代码的长度(字节码比本地机器码小很多)，降低程序速度。
* 惰性评估:即时编译器只在有必要的时候才编译代码，不执行的代码不会被JIT所编译。例如：Java Hotspot , 代码每次执行的时候都会做一些优化，所以执行的次数越多，速度就越快。


#### 静态数据的初始化
无论创建多少个对象，静态数据都只占有一份存储区域。static关键字不能应用于局部变量，只能作用于域。如果一个域是静态的基本类型域，且也没有对它进行初始化，那么它就会获得基本类型的标准初始值；如果它是一个对象引用，那么它的默认初始化值就是null。静态初始化只有在必要时刻才会进行，初始化后静态对象将不会再次被初始化。注：即使没有显式的使用static关键字，构造器实际上也是静态方法，new对象引用时自动设置为null。

静态块：当首次生成这个类的一个对象时，或者首次访问属于那个类的静态数据成员时该静态初始化动作执行且只执行一次。
