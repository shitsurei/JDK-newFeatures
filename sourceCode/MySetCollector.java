package sourceCode;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author 张国荣
 * 	自定义收集器的实现：对Set进行收集
 *
 */
public class MySetCollector<T> implements Collector<T, Set<T>, Set<T>>{
	public static void main(String[] args) {
		List<String> list = Arrays.asList("hello","hello","world","jdk8");
		Set<String> set = list.stream().collect(new MySetCollector<>());
		System.out.println(set);
		/**
		 * 输出结果分析：
		 * supplier used
		 * accumulator used
		 * combiner used
		 * 以上三个方法在makeRef中依次被调用
		 * characteristics used
		 * 第一次在getOpFlags中被调用，判断内部元素是否无序
		 * characteristics used
		 * 第二次在collect中被调用，判断是否需要执行finisher方法
		 * [world, jdk8, hello]结果是无序，去重的
		 */
	}

	@Override
	public Supplier<Set<T>> supplier() {
		// 提供一个空的容器供accumulator后续的调用
		System.out.println("supplier used");
		// lambda表达式
		//return () -> new HashSet<>();
		// 方法引用
		return HashSet::new;
	}

	@Override
	public BiConsumer<Set<T>, T> accumulator() {
		// 累加器，接受两个参数（结果容器，流中遍历的下一个元素），不返回值
		System.out.println("accumulator used");
		// lambda表达式
		//return (set, item) -> set.add(item);
		// 方法引用
		return Set<T>::add;
		//编译不能通过，因为supplier返回的类型是Set，accumulator并不知道Set的具体实现（可能是TreeSet），这里强行指定具体实现可能会与supplier发生冲突
		//return HashSet<T>::add;	
	}

	@Override
	public BinaryOperator<Set<T>> combiner() {
		// 将并行流多个线程执行的部分结果合并起来，返回合并后的中间容器
		System.out.println("combiner used");
		// lambda表达式
		return (set1, set2) -> {
			set1.addAll(set2);
			return set1;
		};
	}

	@Override
	public Function<Set<T>, Set<T>> finisher() {
		// 完成器，将所有线程的计算结果合并到一起，返回最终的结果类型
		//由于该方法根本不会被调用，可以直接在方法中抛出异常
		throw new UnsupportedOperationException("finisher used");
		
		// lambda表达式（当前程序的中间容器和返回值类型相同）
		//return set -> set;
		// 方法调用
		//return Function.identity();
		
	}

	@Override
	public Set<Characteristics> characteristics() {
		/**
		 *  characteristics用于描述收集器的相关特性，返回一个包含特性的set，枚举类中包含三个值：
		 *  CONCURRENT			收集器是并发的，结果容器可以支持累加器并发的调用（和并行流概念不同，并行流有多个结果容器），要求：
		 *  1.	结果容器是线程安全的
		 *  2.	只能用在无序的数据源上，即和unordered配合使用
		 *  UNORDERED			收集操作无法保证集合中的顺序
		 *  IDENTITY_FINISH		表示中间容器和返回值的类型相同，不用进行转换（调用collector的finisher方法），要求：
		 *  1.	A和R类型的不检查转换必须是成功的
		 */
		System.out.println("characteristics used");
		return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH,Characteristics.UNORDERED));
	}
}
