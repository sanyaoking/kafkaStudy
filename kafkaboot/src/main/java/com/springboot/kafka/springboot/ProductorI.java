package com.springboot.kafka.springboot;

public interface ProductorI {
    void init() throws NoRollBackException;
    void init1() throws RollBackException;
}
