# java

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

如CodeTemlplate中break语句中断了for循环，而在抵达for循环的末尾前，递增表达式i++是不会被执行的。由于break跳过了递增表达式，所以在 i==3 时要加入语句 i++ 对 i 直接进行递增操作，在 i==7 时 continue outer 语句会跳到循环顶部，所以这里也要直接对 i 进行递增操作。如果没有 break outer 语句，就没有办法从内部循环里跳出外部循环。这是由于 break 和continue 本身只能中断最内层的循环

同样的规则也适用于 while(如CodeTemplat1):
	(1)一般的 continue 会退回最内层循环的开头（顶部），并继续执行。
	(2)带标签的 continue 会到达标签的位置，并重新进入紧接在那个标签后面的循环。
	(3)一般的 break 会中断并跳出当前循环。
	(4)带标签的 break 会中断并跳出标签所指的循环。
重点是：在 JAVA 中需要使用标签的唯一理由就是因为有循环嵌套存在，而且想从多层嵌套中 break 或 continue 。

