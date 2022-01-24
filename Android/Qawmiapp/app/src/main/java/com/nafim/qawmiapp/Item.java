package com.nafim.qawmiapp;

public class Item
{
    String bookName,writer,id,link;

    public Item(String bookName, String writer, String id, String link) {
        this.bookName = bookName;
        this.writer = writer;
        this.id = id;
        this.link = link;
    }

    public String getBookName() {
        return bookName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
