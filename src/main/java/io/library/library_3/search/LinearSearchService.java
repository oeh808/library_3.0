package io.library.library_3.search;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LinearSearchService implements SearchService {
    @Override
    public boolean search(String[] arr, String[] vals) {
        if (vals.length == 0)
            return true;

        HashSet<String> valuesToFind = new HashSet<String>();
        Set<String> temp = Arrays.asList(vals).stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        valuesToFind.addAll(temp);

        for (String str : arr) {
            str = str.toLowerCase();
            if (valuesToFind.size() == 0)
                break;
            if (valuesToFind.contains(str)) {
                valuesToFind.remove(str);
            }
        }

        return valuesToFind.size() == 0;
    }
}
