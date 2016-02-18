package es.uam.eps.dadm.practica1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class Board extends View {
	private Game game;
	private int SIZE = 7, pulso = 0, id = 0, xIndex = 0, yIndex = 0;
	private float height_of_tile, width_of_tile;
	private int background_off = getResources()
			.getColor(R.color.off_back_color);
	private int background_on = getResources().getColor(R.color.on_back_color);
	private int circulo = getResources().getColor(R.color.circulo);
	private int border = getResources().getColor(R.color.border);
	private int color, seleccionado[][], xAnt = 0, yAnt = 0;
	
	// Coordenadas del punto medio del rectangulo
	float x, y;
	private boolean primera = true;

	public Board(Context context, AttributeSet attributes) {
		super(context, attributes);
		setFocusable(true);
		setFocusableInTouchMode(true);
		seleccionado = new int[SIZE][SIZE];
		game = new Game();
	}

	protected void onSizeChanged(int width, int height, int oldWidth,
			int oldHeight) {
		if (width < height)
			height = width;
		else
			width = height;
		width_of_tile = width / 7f;
		height_of_tile = height / 7f;

		super.onSizeChanged(width, height, oldWidth, oldHeight);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float width_of_board = getWidth();
		float height_of_board = getHeight();
		float startX, stopX, startY, stopY;

		if (width_of_board < height_of_board)
			height_of_board = width_of_board;
		else
			width_of_board = height_of_board;

		Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setColor(Color.TRANSPARENT);
		canvas.drawRect(0, 0, width_of_board, height_of_board, backgroundPaint);

		Paint linePaint = new Paint();
		linePaint.setStrokeWidth(8); /* Define el ancho de la brocha*/
		linePaint.setColor(border);

		Paint btn_background = new Paint(Paint.ANTI_ALIAS_FLAG);
		btn_background.setColor(background_off);

		/* Pintamos lineas horizontales del tablero y fondo */
		for (int i = 0; i <= SIZE; i++) {
			if (i == 0 || i == 1 || i == 6 || i == 7) {
				startX = 2 * width_of_tile;
				stopX = 5 * width_of_tile;
			} else {
				startX = 0;
				stopX = width_of_board;
			}

			startY = (i) * height_of_tile;
			stopY = (i) * height_of_tile;

			canvas.drawLine(startX, startY, stopX, stopY, linePaint);

			if (i == 5)
				canvas.drawRect(2 * width_of_tile, startY, 5 * width_of_tile,
						stopY + width_of_tile, btn_background);
			else if (i != 7)
				canvas.drawRect(startX, startY, stopX, stopY + width_of_tile,
						btn_background);
		}

		/* Pintamos lineas verticales del tablero */
		for (int i = 0; i <= SIZE; i++) {
			if (i == 0 || i == 1 || i == 6 || i == 7) {
				startY = 2 * width_of_tile;
				stopY = 5 * width_of_tile;
			} else {
				startY = 0;
				stopY = width_of_board;
			}
			startX = (i) * height_of_tile;
			stopX = (i) * height_of_tile;
			canvas.drawLine(startX, startY, stopX, stopY, linePaint);
		}

		/* Pintamos simbolos */
		drawSymbols(canvas);
	}

	private void drawSymbols(Canvas canvas) {

		float startX, startY;
		int valor = 0;
		color = circulo;

		Paint paint = new Paint();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (((i == 0 || i == 1 || i == 5 || i == 6) && (j == 0
						|| j == 1 || j == 5 || j == 6))) {
					// no hace nada }
				} else {
					if (primera && i == 3 && j == 3)
						this.off();

					primera = false;
					valor = game.comprobarTablero(i, j);
					if (seleccionado[i][j] == 1)
						this.select();
					else if (valor == 1)
						this.on();
					else
						this.off();

					startX = (j) * width_of_tile;
					startY = (i) * height_of_tile;

					/* Pintamos los símbolos */
					x = startX + (width_of_tile / 2);
					y = startY + (height_of_tile / 2);

					paint.setColor(color);
					canvas.drawCircle(x, y, 20, paint);
				}
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {

		int pulsado = 0;
		int action = event.getAction();
		float x, y;

		if (!game.isGameActive())
			return false;

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();

			if (pulso == 0) {
				id = fromIdToPosition(x, y);

				// Devuelve -1 si se ha pulsado una posicion en blanco
				if (id != -1) {
					if (game.isPossibleMove(id, 1)) {
						seleccionado[yIndex][xIndex] = 1;
						xAnt = xIndex;
						yAnt = yIndex;
						pulsado = 1;
						pulso++;
					}
				} else
					return super.onTouchEvent(event);
			} else {
				seleccionado[yAnt][xAnt] = 0;
				pulsado = 1;
				pulso = 0;
			}

			if (!game.isGameActive())
				return false;

			/*
			 * Si es la primera que se ha pulsado simplemente guarda su estado,
			 * si es la segunda, juega
			 */
			if (pulsado == 1)
				game.play(fromIdToPosition(x, y));

			if (game.isWon())
				Toast.makeText(this.getContext(), "You have won!", Toast.LENGTH_LONG).show();

			else if (game.isLost())
				Toast.makeText(this.getContext(), " There's " + game.count + " pieces isolated. You have lost! GAMEOVER", Toast.LENGTH_LONG).show();
			
			break;
		}

		return super.onTouchEvent(event);
	}

	/************************************************
	 * Me da id del boton segun las coordenadas
	 * pasadas por argumento
	 ***********************************************/
	private int fromIdToPosition(float x, float y) {

		if (x < width_of_tile)
			xIndex = 0;
		else if (x < 2 * width_of_tile)
			xIndex = 1;
		else if (x < 3 * width_of_tile)
			xIndex = 2;
		else if (x < 4 * width_of_tile)
			xIndex = 3;
		else if (x < 5 * width_of_tile)
			xIndex = 4;
		else if (x < 6 * width_of_tile)
			xIndex = 5;
		else if (x < 7 * width_of_tile)
			xIndex = 6;
		if (y < height_of_tile)
			yIndex = 0;
		else if (y < 2 * height_of_tile)
			yIndex = 1;
		else if (y < 3 * height_of_tile)
			yIndex = 2;
		else if (y < 4 * height_of_tile)
			yIndex = 3;
		else if (y < 5 * height_of_tile)
			yIndex = 4;
		else if (y < 6 * height_of_tile)
			yIndex = 5;
		else if (y < 7 * height_of_tile)
			yIndex = 6;

		if (((xIndex == 0 || xIndex == 1 || xIndex == 5 || xIndex == 6) && (yIndex == 0
				|| yIndex == 1 || yIndex == 5 || yIndex == 6)))
			return -1;
		else {
			if (yIndex == 0)
				return xIndex - 1;
			else if (yIndex == 1)
				return xIndex + 2;
			else if (yIndex == 2)
				return xIndex + 7;
			else if (yIndex == 3)
				return xIndex + 14;
			else if (yIndex == 4)
				return xIndex + 21;
			else if (yIndex == 5)
				return xIndex + 26;
			else if (yIndex == 6)
				return xIndex + 29;
		}

		return -1;
	}

	public void select() {
		color = background_on;
		this.invalidate();
	}

	public void on() {
		color = circulo;
		this.invalidate();
	}

	public void off() {
		color = background_off;
		this.invalidate();
	}
}