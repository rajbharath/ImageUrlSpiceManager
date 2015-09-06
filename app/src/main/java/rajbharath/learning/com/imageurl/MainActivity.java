package rajbharath.learning.com.imageurl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.InputStream;


public class MainActivity extends Activity {

    private ImageView img;
    private SpiceManager spiceManager;
    private ImageDownloadRequest imageDownloadRequest;
    private ImageDownloadRequestListener imageDownloadRequestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spiceManager = new SpiceManager(ImageSpiceService.class);
        img = (ImageView) findViewById(R.id.img);

    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spiceManager.execute(imageDownloadRequest, imageDownloadRequestListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ImageDownloadRequest extends SpiceRequest<Bitmap> {

        public ImageDownloadRequest(Class<Bitmap> bitmapClass) {
            super(bitmapClass);
        }

        @Override
        public Bitmap loadDataFromNetwork() throws Exception {
            String imageURL = "http://www.bikramyogawimbledon.com/wp-content/uploads/2014/10/SunsetYoga-2.jpg";
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    private class ImageDownloadRequestListener implements RequestListener<Bitmap> {
        @Override
        public void onRequestFailure(SpiceException e) {
            Log.d("Image Download", "Failed " + e.getMessage());
        }

        @Override
        public void onRequestSuccess(Bitmap bitmap) {
            setImage(bitmap);
        }

    }


    private void setImage(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
    }
}
