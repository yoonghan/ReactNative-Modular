#React native as a modular approach 
Creating 3rd party module for react native to load. This concept is based on https://facebook.github.io/react-native/docs/integration-with-existing-apps.html. JS script are downloaded externally and then loaded as react native rendered into the screen.

This is a code as proof of concept for a customer.

## Concept:
Imagine that you do:
1. Open the app.
2. Download the compiled javascript, this javascript is React native script.
3. Open a seperate Application page to render the App.
4. Modify the layout/javascript and reload the App.
5. The App now shows the latest code.

## Installation
1. Install nodeJS.

```
brew install node
brew install watchman #For macOS
```

2. Run

```
npm install
```

3. Install react-native

```
npm install -g react-native-cli
```

4. Connect to a phone or startup the emulator. Execute on the phone by running

```
react-native run-android
```

## Manual changes
If there are interface changes for the React part, run:

```
react-native bundle --platform android --dev false --entry-file index.js --bundle-output index.android.bundle 
```

Then upload index.android.bundle code into a server, let the user retrieve from the server; the path is changeable.
