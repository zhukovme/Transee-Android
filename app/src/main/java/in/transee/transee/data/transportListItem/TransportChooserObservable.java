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
public class TransportChooserObservable {

    private List<TransportListItem> transportListItems = new ArrayList<>();

    private FloatingActionButton fabLocateTransport;

    public TransportChooserObservable(FloatingActionButton fabLocateTransport) {
        this.fabLocateTransport = fabLocateTransport;
    }

    public void addItem(TransportListItem transportListItem) {
        if (transportListItems.isEmpty()) {
            fabLocateTransport.show();
        }
        transportListItems.add(transportListItem);
    }

    public void deleteItem(TransportListItem transportListItem) {
        transportListItems.remove(transportListItem);
        if (transportListItems.isEmpty()) {
            fabLocateTransport.hide();
        }
    }

    public Observable<Map<String, List<String>>> getSelectedItems() {
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
