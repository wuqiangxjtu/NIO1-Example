package cn.ryanwu.nio1;

import java.nio.CharBuffer;

public class BufferExample {
	public static void main(String[] args) {
		BufferExample example = new BufferExample();
		
		//新建
		System.out.println("----------新建：------------");
		char [] myArray = { 'H', 'e', 'l', 'l', 'o' };
		CharBuffer charbuffer = CharBuffer.wrap(myArray);
		example.printCharBufferProperties(charbuffer);
		myArray[4] = 'f';
		System.out.println(charbuffer.get(4)); //myArray的变化会影响到buffer
		charbuffer.put(0, 't');
		System.out.println(myArray[0]);
		
		
		CharBuffer charBuffer1 = CharBuffer.allocate(20);
		System.out.println("----------初始化：------------");
		System.out.println("capacity: " + charBuffer1.capacity());
		System.out.println("limit: " + charBuffer1.limit());
		System.out.println("position: " + charBuffer1.position());

		System.out.println("-----------写入1：--------------");
		CharBuffer charBuffer2 = CharBuffer.allocate(4);
		
		//写入单个字符
		charBuffer2.put('H');
		System.out.println("position: " + charBuffer2.position());
		
		//写入数组
		char[] a1 = { 'e', 'l', 'l' };
		charBuffer2.put(a1);
		System.out.println("position: " + charBuffer2.position());
		

		System.out.println("-------------读取---------------");
		CharBuffer cb1 = example.createHelloWorldCharBufferWith20Limit();
		example.printCharBufferProperties(cb1);
		//翻转
		cb1.flip();
		example.printCharBufferProperties(cb1);
		System.out.println(cb1.get(3));
		
		//get 会 increments index
		System.out.println(cb1.get());
		System.out.println(cb1.get());
		
		//超过limit Exception in thread "main" java.lang.IndexOutOfBoundsException
		//System.out.println(cb1.get(5));
		
		//put超过limit，Exception in thread "main" java.nio.BufferOverflowException
		char[] a2 = {'a','b','c','d'};
//		cb1.put(a2);
//		example.printCharBufferProperties(cb1);
		
		//释放
		System.out.println("---------------释放--------------------");
		CharBuffer cb3 = example.createHelloWorldCharBufferWith20Limit();
		example.printCharBufferProperties(cb3);
		cb3.clear();
		example.printCharBufferProperties(cb3);
		
		//压缩：主要用在一次读取不完
		System.out.println("---------------压缩--------------------");
		CharBuffer cb4 = CharBuffer.allocate(6);
		char[] a3 = {'h','e','l','l','o','w'};
		char[] a4 = {'o','r','l','d'};
		cb4.put(a3);
		example.printCharBufferProperties(cb4);
		cb4.flip();
		String s4 = "";
		for(int i = 0; i < 5; i++) {
			s4 = s4 + cb4.get();
		}
		System.out.println("s4:" + s4);
		example.printCharBufferProperties(cb4);
		cb4.compact();
		example.printCharBufferProperties(cb4);
		cb4.put(a4);
		example.printCharBufferProperties(cb4);
		cb4.flip();
		while(cb4.hasRemaining()) {
			s4 = s4 + cb4.get();
		}
		System.out.println("s4:" + s4);
		
		//标记
		CharBuffer cb5 = example.createHelloWorldCharBufferWith20Limit();
		example.printCharBufferProperties(cb5);
		cb5.position(2).mark().position(4);
		example.printCharBufferProperties(cb5);
		cb5.reset();
		example.printCharBufferProperties(cb5);
		
		//put后，position的位置没有数据，直接mark后，reset会抛异常
		CharBuffer cb6 = example.createHelloWorldCharBufferWith20Limit();
		example.printCharBufferProperties(cb6);
		cb6.mark();
		cb6.put('w');
		example.printCharBufferProperties(cb6);
		cb6.reset();
		example.printCharBufferProperties(cb6);
		
		
		
	}

	public CharBuffer createHelloWorldCharBufferWith20Limit() {
		CharBuffer buffer = CharBuffer.allocate(20);
		char[] a = { 'H', 'e', 'l', 'l', 'o' };
		buffer.put(a);
		return buffer;
	}

	public void printCharBufferProperties(CharBuffer buffer) {
		System.out.println("Position: " + buffer.position() + ", limit: "
				+ buffer.limit() + ", capacity: " + buffer.capacity());
	}
}
