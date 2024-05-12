package org.co.shoh.HelperClasses;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class StringHelper {
    public static List<String> convertStringArrayToList(String[] stringArray) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, stringArray);
        return list;
    }
}
