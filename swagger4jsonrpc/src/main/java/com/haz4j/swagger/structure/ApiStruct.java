package com.haz4j.swagger.structure;

import lombok.Data;

import java.util.*;

@Data
public class ApiStruct {

    private SortedSet<ClassStruct> structs = new TreeSet<>(Comparator.comparing(ClassStruct::getPath));

}
