package bubble.shooter;

import bubble.shoot.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class Options extends Activity {

	public static boolean soundBoolean  = true;
	public static boolean vibrateBoolean  = true;
	//public static boolean fotohBoolean  = false;
	CheckBox cbSound ;
	CheckBox cbVibrate ;
	//CheckBox cbFotoh ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.options_layout);
		cbSound = (CheckBox) findViewById(R.id.checkBox1);
		cbVibrate = (CheckBox) findViewById(R.id.checkBox2);
		//cbFotoh = (CheckBox) findViewById(R.id.checkBox3);
		cbSound.setChecked(soundBoolean);
		cbVibrate.setChecked(vibrateBoolean);
		//cbFotoh.setChecked(fotohBoolean);
		super.onCreate(savedInstanceState);
	}
	
	public void check(View v){
		soundBoolean = cbSound.isChecked();
		vibrateBoolean = cbVibrate.isChecked();
		//fotohBoolean = cbFotoh.isChecked();
	}
}
