/*
 * SingleCircleLinkedList
 * 单向环形链表
 @author Sirius
 */
package linklist;

import java.util.ArrayList;

class SingleMember<T> {
    public int no;
    public T val;
    public SingleMember<T> next;

    public SingleMember(int no, T val, SingleMember<T> next) {
        this.no = no;
        this.val = val;
        this.next = next;
    }

    public SingleMember(int no, T val) {
        this.no = no;
        this.val = val;
        this.next = this;
    }

    @Override
    public String toString() {
        String nextStr;
        if (next == null) {
            nextStr = "null";
        } else {
            nextStr = String.valueOf(next.hashCode());
        }
        return "hash=" + this.hashCode() + "\tno=" + this.no + "\tval:" + val + "\t" +
                "next:" + nextStr;
    }
}

public class SingleCircleLinkedList<T> {
    private SingleMember<T> head;
    int count = 0;
    ArrayList<Integer> noArray = new ArrayList<>();

    public SingleCircleLinkedList(){
        this.head = new SingleMember<>(0, null);
    }
    // 指定位置添加
    public void addAtLocation(int no, T val, boolean before) {
        if (no == 0 && before) {
            this.addAtLast(val);
            return;
        } else if (no == 0 && !before) {
            this.addToHead(val);
            return;
        }
        count++;
        noArray.add(count);
        SingleMember<T> temp = head;
        SingleMember<T> newMember = new SingleMember<>(count, val, null);
        while (temp != null) {
            if (before) {
                if (temp.next.no == no) {
                    newMember.next = temp.next;
                    temp.next = newMember;
                    break;
                }
            } else {
                if (temp.no == no) {
                    newMember.next = temp.next;
                    temp.next = newMember;
                    break;
                }
            }
            if (temp.next == head) {
                break;
            }
            temp = temp.next;
        }
    }

    // 在末尾添加
    public void addAtLast(T val) {
        count++;
        SingleMember<T> temp = head;
        SingleMember<T> newMember = new SingleMember<>(count, val, null);
        while (temp != null) {
            if (temp.next == head) {
                temp.next = newMember;
                newMember.next = head;
                break;
            }
            temp = temp.next;
        }
    }

    // 在头部节点后添加
    public void addToHead(T val) {
        count++;
        SingleMember<T> newMember = new SingleMember<>(count, val, null);
        newMember.next = head.next;
        head.next = newMember;
    }

    // 标准输出
    public void print() {
        SingleMember<T> temp = head;
        while (temp != null && temp.next != head) {
            System.out.println(temp);
            if (temp.next == head) {
                break;
            }
            temp = temp.next;
        }
    }
    // 根据no查询
    public T getByNo(int no){
        SingleMember<T> temp = head;
        while (temp != null){
            if (temp.no == no){
                return temp.val;
            }
            temp = temp.next;
        }
        return null;
    }
    // 根据值查询对应自增索引
    public int getIndexOf(T val){
        SingleMember<T> temp = head;
        while (temp != null){
            if (temp.val == val){
                return temp.no;
            }
            temp = temp.next;
            if (temp == head){
                break;
            }
        }
        return -1;
    }
    // 删除指定no的节点
    public void deleteByNo(int no){
        SingleMember<T> temp = head;
        while (temp != null){
            if (temp.next.no == no){
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
            if (temp == head){
                break;
            }
        }
    }
    // 清空链表
    public void clear(){
        this.head = new SingleMember<>(0, null);
    }
}
