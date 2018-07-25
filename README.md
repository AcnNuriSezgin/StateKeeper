# StateKeeper
StateKeeper keeps your activity and fragment states and restores them when do you need. The library provides two different states, there are HotState and ColdState. If a field annotated with HotState, it manages over Bundle. When we use ColdState, its state saves to File System and restores from there. 

### Prerequisites
First of all you must add statekeeper library to build.gradle file.
```groovy
implementation 'nurisezgin.com.android.statekeeper:statekeeper:1.0.0'
```

## How To Use
* HotState 
```java
    @HotState
    public boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateKeeper.dispatchSave(getCacheDir(), outState, this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        StateKeeper.dispatchRestore(getCacheDir(), savedInstanceState, this);
    }
```

* HotState & ColdState. In general **ColdState** means save to file and restore from file. We have to know file name in that case we use a field that annotated with **StateIdentifier** also we avoid state confusing for multi instance fragments and activities with that field. Other good thing for **ColdState** we never get TransactionTooLargeException.
```java
    @StateIdentifier
    @HotState
    public String identifier;

    @HotState
    public boolean flag;

    @ColdState
    public List<Object> objectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (identifier == null) {
            identifier = UUID.randomUUID().toString();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        StateKeeper.dispatchSave(getCacheDir(), outState, this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        StateKeeper.dispatchRestore(getCacheDir(), savedInstanceState, this);
    }
```
## Authors
* **Nuri SEZGIN**-[Email](acnnurisezgin@gmail.com)

## License
Copyright 2018 Nuri SEZGIN

GNU General Public License v3.0
