package bubble.shooter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import android.R.color;

public class Generate {

	static Random rand ;
	static JSONObject obj;
	
	public Generate() throws IOException, ParseException {
		rand = new Random();
		JSONParser parser = new JSONParser();
		BufferedReader reader = new BufferedReader(new InputStreamReader(BubbleShootActivity.inputStream));
		
		String data = "";
		String x = "";
		
		// read All DATA
		while ((x = reader.readLine()) != null)
			data+=x ;
		
		obj = (JSONObject) parser.parse(data);
		
	}
	
	public static Bubble [][] load (String level)
	{ 
		Bubble [][] array = null ;
		// get Level Array
		JSONArray arr = (JSONArray)obj.get(level);
		array = new Bubble [GameScene.arrayLenght][GameScene.numOfBubbPerRow-1];
			
		Iterator<Long> it = arr.iterator();
			
		// fill the Array with the bubbles
			for (int i =0;i<arr.size()/(GameScene.numOfBubbPerRow-1);i++)
				for (int j =0;j<array[0].length;j++)
					array[i][j] = new Bubble(j*GameScene.bubbleSize, i*GameScene.bubbleSize,it.next().intValue());
			
		return array ;
	}
	
//	public static Bubble [][] generateArray(String level)
//	{
//		Bubble [][] array = new Bubble [15] [GameScene.numOfBubbPerRow];	
//		int numOfRows = 15;
////		int numOfRows = 5+level;
////		if(numOfRows >= array.length)
////			numOfRows = array.length-5;
//		
//		for (int i =0;i<numOfRows;i++)
//		{
//			for (int j =0;j<array[0].length;j++)
//			{
//					array[i][j] = new Bubble(j*GameScene.bubbleSize
//						, i*GameScene.bubbleSize, rand.nextInt(4));
//			}
//		}
//		return array ;
//	}
	
	
}
