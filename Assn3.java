import java.io.*;
import java.io.ObjectInputStream.GetField;
import java.text.DecimalFormat;
import java.util.*;


public class Assn3 {

	public static void main(String[] args) {

		
		
		// Create a set called mySet 
		Set mySet = new HashSet();         

		// Ensure that this set contains an interesting selection of fruit 
		String fruit1 = "pear", fruit2 = "banana", fruit3 = "tangerine",                                 
				fruit4 = "strawberry", fruit5 = "blackberry";   

		mySet.add( fruit1 );  
		mySet.add( fruit2 );      
		mySet.add( fruit3 );       
		mySet.add( fruit2 );  
		mySet.add( fruit4 );        
		mySet.add( fruit5 ); 

		// Display contents of mySet    
		System.out.println( "mySet now contains:" );  
		System.out.println( mySet );
		System.out.println();

		//display cardinality
		System.out.println("Set contains " + mySet.size() + " elements");
		System.out.println();

		//remove elements
		mySet.remove(fruit5);
		mySet.remove(fruit4);
		System.out.println("mySet Now contains: ");
		System.out.println(mySet);
		System.out.println();

		//remove everything else
		mySet.clear();

		//Show that the set is empty
		if (mySet.isEmpty())
			System.out.println("The set is empty");
		System.out.println();

		//PART 2://

		//Construct a list
		List<String> newList = new ArrayList<String>();
		newList.add(fruit1);
		newList.add(fruit2);
		newList.add(fruit3);
		newList.add(fruit4);
		newList.add(fruit5);

		//show items in order with an iterator
		Iterator ir = newList.iterator();
		System.out.println("Print items in order they were inserted");
		while (ir.hasNext()) {
			System.out.println(ir.next());	
		}
		System.out.println();

		//show items in reverse order with a list iterator
		System.out.println("Print items in reverse order");
		ListIterator lir = newList.listIterator(newList.size());
		while(lir.hasPrevious())
		{
			System.out.println(lir.previous());
		}
		System.out.println();

		//Add Object in between others objects
		System.out.println("Insert a banana inbetween the tangerine and strawberry");
		int newPos = newList.indexOf(fruit3);
		newList.add(newPos + 1, fruit2);

		lir = newList.listIterator();
		//print out them in order
		while(lir.hasNext())
		{
			System.out.println(lir.next());	
		}
		System.out.println();
		
		//PART 3//

		String fileName = "input.txt";
		String line = null;
		TreeMap<String, ArrayList<Double>> alphaOrderedMap = new TreeMap<>();
		TreeMap<Double, ArrayList<String>> meritOrdered = new TreeMap<>();
		
		
		//read the file

		try {
			
			FileReader fileReader =  new FileReader(fileName);
			Scanner sc = new Scanner(fileReader);
			
			//Process the file's lines individually with the scanner
			while(sc.hasNextLine()) {
				String s = sc.next();
				Double n = sc.nextDouble();
				
				//inserting into alpha ordered map
				if (alphaOrderedMap.containsKey(s))
				{
					//if the key already exists, then push the value
					ArrayList<Double> elements = alphaOrderedMap.get(s);
					elements.add(n);
				}
				else
				{
					//if the key does not exist, create it and push a list with only one element
					ArrayList<Double> elements = new ArrayList<>();
					elements.add(n);
					alphaOrderedMap.put(s, elements);
				}
				

			}
			
			//Display the alpha ordered map
			System.out.println("Alpha Order");
			for (Map.Entry<String, ArrayList<Double>> entry : alphaOrderedMap.entrySet())
			{
				int size = entry.getValue().size();
				double avg = getAverage(entry.getValue());
				String name = entry.getKey();
				System.out.println(name + " " + size + " " + avg );

				//create the merit ordered map
				if (meritOrdered.containsKey(avg))
				{
					//if the key already exists, then push the value
					ArrayList<String> elements = meritOrdered.get(avg);
					elements.add(name);
				}else
				{
					//if the key does not exist, create it and push a list with only one element
					ArrayList<String> elements = new ArrayList<>();
					elements.add(name);
					meritOrdered.put(avg, elements);
				}
				
			}
			System.out.println();
			//Display the Merit Ordered map
			System.out.println("Merit Order");
			
			int iteratorNumber = 1;
			//get all the keys so we can iterate backwards
			ArrayList<Double> keys = new ArrayList<Double>(meritOrdered.keySet());
			for (int i = meritOrdered.size() -1; i>= 0; i--)
			{
				ArrayList<String> listOfNames = meritOrdered.get(keys.get(i));
				for(String name : listOfNames)
				{
					//display 
					System.out.println(iteratorNumber + " " + name + " " + alphaOrderedMap.get(name).size() + " "+ getAverage(alphaOrderedMap.get(name)));
				}
				iteratorNumber++;
				
			}
			System.out.println();
			
			//print the final stats
			System.out.println("Number of students: " + alphaOrderedMap.size());
			DecimalFormat oneDigit = new DecimalFormat("0.0");
			System.out.println("Average student mark: " + oneDigit.format(getAverage(keys)));
			System.out.println("Standard Deviation: " + oneDigit.format(getStdDev(keys)));

		} catch (Exception e) {
			System.out.println("Can't open the file.");
		}


	}
	
	//method to get the standard deviation of a list
	public static Double getStdDev(ArrayList<Double> list)
	{
        double avg = getAverage(list);
        double temp = 0;
        for(double x : list)
            temp += (avg-x)*(avg-x);
        
        temp = temp/(list.size() -1);
		return Math.sqrt(temp);
	}
	
	//method to get the average of a list of doulbes
	public static Double getAverage(ArrayList<Double> list)
	{	
		Double sum = 0.0;
		ListIterator it = list.listIterator();
		while(it.hasNext())
		{
			sum += (Double)it.next(); 
		}
		
		return sum/list.size();
	}

}
