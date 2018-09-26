package com.haz4j.swagger;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TypeWrapper {

    private String name;

    private List<TypeWrapper> typeWrappers = new ArrayList<>();

}
