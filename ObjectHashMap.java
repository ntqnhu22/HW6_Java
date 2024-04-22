import java.util.ArrayList;
public class ObjectHashMap extends AbstractHashMap {

    ArrayList<ArrayList<Entry>>mainList;


    public ObjectHashMap(double maxLoad){
        super(maxLoad);
        mainList = new ArrayList<>(capacity);
        for(int i = 0; i < capacity; i++ ){
            mainList.add(new ArrayList<>());
        }

    }
    @Override
    public void put(Object key, Object value) {
        int idx = Math.abs(hash(key)) % mainList.size();
        ArrayList<Entry> chainList = mainList.get(idx);
        for (Entry entry : chainList) {
            if (entry != null && entry.key.equals(key)) {
                entry.value = (Integer) value;
                return;
            }
        }
        // If the loop completes without finding the word, add it to the end of the chain
        chainList.add(new Entry(key, value));
        numKeys++;
    }


    @Override
    public Object find(Object key) {
        int idx = Math.abs(hash(key)) % mainList.size();
        ArrayList<Entry> chainList = mainList.get(idx);
        
        // Traverse the chain at the calculated index
        for (Entry entry : chainList) {
            if (entry != null && entry.key.equals(key)) {
                // Return the value if the key is found
                return entry.value;
            }
        }
        
    // Return null if the key is not found
    return null; 
    }

    // The time complexity of the resize method is O(n), where n is the number of entries in the hash table.
    @Override
    protected void resize() {
        capacity *= 2; // Double the capacity
        
        ArrayList<ArrayList<Entry>> newMainList = new ArrayList<>(capacity);
        
        // Initialize the new main list
        for (int i = 0; i < capacity; i++) {
            newMainList.add(new ArrayList<>());
        }
        
        // Rehash and redistribute entries to the new main list
        for (ArrayList<Entry> chainList : mainList) {
            for (Entry entry : chainList) {
                int idx = Math.abs(hash(entry.key)) % capacity;
                newMainList.get(idx).add(entry);
            }
        }
        
        // Update the main list reference
        mainList = newMainList;
    }

    @Override
    public boolean containsKey(Object key) {
        int idx = Math.abs(hash(key)) % mainList.size();
        ArrayList<Entry> chainList = mainList.get(idx);
        for (Entry entry : chainList) {
            if (entry != null && entry.key.equals(key)) {
                return true;
            }
        }
        
        return false;
    }
    

    @Override
    public Entry[] getEntries() {
        ArrayList<Entry> entryList = new ArrayList<>();
        for (ArrayList<Entry> chainList : mainList){
            for (Entry entry: chainList){
                if (entry != null){
                    entryList.add(entry);
                }
            }
        }
        return entryList.toArray(new Entry[0]);
        
    }
    
}
