import React from 'react';
import {
  Image,
  Platform,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import { ExpoConfigView } from '@expo/samples';

export default class SettingsScreen extends React.Component {
  static navigationOptions = {
    title: 'Settings',
    headerTintColor: "white",
    headerStyle:{
      backgroundColor: "#1589FF"
    }
  };

  render() {
    /* Go ahead and delete ExpoConfigView and replace it with your
     * content, we just wanted to give you a quick view of your config */
    return(
      <View style = {styles.tabBarInfoContainer}>
      <Text style = {styles.contaner}> Hi Shimon </Text>
      </View>
      );
    }
  }

  const styles = StyleSheet.create({
    container: {
      alignItems: 'center',
    },
    tabBarInfoContainer: {
      position: 'absolute',
      bottom: 0,
      left: 0,
      right: 0,
      ...Platform.select({
        ios: {
          shadowColor: 'blue',
          shadowOffset: { height: -3 },
          shadowOpacity: 0.1,
          shadowRadius: 3,
        },
        android: {
          elevation: 20,
        },
      }),
      alignItems: 'center',
      backgroundColor: '#d5d8dc',
      paddingVertical: 20,
    },
    contentContainer: {
      paddingTop: 30,
    },
  });
