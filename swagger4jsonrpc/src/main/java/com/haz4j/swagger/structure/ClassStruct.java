package com.haz4j.swagger.structure;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
//TODO: make all entities immutable
public class ClassStruct {

    private Pair<String, String> tag;
    private String path;
    private SortedSet<MethodStruct> methods = new TreeSet<>(Comparator.comparing(MethodStruct::getName));

    public ClassStruct(ClassStruct classStruct){
        this.setTag(classStruct.getTag());
        this.setPath(classStruct.getPath());
        this.setMethods(classStruct.getMethods());
    }

    public ClassStruct() {
    }
}
