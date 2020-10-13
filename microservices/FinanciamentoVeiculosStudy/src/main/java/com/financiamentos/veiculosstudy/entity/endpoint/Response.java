package com.financiamentos.veiculosstudy.entity.endpoint;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

    private T data;
    private List<String> messages;

    public Response(final T data) {
        this.data = data;
    }

    public Response(final List<String> messages) {
        this.messages = messages;
    }

    public Response(final String string) {
        final List<String> msgs = new ArrayList<>();
        msgs.add(string);
        this.messages = msgs;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public List<String> getmessages() {
        return messages;
    }

    public void setmessages(final List<String> messages) {
        this.messages = messages;
    }

}
