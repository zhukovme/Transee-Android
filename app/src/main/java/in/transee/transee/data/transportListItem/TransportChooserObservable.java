package in.transee.transee.data.transportListItem;

import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, List<String>> getSelectedItems() {
        Map<String, List<String>> selectedItems = new HashMap<>();
        List<String> items;
        for (TransportListItem item : transportListItems) {
            items = selectedItems.get(item.getType());
            if (items != null) {
                items.add(item.getId());
                selectedItems.put(item.getType(), items);
            } else {
                items = new ArrayList<>();
                items.add(item.getId());
                selectedItems.put(item.getType(), items);
            }
        }
        return selectedItems;
    }
}
