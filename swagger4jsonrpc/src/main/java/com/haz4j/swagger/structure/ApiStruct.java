package com.haz4j.swagger.structure;

import lombok.Data;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
public class ApiStruct {

    private SortedSet<ClassStruct> structs = new TreeSet<>(Comparator.comparing(ClassStruct::getPath));

}
