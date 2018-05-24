import React from 'react';
import { Image, Button, StyleSheet, Text, View } from 'react-native';
import { createStackNavigator } from 'react-navigation';
import Pdf from 'react-native-pdf';

class HomeScreen extends React.Component{
  render(){
    return (
     <View style = {{ flex: 5, alignItems: 'center', justifyContent: 'center',}}>
    <Text>Hi Daniel! </Text>
    <Button
    title = "See Kashrus List"
    onPress = {() => this.props.navigation.navigate('List')}
    />
    <Button
    title = "Scan it!"
    onPress = {() => this.props.navigation.navigate('Scanner')}
    />
      </View>
    );
  }
}

class kashrusList extends React.Component{
  render(){
    return(
      <View style = {{ flex : 1, alignItems : 'center', justifyContent: 'center', }}>
      <Text> Hi Jesse! </Text>
    <Button
      title = "Home"
      onPress = {() => this.props.navigation.navigate('Home')}
      />
      <Button
      title = "Scan it!"
      onPress = {() => this.props.navigation.navigate('Scanner')}
      />
    </View>
      );
  }
}

class scanner extends React.Component{
  render(){
  return(
    <View style = {{ flex : 1, alignItems : 'center', justifyContent: 'center', }}>
    <Text> Hi Shimon! </Text>
    <Button
    title = "Kashrus List"
    onPress = {() => this.props.navigation.navigate('List')}
    />
      <Button
      title = "Home"
      onPress = {() => this.props.navigation.navigate('Home')}
      />
    </View>
      );
  }
}
const Appl = createStackNavigator({
  Home: { screen: HomeScreen },
  List: { screen: kashrusList },
  Scanner: { screen : scanner},
    },
  {
  initialRouteName: 'Home',
  }
);

export default class App extends React.Component {

  render() {
    return <Appl />;
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#00A5FF',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
