package es.rubenjgarcia.beanctional.generator.tests;

import es.rubenjgarcia.beanctonial.core.annotation.FunctionalBean;

@FunctionalBean
public class Bean implements Comparable<Bean> {

    private String myString;

    private byte myBytePrimitive;
    private short myShortPrimitive;
    private int myIntPrimitive;
    private long myLongPrimitive;
    private double myDoublePrimitive;

    private Byte myByte;
    private Short myShort;
    private Integer myInt;
    private Long myLong;
    private Double myDouble;

    public String getMyString() {
        return myString;
    }

    public void setMyString(String myString) {
        this.myString = myString;
    }

    public byte getMyBytePrimitive() {
        return myBytePrimitive;
    }

    public void setMyBytePrimitive(byte myBytePrimitive) {
        this.myBytePrimitive = myBytePrimitive;
    }

    public short getMyShortPrimitive() {
        return myShortPrimitive;
    }

    public void setMyShortPrimitive(short myShortPrimitive) {
        this.myShortPrimitive = myShortPrimitive;
    }

    public int getMyIntPrimitive() {
        return myIntPrimitive;
    }

    public void setMyIntPrimitive(int myIntPrimitive) {
        this.myIntPrimitive = myIntPrimitive;
    }

    public long getMyLongPrimitive() {
        return myLongPrimitive;
    }

    public void setMyLongPrimitive(long myLongPrimitive) {
        this.myLongPrimitive = myLongPrimitive;
    }

    public double getMyDoublePrimitive() {
        return myDoublePrimitive;
    }

    public void setMyDoublePrimitive(double myDoublePrimitive) {
        this.myDoublePrimitive = myDoublePrimitive;
    }

    public Byte getMyByte() {
        return myByte;
    }

    public void setMyByte(Byte myByte) {
        this.myByte = myByte;
    }

    public Short getMyShort() {
        return myShort;
    }

    public void setMyShort(Short myShort) {
        this.myShort = myShort;
    }

    public Integer getMyInt() {
        return myInt;
    }

    public void setMyInt(Integer myInt) {
        this.myInt = myInt;
    }

    public Long getMyLong() {
        return myLong;
    }

    public void setMyLong(Long myLong) {
        this.myLong = myLong;
    }

    public Double getMyDouble() {
        return myDouble;
    }

    public void setMyDouble(Double myDouble) {
        this.myDouble = myDouble;
    }

    @Override
    public int compareTo(Bean o) {
        return 0;
    }
}