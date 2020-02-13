package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateInterface {
	public static void main(String[] args) {
		/**
		 * Predicate函数接口：根据给定的一个条件判断true或false并返回
		 * stream中得到大量应用
		 */
		Predicate<String> predicate = p -> p.length() > 5;
		System.out.println(predicate.test("abcde"));//true
		System.out.println(predicate.test("abcdef"));//false
		System.out.println("---------------------");
		List<Integer> list = Arrays.asList(1,2,4,6,7,9);
		//不进行判断通过所有元素
		//List<Integer> all = getNumberByRule(list, num -> true);
		
		//不进行判断不通过所有元素
		//List<Integer> all = getNumberByRule(list, num -> false);
		
		//筛选集合中的奇数
		List<Integer> odd = getNumberByRule(list, num -> num % 2 == 1);
		odd.forEach(System.out::println);
		
		System.out.println("---------------------");
		
		//筛选集合中的质数
		List<Integer> prime = getNumberByRule(list, num -> {
			boolean isPrime = true;
			for (int i = 2; i < num; i++) {
				if(num % i == 0) {
					isPrime = false;
					break;
				}
			}
			return isPrime;
		});
		prime.forEach(System.out::println);
		
		System.out.println("---------------------");
		
		//同时满足两个条件
		List<Integer> and = andPredicate(list, num -> num > 3, num -> num < 8);
		and.forEach(System.out::println);
		//满足两个条件中的任意一个
		List<Integer> or = orPredicate(list, num -> num < 3, num -> num > 8);
		or.forEach(System.out::println);
		//不满足给定条件的集合
		List<Integer> not = notPredicate(list, num -> num > 5);
		not.forEach(System.out::println);
		//可以多个与或非之间进行串联
	}
	/**
	 * 根据传入的规则对集合中的数进行过滤
	 * @param numbers
	 * @param rule
	 * @return
	 */
	public static List<Integer> getNumberByRule(List<Integer> numbers, Predicate<Integer> rule){
		return numbers.stream().filter(rule).collect(Collectors.toList());
	}
	
	/**
	 * Predicate接口中还定义三个默认方法：与、或、非；一个静态方法isEqual，用于判断两个参数是否是相同的（本质上是Object类的equal方法）
	 */
	public static List<Integer> andPredicate(List<Integer> numbers, Predicate<Integer> rule1, Predicate<Integer> rule2){
		return numbers.stream().filter(rule1.and(rule2)).collect(Collectors.toList());
	}
	public static List<Integer> orPredicate(List<Integer> numbers, Predicate<Integer> rule1, Predicate<Integer> rule2){
		return numbers.stream().filter(rule1.or(rule2)).collect(Collectors.toList());
	}
	public static List<Integer> notPredicate(List<Integer> numbers, Predicate<Integer> rule){
		return numbers.stream().filter(rule.negate()).collect(Collectors.toList());
	}
}
