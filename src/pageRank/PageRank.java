/**
 * 
 */
package pageRank;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

/**
 * @author Juan Zamudio
 * @version 11/12/17
 *
 */
public class PageRank {
	
	// Constants
	public static final double EPSILON = 0.30;
	public static final int N = 1791489;
	
	// Variables
	Map<Integer, List<Integer>> vertexMapT = new HashMap<Integer, List<Integer>>();
	Map<Integer, Integer> totalDegree = new HashMap<Integer, Integer>();
	List<Integer> edgeList;
	Scanner scanner;
	Map<Integer, Double> v = new HashMap<Integer, Double>();
	Map<Integer, Double> vOld = new HashMap<Integer, Double>();
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public Map<Integer, List<Integer>> parseToMatrix (String f) {
		try {
			scanner = new Scanner(new FileReader (f));
			Integer key;
			Integer value;
			Integer degree = 0;
			
			while (scanner.hasNext()) {
				key = (Integer)scanner.nextInt();
				value = (Integer)scanner.nextInt();
				
				if (totalDegree.containsKey(key)) {
					degree = totalDegree.get(key);
					degree +=1;
					totalDegree.put(key, degree);
				} else {
					totalDegree.put(key, 1);
				}
				
				if (vertexMapT.containsKey(value)) {
					List<Integer> el = vertexMapT.get(value);
					el.add(key);
					vertexMapT.put(value, el);
				} else {
					edgeList = new ArrayList<Integer>();
					edgeList.add(key);
					vertexMapT.put(value, edgeList);
				}
				
				if (!v.containsKey(key)) {
					v.put(key, 1.0/N);
					vOld.put(key, 0.0);
				}
			}
			
			pageRank(vertexMapT, totalDegree, v, vOld);
			
		} catch (IOException e){
		    e.printStackTrace();
		} finally {
			scanner.close();
		}
		
		return vertexMapT;
		
	}
	
	/**
	 * 
	 * @param v
	 * @param vOld
	 * @return
	 */
	public double subtractMap (Map<Integer, Double> v, Map<Integer, Double> vOld) {
		
		double total = 0.0;
		
		for (Entry<Integer, Double> myEntry : v.entrySet()) {
			total+= Math.abs(v.get(myEntry.getKey()) - vOld.get(myEntry.getKey()));
		}
		
		return total;
		
	}
	
	/**
	 * 
	 * @param map
	 * @param totalDegree
	 * @param v
	 * @param vOld
	 */
	public void pageRank (Map<Integer, List<Integer>> map, 
						  Map<Integer, Integer> totalDegree,
						  Map<Integer, Double> v, 
			              Map<Integer, Double> vOld) {
		
		double subtract = subtractMap(v, vOld);
		while (subtract > 0.0001) {
			vOld = v;
			v = new HashMap<Integer, Double>();
			
			for (Entry<Integer, List<Integer>> myEntries : map.entrySet()) {
				double value = 0.0;
				
				for (Integer values : myEntries.getValue()) {
					value += vOld.get(values) * (1.0/totalDegree.get(values));
				}
				
				value *= (1 - EPSILON);
				value += (EPSILON/N);
				
				v.put(myEntries.getKey(), value);
			}
			subtract = subtractMap(v, vOld);
		}
		
		// PRINT VALUES
		PriorityQueue<Entry<Integer, Double>> pq = new PriorityQueue<Map.Entry<Integer,Double>>(v.size(), new Comparator<Entry<Integer, Double>>() {

		    public int compare(Entry<Integer, Double> arg0,
		            			   Entry<Integer, Double> arg1) {
		        return arg1.getValue().compareTo(arg0.getValue());
		    }
		});
		
		pq.addAll(v.entrySet());
			try {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Wiki-PageRank-Result_Epsilon-0.30.txt"));
				while (!pq.isEmpty()) {
					Entry<Integer, Double> entryVal = pq.poll();
					double val = entryVal.getValue();
					int page = entryVal.getKey();
					out.writeObject("The page is: " + page + " and the value: is " + val + "\n");
				
					
				}
				out.close();
				System.out.print("PageRank Complete");

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PageRank myClass = new PageRank();
		myClass.parseToMatrix("wiki-topcats.txt");

	}

}
