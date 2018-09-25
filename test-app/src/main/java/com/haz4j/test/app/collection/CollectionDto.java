package com.haz4j.test.app.collection;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class CollectionDto {

    private String name;

    private String value;

    private Collection<String> strings;

    private Collection<Collection<String>> stringsOfStrings;

    private Collection<Collection<Collection<Integer>>> integersOfIntegersOfIntegers;

    private Collection<Map<String, String>> stringsMap;

    private Collection<Map<String, Collection<String>>> littleCompositeMap;

    private Collection<Map<String, Collection<Map<Integer, Collection<Map<String, Collection<Map<Double, String>>>>>>>> compositeMap;

    private Entry entry;

    private List<Entry> entries;

    private ArrayList<List<Entry>> entriesOfEntries;

    //TODO: add raw collection

    //TODO: add collection of objects
}