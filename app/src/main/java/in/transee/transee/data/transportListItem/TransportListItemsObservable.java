package in.transee.transee.data.transportListItem;

import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * @author Michael Zhukov
 */
public class TransportListItemsObservable {

    private List<TransportListItem> transportListItems = new ArrayList<>();

    private FloatingActionButton fabApply;

    public TransportListItemsObservable(FloatingActionButton fabApply) {
        this.fabApply = fabApply;
    }

    public void addItem(TransportListItem transportListItem) {
        if (transportListItems.isEmpty()) {
            fabApply.show();
        }
        transportListItems.add(transportListItem);
    }

    public void deleteItem(TransportListItem transportListItem) {
        transportListItems.remove(transportListItem);
        if (transportListItems.isEmpty()) {
            fabApply.hide();
        }
    }

    public Observable<Map<String, List<String>>> asMapObservable() {
        return Observable
                .just(transportListItems)
                .flatMap(Observable::from)
                .reduce(Collections.<String, List<String>>emptyMap(), (total, next) -> {
                    List<String> items = total.get(next.getType());
                    if (items != null) {
                        items.add(next.getId());
                        total.put(next.getType(), items);
                        return total;
                    } else {
                        List<String> temp = new ArrayList<>();
                        temp.add(next.getId());
                        total.put(next.getType(), temp);
                        return total;
                    }
                });
    }
}
