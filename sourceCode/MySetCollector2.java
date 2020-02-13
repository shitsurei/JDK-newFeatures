package sourceCode;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author 张国荣 
 * 	自定义收集器：将set结果封装到map中 
 * 	输入：Set<String> 
 * 	输出：Map<String,String>
 */
public class MySetCollector2<T> implements Collector<T, Set<T>, Map<T, T>> {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("hello", "world", "jdk8", "hello");
		Map<String, String> map = list.parallelStream().collect(new MySetCollector2<>());
		//也可以链式串行并行化，由最后一个决定（实质是修改一个boolean值）
		//list.stream().parallel().sequential().parallel().sequential()……
		System.out.println(map);
		//输出当前系统核心数
		System.out.println(Runtime.getRuntime().availableProcessors());
	}

	@Override
	public Supplier<Set<T>> supplier() {
		//串行流只会生成一个
		//并行流会生成多个（个数最多生成CPU核心数*2个，CPU超线程技术，一个模拟成两个）
		return HashSet::new;
	}

	@Override
	public BiConsumer<Set<T>, T> accumulator() {
		return (set, item) -> {
			/** 
			 * 	测试：
			 * 	串行流结果：
			 * accumulator---main
			 * accumulator---main
			 * accumulator---main
			 * accumulator---main
			 * 	并行流（分支合并框架ForkJoin）结果：
			 * accumulator---ForkJoinPool.commonPool-worker-1
			 * accumulator---ForkJoinPool.commonPool-worker-3
			 * accumulator---main
			 * accumulator---ForkJoinPool.commonPool-worker-2
			 */
			System.out.println("accumulator---"+ set + "---" + Thread.currentThread().getName());
			set.add(item);
		};
		//return Set::add;
	}

	@Override
	public BinaryOperator<Set<T>> combiner() {
		return (set1, set2) -> {
			System.out.println("set1---"+ set1);
			System.out.println("set2---"+ set2);
			set1.addAll(set2);
			return set1;
		};
	}

	@Override
	public Function<Set<T>, Map<T, T>> finisher() {
		return set -> {
			 Map<T, T> map = new TreeMap<>();
			 set.forEach(item -> map.put(item, item));
			 return map;
		};
	}

	@Override
	public Set<Characteristics> characteristics() {
		//由于需要执行完成器方法，因此不能加IDENTITY_FINISH属性
		return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED, Characteristics.CONCURRENT));
		/**
		 * 	在并行流中，加入CONCURRENT属性，同时打印set时，程序可能会出现 java.util.ConcurrentModificationException异常（并行修改异常）
		 * 	该异常产生的原因是，集合被一个线程修改时又被另一个线程遍历
		 * accumulator---[]---main
		 * accumulator---[jdk8]---main
		 * accumulator---[jdk8, hello]---main
		 * 	正常在并行流parallelStream中，多个线程会操作多个结果容器，每个线程拥有各自的结果容器set，遍历和修改操作是串行执行的，不会发生修改和遍历的冲突
		 * 	而加上CONCURRENT字段后，只有一个结果容器会被操作，set在被一个线程遍历时可能会被其他线程并发修改，产生冲突
		 * 
		 * 	这一点可以通过combiner方法来证明：
		 * 1.如果不加CONCURRENT属性，并行流会产生多个结果容器，最终由combiner来合并
		 * 	结果输出：
		 * set1---[hello]
		 * set1---[jdk8]
		 * set2---[world]
		 * set2---[hello]
		 * set1---[world, hello]
		 * set2---[jdk8, hello]
		 * 2.反之不加该属性，只会有一个结果容器，就不会执行combiner方法，结果不输出
		 */
	}
}
