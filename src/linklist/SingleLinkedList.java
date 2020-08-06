/*
 * LinkedList
 * 链表(无序）
 @author Sirius
 */
package linklist;


import java.util.Stack;

class Member<T> {
    public int no;
    public T object;
    public Member<T> next;

    public Member(int no, T object, Member<T> next) {
        this.object = object;
        this.no = no;
        this.next = next;
    }

    @Override
    public String toString() {
        String nextStr;
        if (next == null) {
            nextStr = "null";
        } else {
            nextStr = String.valueOf(next.hashCode());
        }
        return "hash=" + this.hashCode() + "\nno=" + this.no + "\nObject:" + object + "\n" +
                "next:" + nextStr + "\n";
    }
}

/*
 * 创建头结点
 * */
public class SingleLinkedList<T> {
    // 初始化头结点
    private Member<T> head = new Member<T>(0, null, null);
    private Member<T> last = head;
    private int count = 0;

    public void add(T object) {
        count++;
        Member<T> member = new Member<>(count, object, null);
/*      <方式一>较慢，因为要遍历
        Member<T> temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = member;
        */
        // 方式二：使用定义的最后一个节点
        this.last.next = member;
        this.last = member;
    }

    public void getAll() {
        getAll(this.head);

    }

    public void getAll(Member<T> input) {
        if (input.next == null) {
            return;
        }
        Member<T> member = input;
        while (member != null) {
            if (member.no != 0) {
                System.out.println(member);
            }
            member = member.next;
        }
    }

    // 指定元素位置之后插入数据
    public void addBehind(T object, T position) {
        Member<T> temp = head;
        while (temp.next != null) {
            // 找到目标位置时
            if (temp.object == position) {
                // 获取插入前下一个元素的位置，用于赋值给新的元素的next属性
                Member<T> next = temp.next;
                temp.next = new Member<>(1, object, next);
                break;
            }
            temp = temp.next;
        }
    }

    public void addByNo(int n, T val) {
        boolean ifExist = false;
        Member<T> temp = this.head;
        // 判断节点No值是否存在
        while (true) {
            if (temp.next != null) {
                if (temp.next.no == n) {
                    ifExist = true;
                    break;
                }
                temp = temp.next;
            } else {
                break;
            }
        }
        temp = head;
        if (!ifExist) {
            if (n > this.last.no) {
                Member<T> member = new Member<>(n, val, null);
                this.last.next = member;
                this.last = member;
                return;
            }
            while (true) {
                // 在被比较的节点前一个位置进行判断，判断下一个节点的no是否大于输入的no，即temp.next.no是否大于n
                if (n < temp.next.no) {
                    Member<T> nextMember = temp.next;
                    temp.next = new Member<>(n, val, nextMember);
                    break;
                }
                temp = temp.next;
            }
        } else {
            System.out.println("已经存在" + n + "这个No了");
        }
    }

    // 根据no修改
    public void change(int n, T newObject) {
        Member<T> temp = head;
        if (n == 0) {
            System.out.println("不能修改根节点");
            return;
        }
        while (temp != null) {
            if (temp.no == n) {
                temp.object = newObject;
                break;
            }
            temp = temp.next;
        }
        System.out.println("不存在这个No值");
    }

    // 根据No删除节点
    public void delete(int n) {
        Member<T> temp = head;
        while (temp != null) {
            if (temp.next.no == n) {
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }
    }

    public int size() {
        Member<T> temp = head;
        int size = 0;
        while (temp.next != null) {
            size++;
            temp = temp.next;
        }
        return size;
    }

    // 查找倒数第k个元素
    public Member<T> getFromBehind(int k) {
        Member<T> res = head;
        int front = this.size() - k + 1;
        if (front < 1 || k == 0 || head.next == null) {
            return null;
        }
        int c = 1;
        while (c <= front) {
            res = res.next;
            c++;
        }
        return res;
    }

    // 翻转（腾讯）
    public Member<T> reverse() {
        Member<T> temp = head;
        // 定义一个新的链表
        Member<T> newTemp = new Member<>(0, null, null);
        while (temp != null) {
//            // 这种方法会改变原表内节点的哈希值内存地址，因为新创建对象了
//            // 取出新的链表中头部的下一个元素（即第一个元素）
//            Member<T> myNext = newTemp.next; // ****
//            // 将旧的链表中的遍历到的元素的下一个单拿出来
//            Member<T> member = null;
//            if (temp.next != null) {
//                // 注意：如果写成member=temp.next，则member和temp都指向了temp.next，后面有一句member.next，则相当于temp.next.next因为是引用类型，temp和member一起变化。为了将二者独立，这里要新创建对象。
//                member = new Member<>(temp.next.no, temp.next.object, temp.next.next); // ****
//            } else {
//                // 当next为null时，说明到了最后一个元素，那么就结束运行
//
//                return newTemp;
//            }
//            // 修改单拿出来的元素的下一个元素位置为新链表中的第一个元素
//            if (member != null) {
//                member.next = myNext;// ****
//            }
//            // 将修改好的单拿出来的元素放到新链表第一个位置，即head.next
//            newTemp.next = member; // ****
//            temp = temp.next;
            Member<T> next = temp.next;
            Member<T> newMember = temp;
            newMember.next = newTemp.next;
            newTemp.next = newMember;
            temp = next;
        }
        // 将整个对象的头部节点定义为新建的头结点，原链表废除至垃圾回收。
        return newTemp;
    }

    // 反向打印（百度）
    public void printReverse() {
        // 利用栈的数据结构，将各节点压入栈中，利用先进后出特点
        Stack<Member<T>> stack = new Stack<>();
        // 入栈
        Member<T> temp = head;
        while (temp != null) {
            if (temp.next != null) {
                stack.add(temp.next);
            }
            temp = temp.next;
        }
        // 弹栈
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }
    // 查询
    public T get(int no){
        Member<T> temp = head;
        while (temp != null){
            if (temp.no == no){
                return temp.object;
            }
            temp = temp.next;
        }
        return null;
    }
}
