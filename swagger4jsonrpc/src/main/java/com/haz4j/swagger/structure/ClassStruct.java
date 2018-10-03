package com.haz4j.swagger.structure;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

@Getter
public class ClassStruct {

    private final Pair<String, String> tag;
    private final String path;
    private final List<MethodStruct> methods;

    public ClassStruct(Pair<String, String> tag, String path, List<MethodStruct> methods) {
        this.tag = tag;
        this.path = path;
        this.methods = Collections.unmodifiableList(methods);
    }
}
