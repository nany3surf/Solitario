package es.uam.eps.dadm.practica1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Main extends Activity {

	private boolean playSound = true;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);
		Music.play(this, R.raw.shore);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			Toast.makeText(
					Main.this,
					"Juego Solitario Cha-Cha-Cha. Cambia el tablero para mostrar juego en botones o pintado.",
					Toast.LENGTH_LONG).show();
			return true;

		case R.id.changeboard:
			Intent i = new Intent(this, Main_TTT.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			finish();
			return true;

		case R.id.exit:
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void newGame(View button) {
		Intent i = new Intent(this, Main.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		finish();
	}

	public void changeMusic(View button) {

		if (!playSound) {
			button.setBackground(getResources().getDrawable(
					R.drawable.sound_off));
			Music.play(this, R.raw.shore);
			playSound = true;
		} else {
			button.setBackground(getResources()
					.getDrawable(R.drawable.sound_on));
			Music.stop(this);
			playSound = false;
		}
	}
	
	protected void onPause(){
		super.onPause();
		Music.stop(this);
	}

}