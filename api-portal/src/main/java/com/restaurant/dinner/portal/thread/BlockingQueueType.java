package com.restaurant.dinner.portal.thread;

/**
 * 线程池阻塞队列的类型
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/10
 */
public enum BlockingQueueType {
    //基于数组的先进先出队列，此队列创建时必须指定大小
    Array,
    //基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE
    Linked,
    //不会保存提交的任务，而是将直接新建一个线程来执行新来的任务
    Synchronous,
    //具有优先级得无限阻塞队列
    Priority
}
