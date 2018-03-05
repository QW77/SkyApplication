package com.sky.app.bean;

import java.util.List;

/**
 * 消息返回
 * Created by hongbang on 2017/5/7.
 */

public class MessageList  extends Page{

    List<Message>  list;

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MessageList{" +
                "list=" + list +
                '}';
    }
}
