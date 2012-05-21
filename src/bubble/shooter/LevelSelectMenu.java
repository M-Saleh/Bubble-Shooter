package bubble.shooter;

import bubble.shoot.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class LevelSelectMenu extends Activity implements OnClickListener{
	public static String levelSelected ;
	public static int selected ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.selection_1);
		super.onCreate(savedInstanceState);
		
		ImageButton imb1 = (ImageButton) findViewById(R.id.imageButton1);
		imb1.setOnClickListener(this);
//		imb1.setBackgroundColor(color)
		ImageButton imb2 = (ImageButton) findViewById(R.id.imageButton2);
		imb2.setOnClickListener(this);
		ImageButton imb3 = (ImageButton) findViewById(R.id.imageButton3);
		imb3.setOnClickListener(this);
		ImageButton imb4 = (ImageButton) findViewById(R.id.imageButton4);
		imb4.setOnClickListener(this);
		ImageButton imb5 = (ImageButton) findViewById(R.id.imageButton5);
		imb5.setOnClickListener(this);
		ImageButton imb6 = (ImageButton) findViewById(R.id.imageButton6);
		imb6.setOnClickListener(this);
		ImageButton imb7 = (ImageButton) findViewById(R.id.imageButton7);
		imb7.setOnClickListener(this);
		ImageButton imb8 = (ImageButton) findViewById(R.id.imageButton8);
		imb8.setOnClickListener(this);
		ImageButton imb9 = (ImageButton) findViewById(R.id.imageButton9);
		imb9.setOnClickListener(this);
		ImageButton imb10 = (ImageButton) findViewById(R.id.imageButton10);
		imb10.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		int id = v.getId() ;
		switch (id){
		case R.id.imageButton1 :
			selected =1;
			levelSelected = "ONE";
			break;
		case R.id.imageButton2 :
			selected =2;
			levelSelected = "TWO";
			break;
		case R.id.imageButton3 :
			selected =3;
			levelSelected = "THREE";
			break;
		case R.id.imageButton4 :
			selected =4;
			levelSelected = "FOUR";
			break;
		case R.id.imageButton5 :
			selected =5;
			levelSelected = "FIVE";
			break;
		case R.id.imageButton6 :
			selected =6;
			levelSelected = "SIX";
			break;
		case R.id.imageButton7 :
			selected =7;
			levelSelected = "SEVEN";
			break;
		case R.id.imageButton8 :
			selected =8;
			levelSelected = "EIGHT";
			break;
		case R.id.imageButton9 :
			selected =9;
			levelSelected = "NINE";
			break;
		case R.id.imageButton10 :
			selected =10;
			levelSelected = "TEN";
			break;
		}
		
		// Call Playing Avtivity
		startActivity(new Intent(this,BubbleShootActivity.class));
	}
	
}

