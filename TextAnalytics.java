import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
public class TextAnalytics {
    private ObjectHashMap wordCount;

    public TextAnalytics() {
    wordCount = new ObjectHashMap(0.75);
    }      

    public void readFile(String filename){
    File file = new File(filename);
    boolean scannedlines = false; // Initialize scannedlines outside the loop
    try(Scanner scan = new Scanner(file);){
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if(line.contains("*** START OF THIS PROJECT GUTENBERG EBOOK")){
                scannedlines = true; // Set scannedlines to true when book content starts
                continue;
            } else if (line.contains("*** END OF THIS PROJECT GUTENBERG EBOOK")) {
                break;
            }
            if(scannedlines){
                scanTheWords(line);
            }
        }  
    } catch(FileNotFoundException e){
        System.out.println("Cannot find the file: " + filename);
    }
    System.out.println("Finished reading file");
    }

    // This method has O(n) time complexity because it scans each line of the text file and slipt it to words
    //When the size of the words increase, it take a time complexity of order O(n)
    public void scanTheWords(String line) {
        // Remove non-alphabetic characters and convert to lowercase
        line = line.toLowerCase().replaceAll("[^a-z ]", "");
       
        // Split the line into words
        String[] words = line.split("\\s+");
        
        for (String word : words) {
            // Update the count for each word
            if (!word.isEmpty()) {
               Integer count = (Integer) wordCount.find(word);

                if (count == null) {
                    wordCount.put(word, 1); // If word not found, initialize count to 1
                   
                } else {
                    //System.out.println(count);
                    wordCount.put(word, count + 1); // If word found, increment count
                }
            }
        }
    }

    public void countTheWords(String word) {
        Integer count = (Integer) wordCount.find(word.toLowerCase());
        if (count == null) {
            System.out.println("The word '" + word + "' is not present.");
        } else {
            System.out.println("The word '" + word + "' occurs " + count + " times.");
        }
    }

    //Part2
    public static void insertionSort(Entry[] arr){
        for (int i = 1; i < arr.length; i++){
            Entry key = arr[i];
            int j = i - 1;
            while (j >= 0 && (Integer)arr[j].value < (Integer)key.value){
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    //This method has O(n^2) times complexity because it access to the entry array which is sorted by 
    //the insertion sort which has O(n^2) times complexity
    public void topFive(){
        System.out.println("--Top 5 Most Frequent Words--");
        Entry[] sortedArr = wordCount.getEntries();
        insertionSort(sortedArr);
        for (int i = 0; i < Math.min(5, sortedArr.length); i++){
            System.out.println((i + 1) + ".)" + " " + "'" + sortedArr[i].key + "'" + " " + sortedArr[i].value + " uses.");
        }
    }
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TextAnalytics <filePath>");
            return;
        }
        TextAnalytics textAnalysis = new TextAnalytics();
        textAnalysis.readFile(args[0]); // Call readFile before counting words
        textAnalysis.topFive();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Type a word or type 'q' to quit: ");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                System.out.println("Exiting program");
                break; // Exit the loop and terminate the program
            }
            textAnalysis.countTheWords(input);
    }
        scanner.close(); // Close the scanner to release resources
    }
}
