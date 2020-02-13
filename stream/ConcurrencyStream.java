package stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 	并发流
 * @author 张国荣
 *
 */
public class ConcurrencyStream {
	public static void main(String[] args) {
		//案例1：对大数量的UUID进行排序，比较并行流和串行流的时间
		List<String> uuids = new ArrayList<>(500000);
		for (int i = 0; i < 500000; i++) {
			uuids.add(UUID.randomUUID().toString());
		}
		System.out.println("开始排序");
		long start = System.nanoTime();
		//串行流
		//uuids.stream().sorted().count();//652
		//并行流:fork join 将大任务拆分成小任务
		uuids.parallelStream().sorted().count();//459
		long end = System.nanoTime();
		long millis = TimeUnit.NANOSECONDS.toMillis(end - start);
		System.out.println("排序耗时（毫秒）：" + millis);
		
		/**
		 * 流这个容器存放的是对集合的操作
		 * 每一个集合中的元素都会依次执行这些操作，且元素之间的操作过程是异步进行的
		 * 所有的操作都是短路运算的，即某个元素在操作过程中一旦及早求值的结果确定，那么之后的操作就不会再被执行
		 */
		//案例2：输出集合中第一个长度为5的字符串
		List<String> strs = Arrays.asList("hello1","world","hello_world");
		//strs.stream().filter(str -> str.length()==5).findFirst().ifPresent(System.out::println);
		strs.stream().mapToInt(item -> {
			int length = item.length();
			System.out.println(item);//只输出hello1和world
			return length;
		}).filter(l -> l == 5).findFirst().ifPresent(System.out::println);
	}
}
