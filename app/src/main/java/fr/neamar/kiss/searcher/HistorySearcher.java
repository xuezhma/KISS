package fr.neamar.kiss.searcher;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import fr.neamar.kiss.KissApplication;
import fr.neamar.kiss.MainActivity;
import fr.neamar.kiss.pojo.Pojo;

/**
 * Retrieve pojos from history
 */
public class HistorySearcher extends Searcher {
    private SharedPreferences prefs;

    public HistorySearcher(MainActivity activity) {
        super(activity);
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    @Override
    protected List<Pojo> doInBackground(Void... voids) {
        // Ask for records
        boolean smartHistory = !prefs.getString("history-mode", "recency").equals("recency");
        boolean excludeFavorites = prefs.getBoolean("exclude-favorites", false);
        int max_records = Integer.parseInt(prefs.getString("number-of-display-elements", String.valueOf(DEFAULT_MAX_RESULTS)));

        //Gather favorites
        ArrayList<Pojo> favoritesPojo = new ArrayList<Pojo>(0);
        if(excludeFavorites){
            favoritesPojo = KissApplication.getDataHandler(activity).getFavorites(activity.tryToRetrieve);
        }

        return KissApplication.getDataHandler(activity).getHistory(activity, max_records, smartHistory, favoritesPojo);
    }
}
