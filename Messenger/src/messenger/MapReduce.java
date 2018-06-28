package messenger;

import java.util.List;
import java.util.stream.*;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MapReduce
{
	
    public void wordCount(List<String> wlist)
    {
    	List<String> list = wlist;
    	Stream<String> s = list.stream().map(w -> w.toLowerCase()).flatMap(Pattern.compile("\\s")::splitAsStream);
        
    	list = s.collect(Collectors.toList());
    	
        Map<String, Integer> counts = list.parallelStream().map(ws -> ws.toLowerCase()).
            collect(Collectors.toConcurrentMap(
                w -> w, w -> 1, Integer::sum));
        System.out.println(counts);
    }
    
    public void specificWordCount(List<String> wlist, String word)
    {
    	List<String> list = wlist;
    	Stream<String> s = list.stream().map(w -> w.toLowerCase()).flatMap(Pattern.compile("\\s")::splitAsStream);
        
    	list = s.collect(Collectors.toList());
    	String w = word.toLowerCase();
    	
    	int anzahl = 0;
    	
    	for(String ws : list)
    	{
    		if(ws.equals(w))
    		{
    			anzahl++;
    		}
    	}
    	
    	System.out.println(word+": "+anzahl);
    }
}