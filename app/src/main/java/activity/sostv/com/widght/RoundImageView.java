package activity.sostv.com.widght;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import activity.sostv.com.sostvapp.R;

public class RoundImageView extends ImageView
{
	/**
	 */
	private int type;
	public static final int TYPE_CIRCLE = 0;
	public static final int TYPE_ROUND = 1;
	/**
	 */
	private static final int BODER_RADIUS_DEFAULT = 10;
	/**
	 */
	private int mBorderRadius;

	/**
	 * 绘图的Paint
	 */
	private Paint mBitmapPaint;
	/**
	 */
	private int mRadius;
	/**
	 * 3x3 矩阵，主要用于缩小
	 */
	private Matrix mMatrix;
	/**
	 * 渲染图像，使用图像为绘制图形
	 */
	private BitmapShader mBitmapShader;
	/**
	 */
	private int mWidth;
	private RectF mRoundRect;

	public RoundImageView(Context context, AttributeSet attrs)
	{

		super(context, attrs);
		mMatrix = new Matrix();
		mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RoundImageView);

		mBorderRadius = a.getDimensionPixelSize(
				R.styleable.RoundImageView_borderRadius, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
								BODER_RADIUS_DEFAULT, getResources()
										.getDisplayMetrics()));// 默认�?10dp
		type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);// 默认为Circle

		a.recycle();
	}

	public RoundImageView(Context context)
	{
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/**
		 * 如果类型是圆形，则强制改变view的宽高一致，以小
		 */
		if (type == TYPE_CIRCLE)
		{
			mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
			mRadius = mWidth / 2;
			setMeasuredDimension(mWidth, mWidth);
		}

	}

	/**
	 * 初始化BitmapShader
	 */
	private void setUpShader()
	{
		Drawable drawable = getDrawable();
		if (drawable == null)
		{
			return;
		}

		Bitmap bmp = drawableToBitamp(drawable);
		mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
		float scale = 1.0f;
		if (type == TYPE_CIRCLE)
		{
			// 拿到bitmap宽或高的小�??
			int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
			scale = mWidth * 1.0f / bSize;

		} else if (type == TYPE_ROUND)
		{
			Log.e("TAG",
					"b'w = " + bmp.getWidth() + " , " + "b'h = "
							+ bmp.getHeight());
			if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight()))
			{
				scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),
						getHeight() * 1.0f / bmp.getHeight());
			}

		}
		mMatrix.setScale(scale, scale);
		// 设置变换矩阵
		mBitmapShader.setLocalMatrix(mMatrix);
		// 设置shader
		mBitmapPaint.setShader(mBitmapShader);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Log.e("TAG", "onDraw");
		if (getDrawable() == null)
		{
			return;
		}
		setUpShader();

		if (type == TYPE_ROUND)
		{
			canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
					mBitmapPaint);
		} else
		{
			canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
			// drawSomeThing(canvas);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);

		if (type == TYPE_ROUND)
			mRoundRect = new RectF(0, 0, w, h);
	}

	/**
	 * drawable转bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitamp(Drawable drawable)
	{
		if (drawable instanceof BitmapDrawable)
		{
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	private static final String STATE_INSTANCE = "state_instance";
	private static final String STATE_TYPE = "state_type";
	private static final String STATE_BORDER_RADIUS = "state_border_radius";

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
		bundle.putInt(STATE_TYPE, type);
		bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state)
	{
		if (state instanceof Bundle)
		{
			Bundle bundle = (Bundle) state;
			super.onRestoreInstanceState(((Bundle) state)
					.getParcelable(STATE_INSTANCE));
			this.type = bundle.getInt(STATE_TYPE);
			this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
		} else
		{
			super.onRestoreInstanceState(state);
		}

	}

	public void setBorderRadius(int borderRadius)
	{
		int pxVal = dp2px(borderRadius);
		if (this.mBorderRadius != pxVal)
		{
			this.mBorderRadius = pxVal;
			invalidate();
		}
	}

	public void setType(int type)
	{
		if (this.type != type)
		{
			this.type = type;
			if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE)
			{
				this.type = TYPE_CIRCLE;
			}
			requestLayout();
		}

	}

	public int dp2px(int dpVal)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, getResources().getDisplayMetrics());
	}

}