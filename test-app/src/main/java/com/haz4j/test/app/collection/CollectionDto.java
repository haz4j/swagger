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

    private Entry entry;

    private List<Entry> entries;

    private ArrayList<List<Entry>> entriesOfEntries;

    private Collection<Map<String, String>> stringsMap;

    //TODO: collection of collections of objects

    //TODO: add raw collection

    //TODO: collection of maps of lists of maps etc

}