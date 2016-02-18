package es.uam.eps.dadm.practica1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main_TTT extends Activity implements View.OnClickListener {

	private Game game;
	private int pulso = 0, id = 0, id_sel = 0;
	private TextView textView;
	private Button btnRefresh;
	/* Nos permite acceder comodamente a los botones desde los metodos de Main_TTT */
	private int ids[] = { R.id.Button01, R.id.Button02, R.id.Button03,
			R.id.Button04, R.id.Button05, R.id.Button06, R.id.Button07,
			R.id.Button08, R.id.Button09, R.id.Button10, R.id.Button11,
			R.id.Button12, R.id.Button13, R.id.Button14, R.id.Button15,
			R.id.Button16, R.id.Button17, R.id.Button18, R.id.Button19,
			R.id.Button20, R.id.Button21, R.id.Button22, R.id.Button23,
			R.id.Button24, R.id.Button25, R.id.Button26, R.id.Button27,
			R.id.Button28, R.id.Button29, R.id.Button30, R.id.Button31,
			R.id.Button32, R.id.Button33 };
	
	private Animation animScaleOut, animScaleIn;
	private boolean playSound = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); /* Llamamos a superclase e inflamos interfaz grafica */
		setContentView(R.layout.main);						
		textView = (TextView) findViewById(R.id.textView); /* Captamos referencia al elemento TextView de la interfaz */
		btnRefresh = (Button) findViewById(R.id.btnRefresh); /* Captamos referencia al metodo Button de la interfaz */
		registerListeners(); /* Registramos escuchadores */
		animScaleOut = AnimationUtils.loadAnimation(this, R.anim.scale_out);
		animScaleIn = AnimationUtils.loadAnimation(this, R.anim.scale_in);
		animScaleOut.setAnimationListener(animationScaleListenerOut);
		animScaleIn.setAnimationListener(animationScaleListenerIn);
		game = new Game(); /* Instanciamos miembro privado game */
	}

	/*******************************************
	 * Método que registra la actividad como 
	 * escuchador de los botones
	 *******************************************/
	private void registerListeners() {
		TTTButton button;
		for (int i = 0; i < 33; i++) {
			button = (TTTButton) findViewById(ids[i]);
			button.reset(i);
			button.setOnClickListener(this); /* Registra un callback a ser invocado cuando se pulsa la vista */
		}
	}

	/*********************************************
	 * Calcula la posicion del boton segun su id
	 * @param id: id del boton 
	 * @return: posicion del boton en el tablero
	 *********************************************/
	private int fromIdToPosition(int id) {
		for (int i = 0; i < 33; i++)
			if (id == ids[i])
				return i + 1;
		return -1;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/*******************************************
	 * This hook is called whenever an item in 
	 * the options menu is selected.
	 *******************************************/
	@Override
	   public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.about:
	         Toast.makeText(
	               Main_TTT.this
	              ,"Juego Solitario Cha-Cha-Cha. Cambia el tablero para mostrar juego en botones o pintado."
	              ,Toast.LENGTH_LONG)
	              .show();
	         return true;
	 
	      case R.id.changeboard:
	    	  	Intent i = new Intent(this, Main.class);
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

	public void onSaveInstanceState(Bundle outState) {
		// outState.putInt("STATE", outState);
		super.onSaveInstanceState(outState);
	}

	/**************************************
	 * Funcion que se ejecuta cuando el 
	 * usuario pulsa un boton
	 **************************************/ 
	public void onClick(View v) {
		TTTButton button = (TTTButton) v;
		TTTButton aux;
		button.setClickable(true);
		int valor = 0, pulsado = 0;
		int k = 0, l = 2;

		/* Comprobamos que el juego sigue activo, sino, devolvemos el control */
		if (!game.isGameActive())
			return;
		
		id_sel = button.getId();

		if (pulso == 0) {
			if (game.isPossibleMove(fromIdToPosition(id_sel),1)){
				button.select();
				pulsado = 1;
				pulso++;
			}
			id = button.getId();
		} else {
			aux = (TTTButton) findViewById(id);
			aux.deselect();
			pulsado = 1;
			pulso = 0;
		}

		/* Llamamos a play que actualizara el grid */
		if (pulsado == 1)
			game.play(fromIdToPosition(id_sel));
		
		if ((pulso == 0) && (id_sel != id)){
			for (int i = 0; i < 33; i++) {
				button = (TTTButton) findViewById(ids[i]);
				valor = game.comprobarTablero(k, l);
				if (valor == 1)
					button.on();
				else {
					if (id == ids[i]) {
						button.startAnimation(animScaleOut);
					} else
						button.off();
				}
				
				if ((k == 0 || k == 5) && l < 4)
					l++;
				else if ((k == 0 || k == 5) && l == 4) {
					k++;
					l = 2;
				} else if (k == 1 && l == 4) {
					k++;
					l = 0;
				} else if ((k > 1 || k < 5) && l < 6) {
					l++;
				} else if ((k > 1 || k <= 4) && l == 6) {
					k++;
					l = 0;
				} else
					l++;

				if (k == 5 && l == 0)
					l = 2;
			}
		}

		if (game.isWon()) 
			textView.setText("You have won! ");
		
		else if (game.isLost()) {
			textView.setText(" There's " + game.count + " pieces isolated. You have lost! GAMEOVER");
			button.setClickable(false);
			btnRefresh.setVisibility(View.VISIBLE);
			Toast.makeText(getApplicationContext(), "Oooooooooohhh has perdido!", Toast.LENGTH_SHORT).show();
		}

	}
	
	/**************************************************
	 * Se llama cuando el boton que reinicia el juego
	 * es pulsado
	 * @param button: vista del boton seleccionado
	 **************************************************/
	public void newGame(View button) {
		Intent i = new Intent(this, Main_TTT.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		finish();
	}

	/*******************************************************************
	 * An animation listener receives notifications from an animation. 
	 * Notifications indicate animation related events, such as 
	 * the end or the repetition of the animation.
	 *******************************************************************/
	AnimationListener animationScaleListenerOut = new AnimationListener() {

		/**************************************
		 * Notifica el final de una animacion
		 *************************************/
		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			TTTButton aux = (TTTButton) findViewById(id);
			aux.off();		
			aux = (TTTButton) findViewById(id_sel);
			aux.startAnimation(animScaleIn);
		}

		/******************************************
		 * Notifica la repeticion de una animacion
		 *****************************************/
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		/****************************************
		 * Notifica el comienzo de una animacion
		 ***************************************/
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			TTTButton aux = (TTTButton) findViewById(id_sel);
			aux.setVisibility(View.INVISIBLE);
		}
	};
	
	AnimationListener animationScaleListenerIn = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			TTTButton aux = (TTTButton) findViewById(id_sel);			
			aux.on();
			aux.setVisibility(View.VISIBLE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
		}
	};	
	
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
