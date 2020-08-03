import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


class Result{
    
    public static int minDist( int numNodes, Boolean visited[], Integer value[] ){
        
        int minValue = Integer.MAX_VALUE;
        int closestNode = Integer.MAX_VALUE;
        
        for( int i = 0; i < numNodes; i ++ ){
            
            if( visited[i] != true && minValue >= value[i] ){
                
                minValue = value[i];
                closestNode = i;
                
            }
            
        }
        
        return closestNode;
        
    }
    
    public static int minCost( int gNodes, List<Integer> gFrom, List<Integer> gTo, List<Integer> gWeight ) {
        
        Integer key[][] = new Integer[ gNodes ][ gNodes ];
        Integer distance[] = new Integer[ gNodes ];
        Boolean checker[] = new Boolean[ gNodes ];
        
        int result;
        
        for( int i = 0; i < gNodes; i ++ ){
            
            distance[i] = Integer.MAX_VALUE;
            checker[i] = false;
            
        }
        
        distance[0] = 0;
        
        for( int i = 0; i < gNodes; i ++ ){
            
            for(int j = 0; j < gNodes; j ++ ){
                
                key[i][j] = 1;
                
            }
            
        }
        
        for( int i = 0; i < gFrom.size(); i ++ ){
            
            key[ gFrom.get(i) - 1 ][ gTo.get(i) - 1 ] = gWeight.get(i);
            
        }
        
        for( int i = 0; i < gNodes - 1; i ++ ){
            
            int dist = minDist( gNodes, checker, distance );
            
            checker[ dist ] = true;
            
            for( int j = 0; j < gNodes; j ++ ){
                
                if( checker[j] != true && distance[i] != Integer.MAX_VALUE && key[i][j] != 0 ){
                    distance[j] = distance[i] + key[i][j];
                }
                
            }
            
        }
        
        result = distance[ gNodes - 1 ];
        return result;
        
    }
    
}
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        
        String[] gNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
        
        int gNodes = Integer.parseInt(gNodesEdges[0]);
        int gEdges = Integer.parseInt(gNodesEdges[1]);
        
        List<Integer> gFrom = new ArrayList<>();
        List<Integer> gTo = new ArrayList<>();
        List<Integer> gWeight = new ArrayList<>();
        
        IntStream.range(0, gEdges).forEach(i -> {
            try {
                String[] gFromToWeight = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
                
                gFrom.add(Integer.parseInt(gFromToWeight[0]));
                gTo.add(Integer.parseInt(gFromToWeight[1]));
                gWeight.add(Integer.parseInt(gFromToWeight[2]));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        
        int result = Result.minCost(gNodes, gFrom, gTo, gWeight);
        
        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();
        
        bufferedReader.close();
        bufferedWriter.close();
    }
}