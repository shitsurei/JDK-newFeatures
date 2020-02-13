package lambda;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author 张国荣 高阶函数：一个函数的参数或返回值也是函数
 *
 */
public class FunctionInterface {
	public static void main(String[] args) {
		/**
		 * java8中大多新引入的函数式接口位于java.util.function包中 包括Consumer和Function
		 * 其中Function<T,R>这一函数式接口,T为输入类型,R为输出类型,抽象方法为apply
		 */
		// 注：toUpperCase方法虽然没有参数但是执行该方法时必须通过某个对象调用，这个对象视为该方法的参数
		Function<String, String> function = String::toUpperCase;
		System.out.println(function.getClass());// class lambda.FunctionInterface$$Lambda$1/471910020
		
		//Function接口
		FunctionInterface fi = new FunctionInterface();
		// 行为作为参数
		System.out.println(fi.compute(1, value -> 2 * value));// 2
		System.out.println(fi.compute(2, value -> 5 + value));// 7
		// 行为作为返回值
		System.out.println(fi.choose(true).apply("111"));//111aaa
		System.out.println(fi.choose(false).apply("111"));//111bbb
		//compose	先平方再*3		12
		System.out.println(fi.compute2(2, value -> 3 * value, value -> value * value));
		//andThen	先*3在平方		36
		System.out.println(fi.compute3(2, value -> 3 * value, value -> value * value));
		
		//BiFunction接口
		//定义整型的加减乘除
		System.out.println(fi.compute4(2, 3, (value1, value2) -> value1 + value2));//5
		System.out.println(fi.compute4(2, 3, (value1, value2) -> value1 - value2));//-1
		System.out.println(fi.compute4(2, 3, (value1, value2) -> value1 * value2));//6
		System.out.println(fi.compute4(2, 3, (value1, value2) -> value1 / value2));//0
		//andThen	先相加结果在平方	36
		System.out.println(fi.compute5(2, 3, (value1, value2) -> value1 * value2, value -> value * value));
	}

	/**
	 * 传递行为 
	 * 节省了面向对象编程模式中封装行为的成本，可以在使用时将操作传递进来
	 * @param a
	 * @param function 要对a进行的操作
	 * @return
	 */
	public int compute(int a, Function<Integer, Integer> function) {
		return function.apply(a);
	}
	/**
	 * 
	 * @param ch
	 * @return
	 */
	public Function<String, String> choose(boolean ch) {
		return ch ? value -> value + "aaa" : value -> value + "bbb";
	}
	
	/**
	 * 	以下两个方法为接口提供的默认方法（default method），可以用于串联多个函数式接口，并且指定他们之间的先后顺序
	 * compose方法先对参数a应用function2的apply方法，再将计算的结果b作为function1的参数执行apply方法
	 * @param a
	 * @param function1
	 * @param function2
	 * @return
	 */
	public int compute2(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
		return function1.compose(function2).apply(a);
	}
	/**
	 * andThen方法先对参数a应用function1的apply方法，再将计算的结果b作为function2的参数执行apply方法
	 * @param a
	 * @param function1
	 * @param function2
	 * @return
	 */
	public int compute3(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
		return function1.andThen(function2).apply(a);
	}
	/**
	 * BiFunction这一函数式接口可以用于接受两个输入参数，对其进行操作，返回一个结果
	 * @param a
	 * @param b
	 * @param biFunction
	 * @return
	 */
	public int compute4(int a, int b, BiFunction<Integer, Integer, Integer> biFunction) {
		return biFunction.apply(a, b);
	}
	/**
	 * 对于BiFunction来说只有andThen方法，且该方法的第二个参数是Function类型
	 * 因为BiFunction需要两个输入，一个输出，故该方法执行的结果可以作为一个Function接口的输入进行运算，其他情况都缺少相应的参数
	 * @param a
	 * @param b
	 * @param biFunction
	 * @param function
	 * @return
	 */
	public int compute5(int a, int b, BiFunction<Integer, Integer, Integer> biFunction, Function<Integer, Integer> function) {
		return biFunction.andThen(function).apply(a, b);
	}
}
