package es.uam.eps.dadm.practica1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;
//import android.util.Log;

public class TTTButton extends Button {
	
	private int background_off = getResources().getColor(R.color.off_back_color);
	private int background_on = getResources().getColor(R.color.on_back_color);
	private int circulo = getResources().getColor(R.color.circulo1);
	private int border = getResources().getColor(R.color.border1);
	private int background, color;
	// Coordenadas del punto medio del rectangulo
    float x, y;
	
    /**********************************************
     * Método onDraw: Se ejecuta automaticamente 
     * cuando se representa la vista
     **********************************************/
	protected void onDraw (Canvas canvas){
        super.onDraw(canvas);
        
        /* Guardamos la altura y anchura del boton */
        float width = getWidth();
        float height = getHeight();
        float padding = 6;
        
        /* Calculamos punto medio del boton para pintar el circulo */
        x = 0.5f * width;
        y = 0.5f * height;
        
        /* Pintamos el fondo del boton */
        Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG); /* Hace que el trazo sea mas suave */
        backgroundPaint.setColor(background);
        canvas.drawRect(padding, padding, width-padding, height-padding, backgroundPaint);
                
        /* Pintamos los símbolos */
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle (x,y,15,paint);
        
        canvas.restore();
    }
	
	/******************************************************************
	 * Constructor de la clase
	 * @param context: Application context used to access resources
	 * @param attrs: propiedades del boton
	 ******************************************************************/
    public TTTButton(Context context, AttributeSet attrs){
    	super(context, attrs); /* Pasa las propiedades como layout_width y heigth a la superclase */
    	setBackgroundColor(border);
    }

    /*************************************
     * Cambia el color de un boton cuando
     * este ha sido seleccionado
     *************************************/
    public void select(){
    	color = background_off;
    	background = background_on;
    	this.invalidate();
    }
    
    /*****************************************
     * Devuelve un boton a su estado inicial
     * de color, una vez se haya pulsado 
     * otro o el mismo
     *****************************************/
    public void deselect(){
    	color = circulo;
    	background = background_off;
    	this.invalidate();
    }
    
    /**************************************
     * Pinta un circulo en un boton dado
     **************************************/
    public void on(){
    	color = circulo;
    	background = background_off;
    	this.invalidate();
    }
    
    /**************************************
     * Elimina el circulo de un boton dado
     **************************************/
    public void off(){
    	color = background_on;
    	background = color;
    	this.invalidate();
    }
            
    /***********************************************
     * Funcion que se llama al iniciarse un juego y
     * pinta todos los botones excepto el del
     * medio del tablero 
     ***********************************************/
    public void reset(int i){
    	color = circulo;
    	background = background_off;
    	
    	if (i == 16)
    		this.off();
    }
} 