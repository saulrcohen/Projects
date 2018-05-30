import React from 'react';
import { Image, ScrollView, StyleSheet, View, Text, Linking, Button } from 'react-native';
import { ExpoLinksView } from '@expo/samples';

export default class LinksScreen extends React.Component {
  static navigationOptions = {
    title: 'Agencies',
  };

  render() {
    return (
    <ScrollView style={styles.container}>
      <View style={styles.welcomeContainer}>
        <Image
          source={
               require('../assets/images/cRcApproved.png')
          }
            style = {styles.welcomeImage}
        />
        </View>
          <Text style={{fontWeight: 'bold'}}> Approved Agencies </Text>
            <Image
              source ={
                require('../assets/images/Kehilla-Kosher.png')
                      }
              //style = {styles.symbolContainer}
              />
              <Button
              color = 'blue'
              title = "Igud Hakashrus of Los Angeles (Kehillah Kosher)"
              onPress={ ()=> Linking.openURL('http://www.koshermarks.com/business-directory/126/california-k/') } >
              </Button>
              //
            <Button
            title = "Press me!"
            onPress={ ()=> Linking.openURL('http://www.koshermarks.com/business-directory/126/california-k/') } >
            containerStyle={{padding:10, height:45, overflow:'hidden', borderRadius:4, backgroundColor: 'white'}}
            disabledContainerStyle={{backgroundColor: 'grey'}}
            style={{fontSize: 20, color: 'green'}}>
          </Button>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 15,
    backgroundColor: '#fff',
  },
  image:{
      justifyContent: 'center',
      alignItems: 'center',
      flex: 1,
  },
  welcomeImage: {
    width: 100,
    height: 80,
    resizeMode: 'contain',
    marginTop: 3,
    marginLeft: -10,
  },
  welcomeContainer: {
    alignItems: 'center',
    marginTop: 10,
    marginBottom: 20,
  },
  symbolContainer: {
  alignItems: 'center',
  },
});
