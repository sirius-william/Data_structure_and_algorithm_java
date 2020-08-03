/*
 * DoubleLinkedList
 * 双向链表，该链表不支持中间插入
 * 特点：每个节点有类似于MySQL自增主键的标识值（no值），该值不可变
 * 技巧：改哪个节点，就把哪个节点先拿出来进行操作
 @author Sirius
 */
package linklist;

import java.util.ArrayList;

class MemberDouble<T> {
    public int no;
    public MemberDouble<T> pre;
    public T val;
    public MemberDouble<T> next;

    public MemberDouble(int no, MemberDouble<T> pre, T val, MemberDouble<T> next) {
        this.no = no;
        this.pre = pre;
        this.val = val;
        this.next = next;
    }

    public MemberDouble() {

    }

    @Override
    public String toString() {
        String preHash = null;
        String nextHash = null;
        if (this.pre != null) {
            preHash = String.valueOf(pre.hashCode());
        }
        if (this.next != null) {
            nextHash = String.valueOf(next.hashCode());
        }
        return "no=" + no +
                "\npre=" + preHash +
                "\nval=" + val +
                "\nnext=" + nextHash;
    }
}

public class DoubleLinkedList<T> {
    // 头部
    private MemberDouble<T> head = new MemberDouble<>(0, null, null, null);
    // 尾部
    private MemberDouble<T> foot = new MemberDouble<>(-1, null, null, null);
    // 一个自增变量，类似MySQL的自增主键
    private int count = 0;
    // 该数组用于储存no值，便于获取链表长度，以及链表所有的no。
    private ArrayList<Integer> noArray = new ArrayList<>();

    public DoubleLinkedList() {
        this.foot.pre = this.head;
        this.head.next = this.foot;
    }

    // 打印通用方法
    private void printDefault(int direction) {
        // 0为正向，1为反向
        MemberDouble<T> temp;
        if (direction == 0) {
            temp = this.head;
        } else {
            temp = this.foot;
        }
        while (temp != null) {
            if (temp.no != 0 && temp.no != -1) {
                System.out.println(temp.val);
            }
            if (direction == 0) {
                temp = temp.next;
            } else {
                temp = temp.pre;
            }
        }
    }

    // 添加元素通用方法
    private void addDefault(T val, int method) {
        // 0为在头部添加，1位在末尾添加元素
        count++;
        // 把自增count添加到noArray中
        while (true) {
            if (noArray.contains(count)) {
                count++;
            } else {
                noArray.add(count);
                break;
            }
        }
        MemberDouble<T> newMember = new MemberDouble<>();
        newMember.val = val;
        newMember.no = count;
        if (method == 1) {
            newMember.next = this.foot;
        } else {
            newMember.pre = this.head;
        }
        // 设置新增节点的pre为原最后一个节点，即foot的pre
        // 根据技巧，因为改的是最后一个节点，那就先把最后一个节点进行赋值给别人。可以是直接赋值，也可以是赋值给中间变量。
        if (method == 1) {
            newMember.pre = this.foot.pre;
        } else {
            newMember.next = this.head.next;
        }
        // 最后一个节点的next改为新增
        if (method == 1) {
            this.foot.pre.next = newMember;
        } else {
            // 原来head的下一个节点的pre设置为新节点
            this.head.next.pre = newMember;
        }
        // 设置新增节点为最后一个节点
        if (method == 1) {
            this.foot.pre = newMember;
        } else {
            this.head.next = newMember;
        }
    }

    // 查询
    public T get(int no) {
        // 判断编号是否存在
        if (!noArray.contains(no)) {
            System.out.println("不存在这个编号的节点");
            return null;
        }
        // 查询索引位置
        int location = noArray.indexOf(no);
        boolean fromHead;
        MemberDouble<T> temp;
        if (location <= ((float) noArray.size()) / 2) {
            fromHead = true;
            temp = head;
        } else {
            fromHead = false;
            temp = foot;
        }
        // 循环遍历
        while (temp != null) {
            if (temp.no == no) {
                return temp.val;
            }
            temp = fromHead ? temp.next : temp.pre;
        }
        return null;
    }

    // 删除节点
    public void delete(int n) {
        if (n == noArray.get(noArray.size() - 1)) {
            // 如果删除目标是最后一个节点，则直接调用删除最后节点的方法
            deleteLast();
            return;
        } else if (n == 1) {
            // 如果删除目标是第一个节点，则调用删除第一个节点的方法
            deleteFirst();
        }
        MemberDouble<T> temp;
        boolean fromHead = false;
        // 根据no值判断一下要删除的位置在链表的前半段还是后半段，来选择是从头开始遍历还是从末尾开始遍历
        int location = noArray.indexOf(n);
        if (n == -1) {
            System.out.println("没找到这个索引的元素");
            return;
        } else {
            // 查看索引在noArray内是在前半段还是在后半段，前半段则从链表头开始遍历，后半段则从链表结尾开始往前遍历
            if (location <= ((float) noArray.size()) / 2) {
                temp = head;
                fromHead = true;
            } else {
                temp = foot;
            }
        }
        while (temp != null) {
            if (temp.no == n) {
                temp.pre.next = temp.next;
                temp.next.pre = temp.pre;
                break;
            }
            if (fromHead) {
                System.out.println("从链表头遍历");
                temp = temp.next;
            } else {
                System.out.println("从链表结尾遍历");
                temp = temp.pre;
            }
        }
        // 删除noArray中存储的自增count值
        noArray.remove((Integer) n);
    }

    // 通过值来删除，效率低，可通过指定方向解决
    public void delete(T val, String direction) {
        MemberDouble<T> temp;
        if ("head".equals(direction)) {
            temp = head;
        } else {
            temp = foot;
        }
        while (temp != null) {
            temp.pre.next = temp.next;
            temp.next.pre = temp.pre;
            if ("head".equals(direction)) {
                temp = temp.next;
            } else {
                temp = temp.pre;
            }
        }
    }

    // 默认删除方式：从头部开始遍历
    public void delete(T val) {
        this.delete(val, "head");
    }

    // 删除最后一个节点
    public void deleteLast() {
        if (this.foot.pre == this.head) {
            System.out.println("空链表无法删除");
            return;
        }
        this.foot.pre.pre.next = this.foot;
        this.foot.pre = this.foot.pre.pre;
        noArray.remove(noArray.size() - 1);
        System.out.println("删除最后一个节点");

    }

    // 删除第一个节点
    public void deleteFirst() {
        if (this.head.next == this.foot) {
            System.out.println("空链表无法删除");
            return;
        }
        this.head.next.next.pre = this.head;
        this.head.next = this.head.next.next;
        noArray.remove(0);
    }

    // 返回链表长度
    public int size() {
        return noArray.size();
    }

    // 头部添加
    public void addToHead(T val) {
        this.addDefault(val, 0);
    }

    // 末尾添加
    public void addToFoot(T val) {
        this.addDefault(val, 1);
    }

    // 正向打印
    public void print() {
        this.printDefault(0);
    }

    // 反向打印
    public void printReverse() {
        this.printDefault(1);
    }
}
