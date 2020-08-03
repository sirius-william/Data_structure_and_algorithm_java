/*
 * CircleLinkedList
 * 双向环形链表
 * 特点：首尾相连；没有自增no值
 @author Sirius
 */
package linklist;

import java.util.ArrayList;

class CircleMember<T> {
    public int no;
    public CircleMember<T> pre;
    public CircleMember<T> next;
    public T val;

    public CircleMember(int no, CircleMember<T> pre, CircleMember<T> next, T val) {
        this.no = no;
        this.pre = pre;
        this.next = next;
        this.val = val;
    }

    public CircleMember(int no, T val, boolean self) {
        this.no = no;
        this.val = val;
        if (self) {
            // 是否连接自身，初始状态下，自己连接自己形成闭环
            this.pre = this;
            this.next = this;
        } else {
            this.pre = null;
            this.next = null;
        }
    }
}

public class DoubleCircleLinkedList<T> {
    private CircleMember<T> member;
    // 索引集合
    private ArrayList<Integer> noArray = new ArrayList<>();

    public DoubleCircleLinkedList(int no, T val) {
        // 用户指定初始节点的no和数据值
        this.member = new CircleMember<>(no, val, true);
        // no写入noArray
        noArray.add(no);
    }

    public DoubleCircleLinkedList() {
        this.member = new CircleMember<>(0, null, true);
        noArray.add(0);
    }

    /*
     * target：目标位置索引
     * */
    private void addDefault(int target, int no, T val, int location, int direction) {
        // 0表示目标节点前面插入，1表示目标节点后面插入
        // direction: 0顺时针，1逆时针，2为自动判断方向
        if (noArray.contains(no)) {
            System.out.println("编号为" + no + "的节点已存在！");
            return;
        }
        // reverse=true为顺时针
        boolean reverse = true;
        CircleMember<T> temp = member;
        CircleMember<T> newMember;
        if (direction == 2) {
            int distance = noArray.indexOf(no);
            int start = member.no;
            // 判断应该顺时针还是逆时针遍历
            reverse = ((distance - start) - (noArray.size())) > 0;
        }
        if (noArray.contains(target)) {
            newMember = new CircleMember<>(no, val, false);
            while (temp != null) {
                if (temp.no == target) {
                    switch (location) {
                        case 0 -> {
                            temp.pre.next = newMember;
                            newMember.pre = temp.pre;
                            newMember.next = temp;
                        }
                        case 1 -> {
                            temp.next.pre = newMember;
                            newMember.pre = temp;
                            newMember.next = temp.next;
                        }
                    }
                    // 将该索引存入数组
                    noArray.add(no);
                    break;
                }
                switch (direction) {
                    case 0:
                        temp = temp.pre;
                    case 1:
                        temp = temp.next;
                    case 2:
                        temp = reverse ? temp.pre : temp.next;
                }
            }
        }
    }

    // 默认添加方式：顺时针遍历，目标节点前插入。
    public void delete(int no) {
        if (!noArray.contains(no)) {
            System.out.println("该节点不存在！");
            return;
        }
        boolean reverse = true;
        int distance = noArray.indexOf(no);
        int start = member.no;
        // 判断应该顺时针还是逆时针遍历
        reverse = ((distance - start) - (noArray.size())) > 0;

        CircleMember<T> temp = member;
        while (temp != null) {
            if (temp.no == no) {
                temp.pre.next = temp.next;
                temp.next.pre = temp.pre;
                break;
            }
            temp = reverse ? temp.pre : temp.next;

        }
    }

    // 自动判断遍历方向新增节点
    public void add(int target, int no, T val) {
        this.addDefault(target, no, val, 0, 2);
    }

    // 默认遍历方向：顺时针
    public void add(int target, int no, T val, int location) {
        this.addDefault(target, no, val, location, 0);
    }

    // 按照自定义方式遍历添加
    public void add(int target, int no, T val, int location, int direction) {
        this.addDefault(target, no, val, location, direction);
    }

    // 查询
    public T get(int no) {
        if (!noArray.contains(no)) {
            System.out.println("不存在");
            return null;
        }
        boolean reverse = true;
        int distance = noArray.indexOf(no);
        int start = member.no;
        // 判断应该顺时针还是逆时针遍历
        reverse = ((distance - start) - (noArray.size())) > 0;
        CircleMember<T> temp = member;
        while (temp != null) {
            if (temp.no == no) {
                return temp.val;
            }
            temp = reverse ? temp.pre : temp.next;
        }
        return null;
    }
}

