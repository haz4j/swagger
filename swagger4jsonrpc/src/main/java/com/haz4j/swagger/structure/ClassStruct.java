package com.haz4j.swagger.structure;

import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

@Data
public class ClassStruct {

    private Pair<String, String> tag;

    private String path;
}
