package es.uam.eps.dadm.practica1;

import android.util.Log;

public class Game {
	public int dim = 7;
	public int grid[][];

	private enum STATE {
		Inactive, Active, Won, Loose
	};

	private STATE gameState = STATE.Inactive;
	private int col, row, filaAnt = 0, colAnt = 0;
	public int count;
	private boolean estadoAnt = false, flag = true, movimiento = false;

	public Game() {
		grid = new int[dim][dim];
		for (row = 0; row < dim; row++)
			for (col = 0; col < dim; col++) {
				if ((row == 3)
						&& (col == 3)
						|| ((row == 0 || row == 1 || row == 5 || row == 6) && (col == 0
								|| col == 1 || col == 5 || col == 6)))
					grid[row][col] = 0; /* Indica que no hay ficha */
				else
					grid[row][col] = 1; /* Indica que hay ficha */
			}
		gameState = STATE.Active;
		count = 0;
	}

	public boolean isWon() {
		return gameState == STATE.Won;
	}

	public boolean isLost() {
		return gameState == STATE.Loose;
	}

	public boolean isPossibleMove(int buttonId, int i) {
		boolean isFilled = true;
		if (buttonId <= 3) {
			if (grid[0][1 + buttonId] == 0)
				isFilled = false;
			else
				isFilled = true;
			if (i!=1){
				filaAnt = 0;
				colAnt = 1 + buttonId;
			}
		} else if (buttonId > 3 && buttonId <= 6) {
			if (grid[1][buttonId - 2] == 0)
				isFilled = false;
			else
				isFilled = true;
			if (i!=1){
				filaAnt = 1;
				colAnt = buttonId - 2;
			}
		} else if (buttonId > 6 && buttonId <= 13) {
			if (grid[2][buttonId - 7] == 0)
				isFilled = false;
			else
				isFilled = true;
			if (i!=1){
				filaAnt = 2;
				colAnt = buttonId - 7;
			}
		} else if (buttonId > 13 && buttonId <= 20) {
			if (grid[3][buttonId - 14] == 0)
				isFilled = false;
			else
				isFilled = true;
			if (i!=1){
				filaAnt = 3;
				colAnt = buttonId - 14;
			}
		} else if (buttonId > 20 && buttonId <= 27) {
			if (grid[4][buttonId - 21] == 0)
				isFilled = false;
			else
				isFilled = true;
			if (i!=1){
				filaAnt = 4;
				colAnt = buttonId - 21;
			}
		} else if (buttonId > 27 && buttonId <= 30) {
			if (grid[5][buttonId - 26] == 0)
				isFilled = false;
			else
				isFilled = true;
			if (i!=1){
				filaAnt = 5;
				colAnt = buttonId - 26;
			}
		} else {
			if (grid[6][buttonId - 29] == 0)
				isFilled = false;
			else
				isFilled = true;
			if (i!=1){
				filaAnt = 6;
				colAnt = buttonId - 29;
			}
		}

		return isFilled;
	}

	/* Comprueba el estado de la partida */
	private void checkGameState() {
		count = 0;
		movimiento = false;
		Log.w("Nany", "checkGameState");
		for (row = 0; row < dim; row++)
			for (col = 0; col < dim; col++) {
				if (grid[row][col] == 1) {
					count++;
					if (movimiento == false){
						movimiento = hayMov(row, col);
						//Log.w("Nany", "row: " + row + " col: " + col + " => mov: " + movimiento);
					}
				}
			}

		if (count == 1)
			gameState = STATE.Won;
		else if (count > 1 && movimiento == false)
			gameState = STATE.Loose;
	}

	/* Comprueba si hay algun movimiento posible valido*/
	private boolean hayMov(int i, int j) {

		boolean move = false;

		switch (i) {
		case 0:
		case 1:
			switch (j) {
			case 2:
				if ((grid[i][j + 2] == 0 && grid[i][j + 1] == 1)
						|| (grid[i + 2][j] == 0 && grid[i + 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			case 4:
				if ((grid[i][j - 2] == 0 && grid[i][j - 1] == 1)
						|| (grid[i + 2][j] == 0 && grid[i + 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			case 3:
				if (grid[i + 2][j] == 0 && grid[i + 1][j] == 1)
					move = true;
				else
					move = false;
				break;
			default:
				move = false;
			}

			break;

		case 5:
		case 6:
			switch (j) {
			case 2:
				if ((grid[i][j + 2] == 0 && grid[i][j + 1] == 1)
						|| (grid[i - 2][j] == 0 && grid[i - 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			case 4:
				if ((grid[i][j - 2] == 0 && grid[i][j - 1] == 1)
						|| (grid[i - 2][j] == 0 && grid[i - 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			case 3:
				if (grid[i - 2][j] == 0 && grid[i - 1][j] == 1)
					move = true;
				else
					move = false;
				break;
			default:
				move = false;
			}
			break;

		case 3:
			switch (j) {
			case 2:
			case 3:
			case 4:
				if ((grid[i][j + 2] == 0 && grid[i][j + 1] == 1)
						|| (grid[i + 2][j] == 0 && grid[i + 1][j] == 1)
						|| (grid[i][j - 2] == 0 && grid[i][j - 1] == 1)
						|| (grid[i - 2][j] == 0 && grid[i - 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			case 0:
			case 1:
				if (grid[i][j + 2] == 0 && grid[i][j + 1] == 1)
					move = true;
				else
					move = false;
				break;
			case 5:
			case 6:
				if (grid[i][j - 2] == 0 && grid[i][j - 1] == 1)
					move = true;
				else
					move = false;
				break;
			}

			break;

		case 2:
			switch (j) {
			case 0:
			case 1:
				if ((grid[i][j + 2] == 0 && grid[i][j + 1] == 1)
						|| (grid[i + 2][j] == 0 && grid[i + 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			case 5:
			case 6:
				if ((grid[i][j - 2] == 0 && grid[i][j - 1] == 1)
						|| (grid[i + 2][j] == 0 && grid[i + 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			default:
				if ((grid[i][j + 2] == 0 && grid[i][j + 1] == 1)
						|| (grid[i + 2][j] == 0 && grid[i + 1][j] == 1)
						|| (grid[i][j - 2] == 0 && grid[i][j - 1] == 1)
						|| (grid[i - 2][j] == 0 && grid[i - 1][j] == 1))
					move = true;
				else
					move = false;
			}

			break;

		default:
			switch (j) {
			case 0:
			case 1:
				if ((grid[i][j + 2] == 0 && grid[i][j + 1] == 1)
						|| (grid[i - 2][j] == 0 && grid[i - 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			case 5:
			case 6:
				if ((grid[i][j - 2] == 0 && grid[i][j - 1] == 1)
						|| (grid[i - 2][j] == 0 && grid[i - 1][j] == 1))
					move = true;
				else
					move = false;
				break;
			default:
				if ((grid[i][j + 2] == 0 && grid[i][j + 1] == 1)
						|| (grid[i + 2][j] == 0 && grid[i + 1][j] == 1)
						|| (grid[i][j - 2] == 0 && grid[i][j - 1] == 1)
						|| (grid[i - 2][j] == 0 && grid[i - 1][j] == 1))
					move = true;
				else
					move = false;
			}

			break;

		}

		return move;
	}

	/************************************************
	 * Rellena el elemento de grid correspondiente 
	 * al movimiento efectuado y comprueba el estado
	 * de la partida 
	 ************************************************/
	public void play(int buttonId) {
		if (flag == true) {
			estadoAnt = isPossibleMove(buttonId,0);
			flag = false;
		}
		row = filaAnt;
		col = colAnt;

		if (isPossibleMove(buttonId,0) == false && estadoAnt == true) {
			if (row == filaAnt) {
				if ((colAnt == col + 2) && (grid[row][colAnt - 1] == 1)) {
					grid[filaAnt][colAnt] = 1;
					grid[row][colAnt - 1] = 0;
					grid[row][col] = 0;
					flag = true;
				} else if ((colAnt == col - 2) && (grid[row][colAnt + 1] == 1)) {
					grid[filaAnt][colAnt] = 1;
					grid[row][colAnt + 1] = 0;
					grid[row][col] = 0;
					flag = true;
				}
			} else if (colAnt == col) {
				if ((filaAnt == row + 2) && (grid[filaAnt - 1][col] == 1)) {
					grid[filaAnt][col] = 1;
					grid[row + 1][col] = 0;
					grid[row][col] = 0;
					flag = true;
				} else if ((filaAnt == row - 2)
						&& (grid[filaAnt + 1][col] == 1)) {
					grid[filaAnt][col] = 1;
					grid[filaAnt + 1][col] = 0;
					grid[row][col] = 0;
					flag = true;
				}
			}
		}
		checkGameState();
	}

	public boolean isGameActive() {
		return gameState == STATE.Active;
	}

	public void setStateInactive() {
		gameState = STATE.Inactive;
	}

	public int comprobarTablero(int i, int j) {
		return grid[i][j];
	}

}