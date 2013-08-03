package com.mixpanel.src;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class BreakDownBar extends View {

    

   
        private static Paint paint;
        private int screenW, screenH;
        private float X, Y;
        private Path path;
        private float initialScreenW;
        private float initialX, plusX;
        private float TX;
        private boolean translate;
        private int flash;
        private Context context;


        public BreakDownBar(Context context, AttributeSet attrs) {
            super(context, attrs);

            this.context=context;

            paint = new Paint();
            paint.setColor(Color.argb(0xff, 0x99, 0x00, 0x00));
            paint.setStrokeWidth(10);
            paint.setAntiAlias(true);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStyle(Paint.Style.STROKE);
            paint.setShadowLayer(7, 0, 0, Color.BLUE);


            path= new Path();
            TX=0;
            translate=false;

            flash=0;

        }

        @Override
        public void onSizeChanged (int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            screenW = w;
            screenH = h;
            X = 0;
            Y = screenH ;

            initialScreenW=screenW;
            initialX=((screenW/2)+(screenW/4));
            plusX=(screenW/6);

            path.moveTo(X, Y);

        }



        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //canvas.save();    

  
            path.lineTo(X,Y);
         
            float temp=initialX/4;
            if(X<temp+plusX){
            	X+=8;
            	Y-=8;
            }
            else{
					if(X<temp+(plusX*2)){
									X+=8;
									Y+=8;						
					            	}
					else{
						if(X<temp+(plusX*3)){
							X+=8;
							Y-=8;						
			            	}
						else{
							if(X<temp+(plusX*4)){
								X+=8;
								Y+=6;						
				            	}
							else{
								if(X<temp+(plusX*10)){
									X+=8;
									Y-=8;						
					            	}
								
							}
						}
					}
            }
            
            
            canvas.drawPath(path, paint);


            //canvas.restore(); 

            invalidate();
        }
}