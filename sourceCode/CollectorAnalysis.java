package sourceCode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Collector源码分析
 * 	收集器核心
 * @author 张国荣
 *
 */
public class CollectorAnalysis {
	public static class Person{
		String name;
		int age;
		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}
		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}
	}
	public static void main(String[] args) {
		Person p1 = new Person("zhangsan", 20);
		Person p2 = new Person("lisi", 25);
		Person p3 = new Person("wangwu", 30);
		Person p4 = new Person("zhaoliu", 50);
		Person p5 = new Person("liuqi", 70);
		List<Person> ps = Arrays.asList(p1, p2, p3, p4, p5);
		
		//ps.stream().collect(Collectors.toList()).forEach(System.out::println);
		//流计数的两种方式
		System.out.println("count:" + ps.stream().collect(Collectors.counting()));
		System.out.println("count:" + ps.stream().count());
		
		/**
		 * collect：收集器，他的参数是Collector
		 * Collector是一个泛型接口<T,A,R>，他是一个可变的汇聚操作，将输入元素累积到一个可变的结果容器中；他会在所有元素都处理完毕后，将累积的结果转换为一个最终的表示（可选操作）；他支持串行和并行两种方式执行
		 * T：进行汇聚操作的输入元素的类型，流中的每一个元素的类型，如上面list中的Person
		 * A：汇聚操作可变容器的类型，实际上是中间操作结果类型，如ArrayList
		 * R：汇聚操作的结果类型
		 * Collectors本身是一个工厂，提供了许多常见的可变汇聚的实现，如toList，counting
		 * collector的四个组成方法：
		 * 1.Supplier<A> supplier()创建并返回一个新的可变的结果容器，故泛型为A，返回值为Supplier函数接口
		 * 2.BiConsumer<A,T> accumulator()将新的元素（流中的每一个元素）折叠（folds）到一个结果容器中（由supplier创建的），故返回值泛型为A，T
		 * 		BiConsumer接收两个参数，不返回值，第一个参数表示每一次操作中间结果的类型，第二个参数表示流中待处理的下一个元素的类型
		 * 3.BinaryOperator<A> combiner()将两个子结果容器合并成为一个，故泛型为A，和并行流紧密相关
		 * 		BinaryOperator继承自BiFunction，由于其中参数和返回值类型相同，故只接受一个类型的参数
		 * 4.Function<A,R> finisher()对容器执行一个可选的最终转换，将中间的累计类型转换成结果类型，故泛型为A，R
		 * 		Function输入一个参数获得一个返回值
		 * 	为了满足串行与并行操作结果的等价性，Collector函数需要满足两个条件：统一性（identity）和结合性（associativity）
		 * 	推论1：对于部分累积的结果a（某个线程执行分支所得到的结果）和空的容器合并结果不变，即 （supplier.get()提供的是一个空的结果容器）
		 * a 等价于 combiner.apply(a, supplier.get()),若a为list1，则有
		 * (List<String> list1, List<String> list2) -> {list1.addAll(list2); return list1;}
		 * 	推论2：分割计算和串行计算获得的结果等价，即以下两种运算结果r1和r2等价
		 *     A a1 = supplier.get();
		 *     accumulator.accept(a1, t1);
		 *     accumulator.accept(a1, t2);
		 *     R r1 = finisher.apply(a1);  // result without splitting，不切分（串行）
		 *
		 *     A a2 = supplier.get();
		 *     accumulator.accept(a2, t1);
		 *     A a3 = supplier.get();
		 *     accumulator.accept(a3, t2);
		 *     R r2 = finisher.apply(combiner.apply(a2, a3));  // result with splitting，切分（并行）
		 * collect收集的固定顺序：supplier-->accumulator-->(combiner-->)finisher		串行（并行）
		 * collect过程：
		 *	   R container = collector.supplier().get();
		 * 	   for (T t : data)
		 *   		collector.accumulator().accept(container, t);
		 * 	   return collector.finisher().apply(container);
		 */
	}
}
