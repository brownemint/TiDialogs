package yy.tidialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiUIHelper;
import org.appcelerator.titanium.view.TiUIView;

import android.R;
//import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

@Kroll.proxy(creatableInModule = TidialogsModule.class)
  public class ListPickerProxy extends TiViewProxy {
    private class ListPicker extends TiUIView {

      Builder builder;



      public ListPicker(TiViewProxy proxy) {
        super(proxy);

      }

      private Builder getBuilder() {
        if (builder == null) {
          builder = new AlertDialog.Builder(this.proxy.getActivity());
          builder.setCancelable(true);
        }
        return builder;
      }

      @Override
      public void processProperties(KrollDict d) {
        super.processProperties(d);

        if (d.containsKey("title")) {
          getBuilder().setTitle(d.getString("title"));
        }

        if (d.containsKey("options")) {
          final String[] options = d.getStringArray("options");

          getBuilder()
            .setItems(options, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

                  KrollDict data = new KrollDict();
                  data.put("index", which);
                  fireEvent("click", data);

              }
          });
        }

      }

      public void show() {
        getBuilder().create().show();
        builder = null;
      }

    }

    public ListPickerProxy() {
      super();
    }

    @Override
    public TiUIView createView(Activity activity) {
      return new ListPicker(this);
    }

    @Override
    public void handleCreationDict(KrollDict options) {
      super.handleCreationDict(options);
    }

    @Override
    protected void handleShow(KrollDict options) {
      super.handleShow(options);
      TiUIHelper.runUiDelayedIfBlock(new Runnable() {
        @Override
        public void run() {
          ListPicker d = (ListPicker) getOrCreateView();
          d.show();
        }
      });
    }
  }
