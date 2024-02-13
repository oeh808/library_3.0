package io.library.library_3.search;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.stereotype.Service;

@Service
public class LinearSearchService implements SearchService {
    @Override
    public boolean search(String[] arr, String[] vals) {
        if (vals.length == 0)
            return true;

        HashSet<String> valuesToFind = new HashSet<String>(Arrays.asList(vals));
        for (String str : arr) {
            if (valuesToFind.size() == 0)
                break;
            if (valuesToFind.contains(str)) {
                valuesToFind.remove(str);
            }
        }

        return valuesToFind.size() == 0;
    }
}
