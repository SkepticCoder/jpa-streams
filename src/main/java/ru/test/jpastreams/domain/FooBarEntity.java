package ru.test.jpastreams.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FooBarEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String field1;

    private int field2;

    private float field3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public int getField2() {
        return field2;
    }

    public void setField2(int field2) {
        this.field2 = field2;
    }

    public float getField3() {
        return field3;
    }

    public void setField3(float field3) {
        this.field3 = field3;
    }
}
