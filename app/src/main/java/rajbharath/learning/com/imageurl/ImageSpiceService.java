package rajbharath.learning.com.imageurl;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.binary.InFileBitmapObjectPersister;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.memory.LruCacheBitmapObjectPersister;

/**
 * Created by rajbharath on 12/07/15.
 */
public class ImageSpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {

        CacheManager manager = new CacheManager();
        InFileBitmapObjectPersister filePersister = new InFileBitmapObjectPersister(getApplication());
        LruCacheBitmapObjectPersister memoryPersister = new LruCacheBitmapObjectPersister(filePersister, 1024 * 1024);

        manager.addPersister(memoryPersister);
        return manager;
    }
}
