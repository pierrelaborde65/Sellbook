package filters;

/**
 * Created by meier on 21/11/2016.
 */
import play.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.http.DefaultHttpFilters;

import javax.inject.Inject;

public class Filters extends DefaultHttpFilters {
    @Inject public Filters(CORSFilter corsFilter) {
        super(corsFilter);
    }
}