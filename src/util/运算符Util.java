package util;

public class 运算符Util {
	public static void main(String[] args) {

		// define();
		// 应用实例();
		System.out.println(-0100);
		System.out.println(-0100 << 1);
	}

	private static void 应用实例() {
		与或运算();
	}

	private static void 与或运算() {

		// 异或的性质
		// 交换律：a ^ b = b ^ a
		// 结合律：a ^ b ^ c = a ^ (b ^ c) = (a ^ b) ^ c
		// d = a ^ b ^ c 可以推出 a = d ^ b ^ c
		// 自反性：a ^ b ^ a = b

		// 利用异或运算，也可以将两个数交换，例如：
		int a = 1, b = 2;

		System.out.println("Before switch:    a:" + a + "\tb:" + b);
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		System.out.println("After switch:    a:" + a + "\tb:" + b);

		// 挑选意外数字
		// 1-1000放在含有1001个元素的数组中，只有唯一的一个元素值重复，其它均只出现一次
		int[] array = new int[1001];
		for (int i = 0; i < 1000; i++) {
			array[i] = i;
		}
		array[1000] = 166;
		int ar = 0;
		for (int i = 0; i < 1000; i++) {
			ar ^= i;
		}
		for (int i = 0; i < array.length; i++) {
			ar ^= array[i];
		}
		System.out.println("意外数字:" + ar);

		// 挑选非重复数字
		// 1-1000放在含有1001个元素的数组中，只有唯一的一个元素值重复
		int ar2 = 0;
		int[] array2 = new int[] { 1, 2, 2, 3, 3, 4, 4 };
		for (int i = 0; i < array2.length; i++) {
			ar2 ^= array2[i];
		}
		System.out.println("非重复数字:" + ar2);

	}

	private static void define() {
		int a = 310011;
		int b = 11010100;
		// ＆ 如果相对应位都是1，则结果为1，否则为0
		System.out.println(a & b);
		// | 如果相对应位都是0，则结果为0，否则为1
		System.out.println(a | b);
		// ^ 如果相对应位值相同，则结果为0，否则为1
		System.out.println(a ^ b);
		// 〜 按位补运算符翻转操作数的每一位，即0变成1，1变成0。
		System.out.println(a = ~a);
		// << 按位左移运算符。左操作数按位左移右操作数指定的位数。
		System.out.println(a <<= 1);
		// >> 按位右移运算符。左操作数按位右移右操作数指定的位数。
		System.out.println(a >>= 1);
		// >>> 按位右移补零操作符。左操作数的值按右操作数指定的位数右移，移动得到的空位以零填充。
		System.out.println(a >>>= 1);
	}
}
