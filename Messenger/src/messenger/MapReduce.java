package messenger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapReduce
{
    public void wordCount(List<String> wlist)
    {
        List<String> list = wlist;
        Map<String, Integer> counts = list.parallelStream().map(w -> w.toLowerCase()).
            collect(Collectors.toConcurrentMap(
                w -> w, w -> 1, Integer::sum));
        System.out.println(counts);
    }
}