package com.yy;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.Node;
import com.alibaba.csp.sentinel.node.StatisticNode;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.nodeselector.NodeSelectorSlot;


/**
 * 2020年，提前祝大家 鼠年快乐，属你最美，属你最帅，鼠钱鼠到手抽筋。
 *
 * 给大家准备了一道题
 * 1 仔细看下这个类，摘抄某大型开源项目片段
 * 问题1：说明本类基本用途？
 * 问题2：为什么要用 volatile？
 * 问题3：为什么要用 synchronized？
 * 问题4：
 *                   Set<Node> newSet = new HashSet<>(childList.size() + 1);
 *                   newSet.addAll(childList);
 *                   newSet.add(node);
 *                   childList = newSet;
 *					替换为   childList.add(node); 行不行？为什么？
 *
 * 参与人：本群人员，李海南，孙东凯，石雨龙，詹怀远，姚远 不参与此活动。
 * 年后回来，每人都10分钟面谈，优秀者 我私人出资奖励200元红包。评委：李海南，孙东凯
 *
 * 要求：组内不允许私下和公开探讨。一经发现罚款100，奖励给优秀者和举报者
 *     允许面向百度编程
 *
 *
 */
public class DefaultNode extends StatisticNode {

    /**
     * The list of all child nodes.
     */
    private volatile Set<Node> childList = new HashSet<>();


    /**
     * Add child node to current node.
     *
     * @param node valid child node
     */
    public void addChild(Node node) {
        if (node == null) {
            //    RecordLog.warn("Trying to add null child to node <{0}>, ignored");
            return;
        }
        if (!childList.contains(node)) {
            synchronized (this) {
                if (!childList.contains(node)) {
                    Set<Node> newSet = new HashSet<>(childList.size() + 1);
                    newSet.addAll(childList);
                    newSet.add(node);
                    childList = newSet;
                }
            }
            //  RecordLog.info("Add child <{0}> to node <{1}>", ((DefaultNode)node).id.getName(), id.getName());
        }
    }

    /**
     * Reset the child node list.
     */
    public void removeChildList() {
        this.childList = new HashSet<>();
    }

    public Set<Node> getChildList() {
        return childList;
    }
}
