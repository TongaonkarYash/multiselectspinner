package com.yash.tongaonkar.multiselectspinner;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yash on 18/11/17.
 */

public class MultiSelectSpinner extends AppCompatSpinner implements DialogInterface.OnMultiChoiceClickListener {

    private List<Object> objectsList;
    private Context context;
    private String[] items;
    private ArrayAdapter<Object> multiSpinnerAdapter;
    private boolean[] mSelection;
    private boolean[] mSelectionAtStart;
    private String itemSelectedAtStart;
    private OnMultipleItemsSelectedListener listener;
    private boolean hasNone;
    private List<String> stringFromList = new ArrayList<>();

    public MultiSelectSpinner(Context context) {
        super(context);
        this.context = context;
        this.multiSpinnerAdapter = new ArrayAdapter<Object>(context, android.R.layout.simple_list_item_1);
        super.setAdapter(this.multiSpinnerAdapter);
    }


    public MultiSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.multiSpinnerAdapter = new ArrayAdapter<Object>(context, android.R.layout.simple_list_item_1);
        super.setAdapter(this.multiSpinnerAdapter);
    }

    public void setListener(OnMultipleItemsSelectedListener listener) {
        this.listener = listener;
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            if (hasNone) {
                if (which == 0 && isChecked && mSelection.length > 1) {
                    for (int i = 1; i < mSelection.length; i++) {
                        mSelection[i] = false;
                        ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                    }
                } else if (which > 0 && mSelection[0] && isChecked) {
                    mSelection[0] = false;
                    ((AlertDialog) dialog).getListView().setItemChecked(0, false);
                }
            }
            mSelection[which] = isChecked;
            multiSpinnerAdapter.clear();
            multiSpinnerAdapter.add(buildSelectedItemString());
        } else {
            throw new IllegalArgumentException(
                    "Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Please select!");
        builder.setMultiChoiceItems(items, mSelection, this);
        itemSelectedAtStart = getSelectedItemsAsString();
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);
                List<Object> selectedObjects = new ArrayList<>();
                for (Integer selectedObj : getSelectedIndices()) {
                    selectedObjects.add(objectsList.get(selectedObj));
                }
//                listener.selectedObjects(selectedObjects);
//                listener.selectedIndices(getSelectedIndices());
//                listener.selectedStrings(getSelectedStrings());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                multiSpinnerAdapter.clear();
                multiSpinnerAdapter.add(itemSelectedAtStart);
                System.arraycopy(mSelectionAtStart, 0, mSelection, 0, mSelectionAtStart.length);
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] itemArray) {
        items = itemArray;
        mSelection = new boolean[itemArray.length];
        mSelectionAtStart = new boolean[itemArray.length];
        multiSpinnerAdapter.clear();
        multiSpinnerAdapter.add(items[0]);
        Arrays.fill(mSelection, false);
    }

    public void setItems(List<Object> itemList, String fieldName) {
        try {
            this.objectsList = itemList;
            if (!fieldName.equals("")) {
                for (Object obj : itemList) {
                    Class clazz = Class.forName(obj.getClass().getName());
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.getName().equals(fieldName)) {
                            stringFromList.add(field.get(obj).toString());
                        }
                    }
                }
            } else {
                for (Object stringObj : itemList) {
                    stringFromList.add((String) stringObj);
                }
            }
            items = stringFromList.toArray(new String[itemList.size()]);
            mSelection = new boolean[items.length];
            mSelectionAtStart = new boolean[items.length];
            multiSpinnerAdapter.clear();
            multiSpinnerAdapter.add(items[0]);
            Arrays.fill(mSelection, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setSelection(String[] selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (String cell : selection) {
            for (int j = 0; j < items.length; ++j) {
                if (items[j].equals(cell)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        multiSpinnerAdapter.clear();
        multiSpinnerAdapter.add(buildSelectedItemString());
    }

    public void setSelection(List<String> selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < items.length; ++j) {
                if (items[j].equals(sel)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        multiSpinnerAdapter.clear();
        multiSpinnerAdapter.add(buildSelectedItemString());
    }

    public List<Object> getSelectedObjects() {
        List<Object> selectedObjects = new ArrayList<>();
        for (Integer selectedObj : getSelectedIndices()) {
            selectedObjects.add(objectsList.get(selectedObj));
        }
        return selectedObjects;
    }


    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
            mSelectionAtStart[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        multiSpinnerAdapter.clear();
        multiSpinnerAdapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndices) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (int index : selectedIndices) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
                mSelectionAtStart[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        multiSpinnerAdapter.clear();
        multiSpinnerAdapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<>();
        for (int i = 0; i < items.length; ++i) {
            if (mSelection[i]) {
                selection.add(items[i]);
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndices() {
        List<Integer> selection = new LinkedList<>();
        for (int i = 0; i < items.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(items[i]);
            }
        }
        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(items[i]);
            }
        }
        return sb.toString();
    }

    public void hasNoneOption(boolean val) {
        hasNone = val;
    }

    public interface OnMultipleItemsSelectedListener {
        void selectedIndices(List<Integer> indices);

        void selectedStrings(List<String> strings);

        void selectedObjects(List<Object> objects);
    }
}
