package activity.sostv.com.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import activity.sostv.com.sostvapp.R;

public class SostvRoundImageView extends ImageView {

	private Context mContext;
	private int mBorderThickness = 0;
	private int defaultColor = 0xFFFFFFFF;
	private int mBorderOutsideColor = 0;
	private int mBorderInsideColor = 0;
	private int defaultWidth = 0;
	private int defaultHeight = 0;

	public SostvRoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		setCustomAttributes(attrs);
	}

	public SostvRoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		setCustomAttributes(attrs);
	}

	public SostvRoundImageView(Context context) {
		super(context);
		this.mContext = context;
	}

	private void setCustomAttributes(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs,
				R.styleable.roundedimageview);
		mBorderThickness = a.getDimensionPixelSize(
				R.styleable.roundedimageview_border_thickness, 0);
		mBorderOutsideColor = a
				.getColor(R.styleable.roundedimageview_border_outside_color,
						defaultColor);
		mBorderInsideColor = a.getColor(
				R.styleable.roundedimageview_border_inside_color, defaultColor);
	}

	private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();

		if (drawable == null) {
			return;
		}

		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		this.measure(0, 0);

		if (drawable.getClass() == NinePatchDrawable.class) {
			return;
		}
		Bitmap b = drawableToBitamp(drawable);
		Bitmap bitmap = b.copy(Config.ARGB_8888, true);
		if (defaultWidth == 0) {
			defaultWidth = getWidth();
		}
		if (defaultHeight == 0) {
			defaultHeight = getHeight();
		}
		int radius = 0;

		if (mBorderInsideColor != defaultColor
				&& mBorderOutsideColor != defaultColor) {// ���廭�����߿򣬷ֱ�Ϊ��Բ�߿����Բ�߿�

			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - 2 * mBorderThickness;

			// ����Բ

			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderInsideColor);

			// ����Բ

			drawCircleBorder(canvas, radius + mBorderThickness
					+ mBorderThickness / 2, mBorderOutsideColor);

		} else if (mBorderInsideColor != defaultColor
				&& mBorderOutsideColor == defaultColor) {// ���廭һ���߿�

			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - mBorderThickness;

			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderInsideColor);

		} else if (mBorderInsideColor == defaultColor
				&& mBorderOutsideColor != defaultColor) {// ���廭һ���߿�

			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - mBorderThickness;

			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderOutsideColor);

		} else {// û�б߿�

			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2;

		}

		Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);

		canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius, defaultHeight
				/ 2 - radius, null);
	}

	public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;
		int diameter = radius * 2;
		// Ϊ�˷�ֹ��߲���ȣ����Բ��ͼƬ���Σ���˽�ȡ�������д����м�λ������������ͼƬ
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// �ߴ��ڿ�
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// ��ȡ������ͼƬ
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// ����ڸ�
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bmp;
		}
		if (squareBitmap.getWidth() != diameter
				|| squareBitmap.getHeight() != diameter) {

			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
					diameter, true);
		} else {
			scaledSrcBmp = squareBitmap;
		}

		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;

	}

	private void drawCircleBorder(Canvas canvas, int radius, int color) {
		Paint paint = new Paint();
		/* ȥ��� */
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(color);
		/* ����paint�ġ�style��ΪSTROKE������ */
		paint.setStyle(Paint.Style.STROKE);
		/* ����paint������� */
		paint.setStrokeWidth(mBorderThickness);
		canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);

	}

}
