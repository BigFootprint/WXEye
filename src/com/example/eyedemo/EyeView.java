package com.example.eyedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class EyeView extends View {
	private Context context;
	private Rect src, dst;
	private Paint paint, cleanPaint;
	private float phase = 0f;
	private Bitmap bitmap;
	private Canvas bitmapCanvas;
	private PaintFlagsDrawFilter filter;

	public EyeView(Context context) {
		super(context);
		this.context = context;
		initRes();
	}

	public EyeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		phase = 0;
		initRes();
	}

	private void initRes() {
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.icon_sight_capture_mask).copy(Bitmap.Config.ARGB_8888, true);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setDither(true);
		paint.setColor(Color.BLACK);

		cleanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		cleanPaint.setDither(true);
		cleanPaint.setColor(Color.WHITE);
		cleanPaint.setAlpha(0);
		cleanPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		
		bitmapCanvas = new Canvas(bitmap);
		
		filter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		bitmapCanvas.setDrawFilter(filter); 
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		dst = new Rect(0, 0, w, h);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		bitmapCanvas.drawColor(Color.BLACK);
		drawCircle(bitmapCanvas);
		canvas.setDrawFilter(filter); 
		canvas.drawBitmap(bitmap, src, dst, paint);
		if(phase < 1.0f){
			paint.setAlpha(80);
			canvas.drawRect(dst, paint);
		}
		
		paint.setAlpha(255);
	}

	private void drawCircle(Canvas canvas) {
		int circleRadius = Math.min(canvas.getWidth() / 2, canvas.getHeight() / 2);
		float realRadius = circleRadius * phase;
		canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, realRadius, cleanPaint);
	}

	public float getPhase() {
		return phase;
	}

	public void setPhase(float phase) {
		if(phase > 1.0 && this.phase > 1.0){
			return;
		}
		this.phase = phase;
		postInvalidate();
	}
}
