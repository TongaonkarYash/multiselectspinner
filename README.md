# multiselectspinner

A simple multiple spinner selection library.
This library can help you to select multiple objects from list. Library not only supports normal String list but also supports your own custom class.
 
No need to set adapter. Just pass list contains and fieldName which you want to see on Spinner.

 
To use this:

- Add jitpack in your root build.gradle file.

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
 - add multi-select-spinner dependancy in your build.gradle
 
 	dependencies {
	        compile 'com.github.TongaonkarYash:multiselectspinner:1.0'
	}
 
- Most important while using this library is you have to pass List of Object to multiSelectSpinner.setItems(List<Object>, <fieldName>);
 
- To use this with simple String list use it like:

 MultiSelectSpinner multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.optionSpinner);
 MultiSelectSpinner multiSelectSpinner.setItems(stringList, ""); 
 #(stringList is custom list of Strings & second Parameter should be left blank).
 MultiSelectSpinner multiSelectSpinner.setSelection(new int[]{0});
 
 - To use this with custom user defind class, use like: 
 
 MultiSelectSpinner multiSelectSpinner = (MultiSelectSpinner) findViewById(R.id.optionSpinner);
 MultiSelectSpinner multiSelectSpinner.setItems(<any custom class list>, "id"); 
 #(here first parameter is any user defind class list, and second parameter is the field from that class which you want to see in Spinner textView, which is in this case "id")
 MultiSelectSpinner multiSelectSpinner.setSelection(new int[]{0});

- To get result of selected objects call:
multiSelectSpinner.getSelectedObjects() which will return you List<Object>.
 
 - To get result of selected index call:
 multiSelectSpinner.getSelectedIndices() which will return you List<Integer>.
 
 
