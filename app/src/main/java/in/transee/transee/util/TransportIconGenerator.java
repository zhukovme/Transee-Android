package in.transee.transee.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import in.transee.transee.R;

public class TransportIconGenerator {

    private int width;
    private int height;
    private int strokeWidth;
    private int textSize;
    private int maxTextLength;

    private Canvas canvas;
    private Paint paint;

    public TransportIconGenerator(Context context) {
        width = context.getResources().getInteger(R.integer.transport_icon_width);
        height = context.getResources().getInteger(R.integer.transport_icon_height);
        textSize = context.getResources().getInteger(R.integer.transport_icon_text_size);
        strokeWidth = context.getResources().getInteger(R.integer.transport_icon_stroke_width);
        maxTextLength = context.getResources().getInteger(R.integer.transport_icon_max_text_length);
    }

    public BitmapDescriptor getIcon(String text, int color, float angle) {
        return BitmapDescriptorFactory.fromBitmap(createBitmap(text, color, angle));
    }

    private Bitmap createBitmap(String text, int color, float angle) {
        Bitmap transportBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(transportBitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);

        drawDirectionAngle(color);
        drawOuterCircle(color);
        drawInnerCircle();
        rotate(angle);
        drawText(text);

        return transportBitmap;
    }

    private void drawDirectionAngle(int color) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(color);
        Path path = new Path();
        path.moveTo(width * 0.1f, height - width * 0.8f);
        path.lineTo(width * 0.5f, 0);
        path.lineTo(width * 0.9f, height - width * 0.8f);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawOuterCircle(int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width * 0.5f, height - width * 0.5f, width * 0.5f, paint);
    }

    private void drawInnerCircle() {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width * 0.5f, height - width * 0.5f, width * 0.4f, paint);
    }

    private void rotate(float angle) {
        canvas.rotate(-angle, width * 0.5f, height - width * 0.5f);
    }

    private void drawText(String text) {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextAlign(Paint.Align.CENTER);
        if (text.length() >= maxTextLength) {
            paint.setTextSize(textSize - 5);
        } else {
            paint.setTextSize(textSize);
        }
        canvas.drawText(text, width * 0.5f, height - width * 0.37f, paint);
    }
}
