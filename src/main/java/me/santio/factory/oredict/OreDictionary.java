package me.santio.factory.oredict;

import me.santio.factory.FactoryLib;
import me.santio.factory.models.FactoryItem;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class OreDictionary {
    
    private static final HashMap<String, List<FactoryItem>> identifiers = new HashMap<>();
   
    public static List<FactoryItem> findItem(Dictionary dict) {
        return FactoryLib.getItems().values()
                .stream()
                .filter(dict::matches)
                .collect(Collectors.toList());
    }
    
    public static boolean matches(FactoryItem i1, FactoryItem i2) {
        String id = getID(i1);
        if (id == null) return false;
        
        Dictionary dict = new Dictionary(id);
        return matches(dict, i2);
    }
    
    public static boolean matches(Dictionary dictionary, FactoryItem item) {
        return findItem(dictionary).contains(item);
    }
    
    public static String getID(FactoryItem item) {
        Optional<Map.Entry<String, List<FactoryItem>>> entry = identifiers.entrySet()
                .stream()
                .filter(m -> m.getValue().contains(item))
                .findFirst();
    
        return entry.map(Map.Entry::getKey).orElse(null);
    }
    
    public static void registerItem(FactoryItem item, String identifier) {
        String id = identifier.toLowerCase().replaceAll(" ", "_");
        List<FactoryItem> items = identifiers.getOrDefault(id, new ArrayList<>());
        items.add(item);
        identifiers.put(id, items);
    }
}
