/*
 * ArrayStack
 * 用数组模拟栈结构
 @author Sirius
 */
package stack;

public class ArrayStack<T> {
    private final static int MAXSIZE = 20;
    private T[] stack;
    private int top = -1;

    public ArrayStack() {
        stack = (T[]) (new Object[MAXSIZE]);
    }

    // 栈满
    public boolean isFull() {
        return top == MAXSIZE - 1;
    }

    // 栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈
    public void push(T val) {
        if (!isFull()) {
            top++;
            stack[top] = val;
        }else {
            throw new RuntimeException("栈满");
        }
    }

    // 出栈
    public T pop() {
        if (!isEmpty()) {
            T val = stack[top];
            top--;
            return val;
        } else {
            throw new RuntimeException("栈空");
        }
    }

    public void print() {
        for (int i = top; i >= 0; i--) {
            System.out.println(stack[i]);
        }
    }
}