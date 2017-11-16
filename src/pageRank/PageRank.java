/**
 * 
 */
package pageRank;

import java.util.*;
import java.io.*;

/**
 * @author Juan Zamudio
 * @version 11/12/17
 *
 */
public class PageRank {
	
	Map<Integer, List<Integer>> vertexMap = new HashMap<Integer, List<Integer>>();
	List<Integer> edgeList;
	Scanner scanner;
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public Map<Integer, List<Integer>> parseToMap (String f) {
		try {
			scanner = new Scanner(new FileReader (f));
			Integer key;
			Integer value;
			
			while (scanner.hasNext()) {
				key = (Integer)scanner.nextInt();
				value = (Integer)scanner.nextInt();
				
				if (vertexMap.containsKey(key)) {
					edgeList.add(value);
					vertexMap.put(key, edgeList);
				} else {
					edgeList = new ArrayList<Integer>();
					edgeList.add(value);
					vertexMap.put(key, edgeList);
				}
			}
		} catch (IOException e){
		    e.printStackTrace();
		} finally {
			scanner.close();
		}
		
		return vertexMap;
		
	}
	
	public Integer[][] createMatrix(Map<Integer, List<Integer>> map) {
		int mapSize = map.size();
		List<Integer> values;
		Integer[][] matrix = new Integer[mapSize][mapSize];
		System.out.println(mapSize);
		
		for (int i = 0; i <= mapSize; i++) {
			
			for (int j = 0; j <= mapSize - 1; j++) {
				
				values = map.get(i);
				
				if (values.contains(j)) {
//					matrix[i][j] 
				} else {
					
				}
			}
		}
		
		return matrix;
		
		
	}
	
//	public Vector pageRank (Graph g, int epsilon) {
//		
//	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PageRank myClass = new PageRank();
		myClass.parseToMap("wiki-topcats.txt");

	}

}
